/**
 * 
 */
package edu.umbc.recognizr;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * @author audumbar
 *
 */
public class HttpClientWrapper {
	
	HttpClient client;

	private byte[] body;
	private int statusCode;

	public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.0.1) Gecko/20060111 Firefox/1.5.0.1";
	
	public HttpClientWrapper() {
		client = new HttpClient();
	}
	
	public String getPageContents(String p_pageURL) {
		String pageContents = "";
		HttpClientParams clientParams = new HttpClientParams();

		clientParams.setCookiePolicy(CookiePolicy.DEFAULT);
        client.setParams(clientParams);

        HttpMethodBase method = new GetMethod(p_pageURL);
        
        method.addRequestHeader(new Header("User-Agent", DEFAULT_USER_AGENT));

        try {
            statusCode = client.executeMethod(method);
            body = method.getResponseBody();	
        } catch (Exception e) {
        	System.out.println("Error : " + e.getMessage());
        }
		pageContents = body.toString();
		return pageContents;
	}
	public static void main(String[] args) {
		String url = new String("http://tagthe.net/api/?url=http://socialwebtechnologies.blogspot.com/");
		HttpClientWrapper clientWrapper = new HttpClientWrapper();

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setCookiePolicy(CookiePolicy.DEFAULT);
        clientWrapper.client.setParams(clientParams);

        HttpMethodBase method = new GetMethod(url);
        
        method.addRequestHeader(new Header("User-Agent", DEFAULT_USER_AGENT));

        try {
            clientWrapper.statusCode = clientWrapper.client.executeMethod(method);
            clientWrapper.body = method.getResponseBody();
        } catch (Exception e) {
        	
        }
        
		System.out.println("hello world");
		System.out.println(clientWrapper.statusCode + new String(clientWrapper.body).toString());
	}
}
