package com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String,String> m_Headers;
	protected T m_Body;
	
	public Request(Map<String,String> headers, T body)
	{
		this.m_Headers = headers;
		this.m_Body = body;
	}

	public Map<String, String> getHeaders() {
		return m_Headers;
	}

	public void setHeaders(Map<String, String> m_Headers) {
		this.m_Headers = m_Headers;
	}

	public T getBody() {
		return m_Body;
	}

	public void setBody(T m_Body) {
		this.m_Body = m_Body;
	}
}
