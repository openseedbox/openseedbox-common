package com.openseedbox.code.ws;


import com.openseedbox.code.Util;
import org.apache.commons.lang.builder.HashCodeBuilder;
import play.Logger;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * Using a custom truststore in java as well as the default one
 * @url https://stackoverflow.com/a/24561444
 */
public class CustomSSLContext {

	public static SSLContext getSslContext(final List<Map.Entry<String, String>> uriWithCertificates) {
		SSLContext sslContext;
		StringBuilder certificatesToAdd = new StringBuilder();

		try {
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			// Using null here initialises the TMF with the default trust store.
			tmf.init((KeyStore) null);

			// Get hold of the default trust manager
			X509ExtendedTrustManager defaultTm = null;
			for (TrustManager tm : tmf.getTrustManagers()) {
				if (tm instanceof X509ExtendedTrustManager) {
					defaultTm = (X509ExtendedTrustManager) tm;
					break;
				}
			}

			final List<HostX509CertificatesTuple> certificatesByHosts = new ArrayList<HostX509CertificatesTuple>();
			for (Map.Entry<String, String> entry : uriWithCertificates) {
				certificatesToAdd.append(entry.getValue());
				try {
					URI uri = new URI("https://" + entry.getKey());
					String host = uri.getHost();
					X509Certificate[] chain = Util.stringToCertificates(entry.getValue()).toArray(new X509Certificate[0]);
					certificatesByHosts.add(new HostX509CertificatesTuple(host, chain));
				} catch (URISyntaxException e) {
					Logger.warn("What's wrong with %s? %s", entry.getKey(), e);
				}
			}

			Collection<? extends Certificate> certificates = Util.stringToCertificates(certificatesToAdd.toString());
			Logger.info("Extracted %d certificates", certificates.size());

			// Do the same with your trust store this time
			// Adapt how you load the keystore to your needs
			KeyStore myTrustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			myTrustStore.load((InputStream) null, "password".toCharArray());
			int index = 0;
			for (Certificate certificate : certificates) {
				myTrustStore.setCertificateEntry(Integer.toString(index++), certificate);
			}

			tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(myTrustStore);

			// Get hold of the default trust manager
			X509ExtendedTrustManager myTm = null;
			for (TrustManager tm : tmf.getTrustManagers()) {
				if (tm instanceof X509ExtendedTrustManager) {
					myTm = (X509ExtendedTrustManager) tm;
					break;
				}
			}

			// Wrap it in your own class.
			final X509ExtendedTrustManager finalDefaultTm = defaultTm;
			final X509ExtendedTrustManager finalMyTm = myTm;
			X509ExtendedTrustManager customTm = new X509ExtendedTrustManager() {
				// TODO: implement poor man's HostnameChecker for the other checks!
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
					finalDefaultTm.checkClientTrusted(x509Certificates, s, socket);
				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
					try {
						finalMyTm.checkServerTrusted(x509Certificates, s, socket);
					} catch (CertificateException e) {
						// This will throw another CertificateException if this fails too.
						finalDefaultTm.checkServerTrusted(x509Certificates, s, socket);
					}
				}

				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
					finalDefaultTm.checkClientTrusted(x509Certificates, s, sslEngine);
				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
					try {
						try {
							finalMyTm.checkServerTrusted(x509Certificates, s, sslEngine);
						} catch (CertificateException e) {
							// Poor man's HostnameChecker
							if(!certificatesByHosts.contains(new HostX509CertificatesTuple(sslEngine.getPeerHost(), x509Certificates))) {
								throw e;
							}
						}
					} catch (CertificateException e) {
						// This will throw another CertificateException if this fails too.
						finalDefaultTm.checkServerTrusted(x509Certificates, s, sslEngine);
					}
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// If you're planning to use client-cert auth,
					// merge results from "defaultTm" and "myTm".
					return finalDefaultTm.getAcceptedIssuers();
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
				                               String authType) throws CertificateException {
					try {
						finalMyTm.checkServerTrusted(chain, authType);
					} catch (CertificateException e) {
						// This will throw another CertificateException if this fails too.
						finalDefaultTm.checkServerTrusted(chain, authType);
					}
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// If you're planning to use client-cert auth,
					// do the same as checking the server.
					finalDefaultTm.checkClientTrusted(chain, authType);
				}
			};

			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{customTm}, null);
		} catch (Exception e) {
			throw new RuntimeException("Error setting SSL context " + e.toString());
		}

		// You don't have to set this as the default context,
		// it depends on the library you're using.
		//SSLContext.setDefault(sslContext);

		return sslContext;
	}

	private static class HostX509CertificatesTuple {
		private String host;
		private X509Certificate[] certificates;

		public HostX509CertificatesTuple(String host, X509Certificate[] certificates) {
			this.host = host;
			this.certificates = certificates;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public X509Certificate[] getCertificates() {
			return certificates;
		}

		public void setCertificates(X509Certificate[] certificates) {
			this.certificates = certificates;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;

			if (o == null || getClass() != o.getClass()) return false;

			HostX509CertificatesTuple that = (HostX509CertificatesTuple) o;

			return host.equals(that.host)
					&& Arrays.deepEquals(certificates, that.certificates);
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37)
					.append(host)
					.append(certificates)
					.toHashCode();
		}

		@Override
		public String toString() {
			return "HostX509CertificatesTuple{" +
					"host='" + host + '\'' +
					", certificates=" + Arrays.toString(certificates) +
					'}';
		}
	}
}
