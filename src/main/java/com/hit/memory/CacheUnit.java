package com.hit.memory;

import com.hit.dm.DataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;

public class CacheUnit<T> {

	protected IAlgoCache<Long, DataModel<T>> m_IAlgoCache;
	protected IDao<Long, DataModel<T>> m_Dao;
	private static Map<String, Integer> counter;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
		this.m_IAlgoCache = algo;
		this.m_Dao = dao;
		this.counter = new HashMap<String, Integer>() {
			{
				put("get", 0);
				put("update", 0);
				put("remove", 0);
				put("swap", 0);
			}
		};
	}

	private void IncrementCounter(String key) {
		Integer currentValue = this.counter.containsKey(key) ? this.counter.get(key) : 0;
		this.counter.put(key, currentValue + 1);
	}

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

	public void removeDataModels(Long[] ids) {
		for (Long id : ids) {
			if (this.m_IAlgoCache.getElement(id) != null) {
				this.IncrementCounter("remove");
				this.m_IAlgoCache.removeElement(id);
			}
		}
	}
}
