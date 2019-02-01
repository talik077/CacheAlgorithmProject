package com.hit.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	private String m_filePath = "output.txt";

	@Test
	public void TestCacheUnit() {
		DataModel<String> objFound = null;
		DataModel<String> one = new DataModel<String>((long) 1, "Avocado");
		DataModel<String> two = new DataModel<String>((long) 2, "Banana");
		DataModel<String> three = new DataModel<String>((long) 3, "Apple");
		DataModel<String> four = new DataModel<String>((long) 4, "Suka");

		IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<Long, DataModel<String>>(3);
		DaoFileImpl dao = new DaoFileImpl<String>(this.m_filePath);
		dao.save(one);
		dao.save(two);
		dao.save(three);

		CacheUnit<String> cache = new CacheUnit<String>(algo, dao);
		Long[] ass = new Long[] { one.getDataModelId() };
		cache.getDataModels(ass);

		objFound = dao.find(one.getDataModelId());
		assertEquals(null, objFound);

		cache.getDataModels(new Long[] { two.getDataModelId(), three.getDataModelId() });

		ArrayList<DataModel<String>> tempList = new ArrayList<DataModel<String>>();
		tempList.add(four);
		
		DataModel[] arr = new DataModel[tempList.size()];
		tempList.toArray(arr);

		cache.putDataModels(arr);
		objFound = dao.find(one.getDataModelId());

		assertNotEquals(null, objFound);
	}
}
