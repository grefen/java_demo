package com.rest.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.rest.demo.HttpClientUtils;
import com.sun.jna.platform.win32.Advapi32Util.Account;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class Demo {
	static RestTemplate createRestTemplate(String username, String password, String host, int port ) {
	    return new RestTemplate(createSecureTransport( username, password, host, port ));
	}

	static ClientHttpRequestFactory createSecureTransport( String username, String password, String host, int port ){
	    DefaultHttpClient client = new DefaultHttpClient();
	    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials( username, password );
	    client.getCredentialsProvider().setCredentials( new AuthScope( host, port ), credentials );
	    return new HttpComponentsClientHttpRequestFactory(client);
	}

	public static void main(String[] args) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
	    CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
       // String result = restTemplate.getForObject("https://14A6008X60MGLR0:8443",String.class);
        //System.out.println(result);
        
        String plainCreds = "admin:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        
        HttpEntity<String> request = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange("https://14A6008X60MGLR0:8443/cli/application/info/?application=AppDemo", HttpMethod.GET, request, String.class);
        
        System.out.println(response.getBody());

        
        JSONObject json = JSONObject.fromObject(response.getBody());
        
        System.out.println(json.toString());
	}

}
