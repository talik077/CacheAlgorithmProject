package com.hit.dm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;

import org.junit.Test;
import com.hit.dao.DaoFileImpl;

public class DaoFileTest
{
	
	private String m_filePath = "output.txt";
	
    @Test
    public void TestDaoFileSave() throws FileNotFoundException 
    {
    	DataModel<String> objFound = null;
    	DataModel<String> one = new DataModel<String>((long)1, "Avocado");
    	DataModel<String> two = new DataModel<String>((long)2, "Banana");
    	DataModel<String> three = new DataModel<String>((long)3, "Apple");
    	
    	DaoFileImpl dao = new DaoFileImpl<String>(this.m_filePath);
    	dao.save(one);
    	dao.save(two);
    	dao.save(three);
    	
    	objFound = dao.find(one.getDataModelId());
    	assertNotEquals(null, objFound);
    	
    	dao.delete(two);
    	objFound = dao.find(two.getDataModelId());
    	assertEquals(null, objFound);
    }
	
}
