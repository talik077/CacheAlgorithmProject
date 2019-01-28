package com.hit.memory;

import com.hit.dm.DataModel;

import java.util.ArrayList;
import java.util.HashMap;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;


public class CacheUnit<T>
{
	
	protected IAlgoCache<Long, DataModel<T>> m_IAlgoCache;
	
	public CacheUnit(IAlgoCache<Long,DataModel<T>> algo, IDao<Long,DataModel<T>> dao) 
	{
		this.m_IAlgoCache = algo;	
	}
	
	public DataModel<T>[] getDataModels(Long[] ids)
	{
		ArrayList<T> list = new ArrayList<T>();
		for (long id : ids)
		{
			T temp = (T)this.m_IAlgoCache.getElement(id);
		 	list.add(temp);
		}
		
		DataModel<T>[] ts = (DataModel<T>[])new Object[list.size()];
		ts = list.toArray(ts);
	    return ts;
	}
	
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels)
	{
		HashMap<Long, DataModel<T>> l = new HashMap<Long, DataModel<T>>();
		for (DataModel<T> temp : datamodels)
		{
			long Id = temp.getDataModelId();
			l.put(Id, temp);
			
			DataModel<T> removedElement = this.m_IAlgoCache.putElement(Id, temp);
			long removedElementId = removedElement.getDataModelId();
			if (l.containsKey(removedElementId)) 
			{
				l.remove(removedElementId);
			}
		}
		
		DataModel<T>[] array = (DataModel<T>[]) l.values().toArray();
		return array;
	}
	
	public void removeDataModels(Long[] ids)
	{
		for (Long id : ids)
		{
			this.m_IAlgoCache.removeElement(id);
		}
	}
}
