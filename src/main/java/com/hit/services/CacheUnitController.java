package com.hit.services;

import java.util.HashMap;
import java.util.Map;

import com.hit.dm.DataModel;

/**
 * 
 * @author Shahar and Tal
 *
 * @param <T> Type of the model
 */
public class CacheUnitController<T>
{
	// Creating a singleton instance to retrive that from the CacheUnit
	CacheUnitService<T> cacheService = null;
	
	public CacheUnitController()
	{
		cacheService = (CacheUnitService<T>) CacheUnitService.getInstance();
	}
	
	/**
	 * Remove the models from the cache unit
	 * @param dataModels
	 * @return if it's successful or not
	 */
	public boolean delete(DataModel<T>[] dataModels)
	{
		return cacheService.delete(dataModels);
	}
	
	/**
	 * 
	 * @param dataModels
	 * @return retrieves the models from the CacheUnit
	 */
	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		return cacheService.get(dataModels);
	}
	
	/**
	 * 
	 * @param dataModels
	 * @return Update models in the cache unit
	 */
	public boolean update(DataModel<T>[] dataModels)
	{
		return cacheService.update(dataModels);
	}

	public Map<String, Integer> options() {
		// TODO Auto-generated method stub
		return cacheService.getCacheSummary();
	}
}
