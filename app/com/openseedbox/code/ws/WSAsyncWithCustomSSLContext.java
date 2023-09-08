package com.openseedbox.code.ws;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ProxyServer;
import play.Play;
import play.libs.ws.WSAsync;
import play.libs.ws.WSRequest;
import play.libs.ws.WSAsyncWithCustomSSLContextRequest;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class WSAsyncWithCustomSSLContext extends WSAsync {
	private static final Logger logger = LoggerFactory.getLogger(WSAsyncWithCustomSSLContext.class);
	private final AsyncHttpClient httpClient;

	public WSAsyncWithCustomSSLContext() {
		String userAgent = Play.configuration.getProperty("http.userAgent");

		AsyncHttpClientConfig.Builder confBuilder = new AsyncHttpClientConfig.Builder();

		buildProxy().ifPresent(confBuilder::setProxyServer);

		if (isNotEmpty(userAgent)) {
			confBuilder.setUserAgent(userAgent);
		}

		try {
			confBuilder.setSSLContext(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException ignored) {
		}

		// when using raw urls, AHC does not encode the params in url.
		// this means we can/must encode it(with correct encoding) before
		// passing it to AHC
		confBuilder.setDisableUrlEncodingForBoundedRequests(true);
		httpClient = new AsyncHttpClient(confBuilder.build());
	}

	private Optional<ProxyServer> buildProxy() {
		String proxyHost = Play.configuration.getProperty("http.proxyHost", System.getProperty("http.proxyHost"));
		if (isEmpty(proxyHost)) return Optional.empty();

		String proxyPort = Play.configuration.getProperty("http.proxyPort", System.getProperty("http.proxyPort"));
		String proxyUser = Play.configuration.getProperty("http.proxyUser", System.getProperty("http.proxyUser"));
		String proxyPassword = Play.configuration.getProperty("http.proxyPassword", System.getProperty("http.proxyPassword"));
		String nonProxyHosts = Play.configuration.getProperty("http.nonProxyHosts", System.getProperty("http.nonProxyHosts"));

		ProxyServer proxy = new ProxyServer(proxyHost, parseProxyPort(proxyPort), proxyUser, proxyPassword);
		if (isNotEmpty(nonProxyHosts)) {
			for (String url : nonProxyHosts.split("\\|")) {
				proxy.addNonProxyHost(url);
			}
		}
		return Optional.of(proxy);
	}

	private int parseProxyPort(String proxyPort) {
		try {
			return Integer.parseInt(proxyPort);
		} catch (NumberFormatException e) {
			logger.error(
				"Cannot parse the proxy port property '{}'. Check property http.proxyPort either in System configuration or in Play config file.",
				proxyPort, e);
			throw new IllegalStateException("WS proxy is misconfigured -- check the logs for details");
		}
	}

	@Override
	public void stop() {
		logger.trace("Releasing http client connections...");
		httpClient.close();
	}

	@Override
	public WSRequest newRequest(String url) {
		return new WSAsyncWithCustomSSLContextRequest(httpClient, url, Play.defaultWebEncoding);
	}

}
