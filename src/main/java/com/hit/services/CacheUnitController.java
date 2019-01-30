package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T>
{
	CacheUnitService<T> cacheService = null;
	
	public CacheUnitController()
	{
		cacheService = (CacheUnitService<T>) CacheUnitService.getInstance();
	}
	
	public boolean delete(DataModel<T>[] dataModels)
	{
		return cacheService.delete(dataModels);
	}
	
	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		return cacheService.get(dataModels);
	}
	
	public boolean update(DataModel<T>[] dataModels)
	{
		return cacheService.update(dataModels);
	}
}
