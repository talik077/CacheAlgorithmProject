package com.hit.services;

import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hit.algorithm.*;

public class CacheUnitService<T>
{
	private static CacheUnitService<?> singleton = null;
	
	/**
	 * Capacity of the unit cache
	 */
	private static int capacity = 20;
	
	/**
	 * The Alogortihem to use for the unit cache
	 */
	private static IAlgoCache algo = new LRUAlgoCacheImpl(capacity);
	private CacheUnit<T> cacheUnit = null;
	
	private IDao dao = new DaoFileImpl<T>("datasource.txt");
	
	//Constructor
	CacheUnitService()
	{
		this.cacheUnit = new CacheUnit(this.algo, dao);
	}
	
	/**
	 * Singleton for cacheunit get - get instance
	 */
	public static CacheUnitService<?> getInstance()
	{
		if (singleton == null)
			return singleton = new CacheUnitService<>();
		
		return singleton;
	}
	
	public static void changeAlgo(IAlgoCache newAlgo)
	{
		algo = newAlgo;
		singleton = null;
		singleton = new CacheUnitService<>();
	}
	
	/**
	 * Change capacity of the cache unit
	 * @param cap
	 */
	
	public static void changeCapcity(int cap)
	{
		capacity = cap;
		singleton = null;
		singleton = new CacheUnitService<>();
	}
	
	
	///Update cacheUnit by removing old data and insert updated data
	public boolean update(DataModel<T>[] dataModels)
	{
		if (dataModels.length > 0)
		{	
			cacheUnit.putDataModels(dataModels);
			
			return true;
		}
		
		return false;
	}
	
	//Remove specified datamodels from cacheUnit
	public boolean delete(DataModel<T>[] dataModels)
	{
		if (dataModels.length > 0)
		{
			Long[] ids = new Long[dataModels.length];
			
			for (int i = 0; i < dataModels.length; i++)
				ids[i] = dataModels[i].getDataModelId();

			cacheUnit.removeDataModels(ids);
			
			return true;
		}
		
		return false;
	}

	//Get list of specified datamodels from cacheUnit
	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		if (dataModels.length > 0)
		{
			Long[] ids = new Long[dataModels.length];
			
			for (int i = 0; i < dataModels.length; i++)
				ids[i] = dataModels[i].getDataModelId();

			return cacheUnit.getDataModels(ids);
		}
		
		return null;
	}
	
	/** 
	 * Return HashMap of relevant data of the cache unit
	 * @return
	 */
	public HashMap<String, Integer> getCacheSummary()
	{
		HashMap<String, Integer> summary = (HashMap<String, Integer>) cacheUnit.getCacheSummary().clone();
		summary.put("capacity", algo.getCapacity());
		summary.put("available", algo.getAvailableSlotsCount());
		return summary;
	}
}