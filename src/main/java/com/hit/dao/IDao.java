package com.hit.dao;

import java.io.Serializable;

public interface IDao<ID extends Serializable,T>
{
	/***
	 * Saves a given entity.
	 * @param entity
	 */
	public void save(T entity);
	
	/***
	 * Deletes a given entity.
	 * @param entity
	 */
	public void delete(T entity);
	
	/***
	 * Retrieves an entity by its id.
	 * @param id
	 * @return
	 */
	public T find(ID id);
}
