package play.libs.ws;

import com.ning.http.client.AsyncHttpClient;

import java.nio.charset.Charset;

public class WSAsyncWithCustomSSLContextRequest extends WSAsyncRequest {
	private final AsyncHttpClient httpClient;

	public WSAsyncWithCustomSSLContextRequest(AsyncHttpClient httpClient, String url, Charset encoding) {
		super(httpClient, url, encoding);
		this.httpClient = httpClient;
	}
}
