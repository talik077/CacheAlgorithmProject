package com.hit.dao;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	
	public DaoFileImpl(String filepath) {
		
	}
	
	public DaoFileImpl(String filePath, int capacity)  {
		
	}
	
	@Override
	/***
	 * Saves a given entity.
	 */
	public void save(DataModel<T> t) {
		
	}
	
	@Override	
	/***
	 * Deletes a given entity.
	 */
	public void delete(DataModel<T> t) {
		
	}
	
	@Override	
	/***
	 * Retrieves an entity by its id.
	 */
	public DataModel<T> find(Long id){
		return null;
	}

}
