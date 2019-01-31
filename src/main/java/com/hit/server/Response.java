package com.hit.server;

import java.util.Map;

public class Response<T> {
	
	protected Map<String,String> headers;
	protected T body;
	
	public Response(Map<String,String> headers, T body)
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
