package com.openseedbox.code.ws;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ProxyServer;
import play.Logger;
import play.Play;
import play.libs.WS;
import play.libs.ws.WSAsync;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

public class WSAsyncWithCustomSSLContext extends WSAsync implements WS.WSImpl {
	private AsyncHttpClient httpClient;

	public WSAsyncWithCustomSSLContext() {
		String proxyHost = Play.configuration.getProperty("http.proxyHost", System.getProperty("http.proxyHost"));
		String proxyPort = Play.configuration.getProperty("http.proxyPort", System.getProperty("http.proxyPort"));
		String proxyUser = Play.configuration.getProperty("http.proxyUser", System.getProperty("http.proxyUser"));
		String proxyPassword = Play.configuration.getProperty("http.proxyPassword", System.getProperty("http.proxyPassword"));
		String nonProxyHosts = Play.configuration.getProperty("http.nonProxyHosts", System.getProperty("http.nonProxyHosts"));
		String userAgent = Play.configuration.getProperty("http.userAgent");

		AsyncHttpClientConfig.Builder confBuilder = new AsyncHttpClientConfig.Builder();
		if (proxyHost != null) {
			int proxyPortInt = 0;
			try {
				proxyPortInt = Integer.parseInt(proxyPort);
			} catch (NumberFormatException e) {
				Logger.error(e,
						"Cannot parse the proxy port property '%s'. Check property http.proxyPort either in System configuration or in Play config file.",
						proxyPort);
				throw new IllegalStateException("WS proxy is misconfigured -- check the logs for details");
			}
			ProxyServer proxy = new ProxyServer(proxyHost, proxyPortInt, proxyUser, proxyPassword);
			if (nonProxyHosts != null) {
				String[] strings = nonProxyHosts.split("\\|");
				for (String uril : strings) {
					proxy.addNonProxyHost(uril);
				}
			}
			confBuilder.setProxyServer(proxy);
		}
		if (userAgent != null) {
			confBuilder.setUserAgent(userAgent);
		}

		try {
			confBuilder.setSSLContext(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
		}

		// TODO: update this when updating to Play! 1.5 and newer
		httpClient = new AsyncHttpClient(confBuilder.build());
	}

	@Override
	public void stop() {
		Logger.trace("Releasing http client connections...");
		httpClient.close();
	}

	@Override
	public WS.WSRequest newRequest(String url, String encoding) {
		return new WSAsyncWithCustomSSLContextRequest(url, encoding);
	}


	public class WSAsyncWithCustomSSLContextRequest extends WSAsyncRequest {
		public WSAsyncWithCustomSSLContextRequest(String url, String encoding) {
			super(url, encoding);
		}
	}
}
