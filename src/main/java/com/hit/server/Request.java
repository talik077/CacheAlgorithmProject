package com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String,String> headers;
	protected T body;
	
	public Request(Map<String,String> headers, T body)
	{
		this.headers = headers;
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> m_Headers) {
		this.headers = m_Headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T m_Body) {
		this.body = m_Body;
	}
	
}
