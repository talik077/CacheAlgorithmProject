package com.hit.memory;

import com.hit.dm.DataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;

/***
 * 
 * @author Shahar and Tal
 * 
 * A Class that work with our CacheUnit and DaoFile, 
 * Save things in Cache Unit and Whenever we pull DataModels out of the cache unit because lack of capacity
 * The Dao will have the models in a file. 
 * 
 * @param <T> type of data
 */
public class CacheUnit<T> {
	
	/***
	 * The Algorithm implementation of the Cache Unit.
	 */
	protected IAlgoCache<Long, DataModel<T>> m_IAlgoCache;
	
	/***
	 * The dao implementation 
	 */
	protected IDao<Long, DataModel<T>> m_Dao;
	
	/***
	 * Hold info about the amount of get, update, remove, swaps used in the Cache unit.
	 */
	private HashMap<String, Integer> counter;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
		this.m_IAlgoCache = algo;
		this.m_Dao = dao;
		this.counter = new HashMap<String, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("get", 0);
				put("update", 0);
				put("remove", 0);
				put("swap", 0);
			}
		};
	}
	
	/**
	 * 
	 * @return  info about the amount of get, update, remove, swaps used in the Cache unit.
	 */
	public HashMap<String, Integer> getCacheSummary() 
	{
		return counter;
	}
	
	/**
	 * 
	 * @param key -One of analytic key(get, update, remove, swap) to increment
	 */
	private void IncrementCounter(String key) {
		Integer currentValue = this.counter.containsKey(key) ? this.counter.get(key) : 0;
		this.counter.put(key, currentValue + 1);
	}
	
	/**
	 * 
	 * @param ids
	 * @return retrive the models from the cache unit or from the dao.
	 */
	public DataModel<T>[] getDataModels(Long[] ids) {
		ArrayList<DataModel<T>> list = new ArrayList<DataModel<T>>();
		for (long id : ids) {
			DataModel<T> temp = (DataModel<T>) this.m_IAlgoCache.getElement(id);
			if (temp != null) {
				this.IncrementCounter("get");
				list.add(temp);
			} else {
				DataModel<T> storedValue = checkStoredDaoCache(id);
				if (storedValue != null)
					list.add(storedValue);
			}
		}

		DataModel[] dataArray = new DataModel[list.size()];
		list.toArray(dataArray);
		return dataArray;
	}
	
	/**
	 * 
	 * @param id
	 * @return return the model from the dao, and increase the amount of swaps if necessary 
	 */
	private DataModel<T> checkStoredDaoCache(Long id) {

		DataModel<T> storedValue = this.m_Dao.find(id);
		if (storedValue != null) {
			DataModel<T> removedCachedValue = this.m_IAlgoCache.putElement(id, storedValue);
			if (removedCachedValue != null) { // algo cache is full
				this.IncrementCounter("swap");
				this.m_Dao.save(removedCachedValue);
			}
			this.m_Dao.delete(storedValue);
		}
		return storedValue;
	}

	/**
	 * 
	 * @param datamodels
	 * @return save the model in the cache unit.
	 */
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
		HashMap<Long, DataModel<T>> l = new HashMap<Long, DataModel<T>>();
		for (DataModel<T> temp : datamodels) {
			long Id = temp.getDataModelId();
			l.put(Id, temp);
			this.IncrementCounter("update");
			
			DataModel<T> removedElement = this.m_IAlgoCache.putElement(Id, temp);
			if (removedElement != null) {
				this.IncrementCounter("swap");
				long removedElementId = removedElement.getDataModelId();
				this.m_Dao.save(removedElement);
				// l.remove(removedElementId);
			}
		}

		DataModel[] dataArray = new DataModel[l.size()];
		l.values().toArray(dataArray);
		return dataArray;
	}

	/**
	 * Remove the models from Cache unit
	 * @param ids
	 */
	public void removeDataModels(Long[] ids) {
		for (Long id : ids) {
			if (this.m_IAlgoCache.getElement(id) != null) {
				this.IncrementCounter("remove");
				this.m_IAlgoCache.removeElement(id);
			}
		}
	}
}
