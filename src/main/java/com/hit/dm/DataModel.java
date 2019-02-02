package com.hit.dm;

import java.io.Serializable;

/***
 * A generic DataModel save id of the model, and his Data.
 * @author Shahar And Tal
 *
 * @param <T> type of Data
 */
public class DataModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/***
	 * Hold the data of the model
	 */
	protected T content;
	
	/***
	 * Hold the id of the model.
	 */
	protected Long dataModelId;

	
	/***
	 * 
	 * @param id - identity of the model 
	 * @param content - data of the model
	 */
	public DataModel(Long id, T content) {
		this.content = content;
		this.dataModelId = id;
	}

	/***
	 * 
	 * @return the Data of this model
	 */
	public T getContent() {
		return this.content;
	}

	/***
	 * 
	 * @return the identity of this model with the id we can find a specific models.
 	 */
	public Long getDataModelId() {
		return this.dataModelId;
	}
	
	/***
	 * 
	 * @param content - the data that going to be set in the model.
	 */
	public void setContent(T content) {
		this.content = content;
	}
	
	/***
	 * 
	 * @param id - the identity that going to be set for the model,
	 * with the id we can find a specific models.
	 */
	public void setDataModelId(Long id) {
		this.dataModelId = id;
	}

}
