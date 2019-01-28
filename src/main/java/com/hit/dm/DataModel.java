package com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected T m_Content;
	protected Long m_Id;
	
	public DataModel(Long id, T content)
	{
		this.m_Content = content;
		this.m_Id = id;
	}
	
	public T getContent()
	{
		return this.m_Content;
	}
	
	public Long getDataModelId()
	{
		return this.m_Id;
	}
	
	public void setContent(T content)
	{
		this.m_Content = content;
	}
	
	public void setDataModelId(Long id)
	{
		this.m_Id = id;
	}
	
}
