package com.hit.server;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author Shahar and Tal
 *
 * @param <T> Model type of Response
 */
public class Response<T> implements Serializable {
	
	/**
	 * Headers of the response represented as HashMap<String, String>
	 */
	protected Map<String,String> headers;
	
	/**
	 * Body of the response can be any type
	 */
	protected T body;
	
	public Response(Map<String,String> headers, T body)
	{
		this.headers = headers;
		this.body = body;
	}
	
	/**
	 * 
	 * @return get headers of the response
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	/**
	 * 
	 * @param m_Headers set headers of the response
	 */
	public void setHeaders(Map<String, String> m_Headers) {
		this.headers = m_Headers;
	}
	
	/**
	 * 
	 * @return get body the response
	 */
	public T getBody() {
		return body;
	}
	
	/**
	 * 
	 * @param m_Body set body of the response
	 */
	public void setBody(T m_Body) {
		this.body = m_Body;
	}
}
