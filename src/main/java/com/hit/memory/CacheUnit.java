package com.hit.memory;

import com.hit.dm.DataModel;
import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;


public class CacheUnit<T>
{
	
	protected IAlgoCache<Long, DataModel<T>> m_IAlgoCache;
	
	public CacheUnit(IAlgoCache<Long,DataModel<T>> algo, IDao<Long,DataModel<T>> dao) 
	{
		this.m_IAlgoCache = algo;
	}
}
