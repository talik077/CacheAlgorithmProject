package com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected T content;
	protected Long dataModelId;

	public DataModel(Long id, T content) {
		this.content = content;
		this.dataModelId = id;
	}

	public T getContent() {
		return this.content;
	}

	public Long getDataModelId() {
		return this.dataModelId;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public void setDataModelId(Long id) {
		this.dataModelId = id;
	}

}
