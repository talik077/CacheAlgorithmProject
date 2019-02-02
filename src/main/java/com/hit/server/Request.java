package com.hit.server;

import java.io.Serializable;
import java.util.Map;

/**
 * The request of the user 
 * 
 * @author Shahar and Tal
 *
 * @param <T>
 */
public class Request<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * Headers of the requests represented as HashMap<String, String>
	 */
	protected Map<String,String> headers;
	
	/**
	 * Body of the request can be any type
	 */
	protected T body;
	
	public Request(Map<String,String> headers, T body)
	{
		this.headers = headers;
		this.body = body;
	}
	
	/**
	 * 
	 * @return get headers of the request
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * 
	 * @param m_Headers set headers of the request
	 */
	public void setHeaders(Map<String, String> m_Headers) {
		this.headers = m_Headers;
	}

	/**
	 * 
	 * @return the body of the request
	 */
	public T getBody() {
		return body;
	}
	
	/***
	 * 
	 * @param m_Body set the body of the request
	 */
	public void setBody(T m_Body) {
		this.body = m_Body;
	}
	
}
