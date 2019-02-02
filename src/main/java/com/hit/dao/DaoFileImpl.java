
package com.hit.dao;

import com.hit.dm.DataModel;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shahar and Tal
 * 
 * @param <T>
 * Save data models that out of the memory to a file.
 */
public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	private final String filePath;
	private Map<Long, DataModel<T>> dataModelCache;
	private boolean toInitialize;
	private int capacity;

	/**
	 * @param filePath - file path
	 */
	public DaoFileImpl(String filePath) {
		this.filePath = filePath;
		this.dataModelCache = new HashMap();
		this.toInitialize = true;
		this.capacity = Integer.MAX_VALUE;
	}

	public DaoFileImpl(String filePath, int capacity) {
		this.filePath = filePath;
		this.dataModelCache = new HashMap();
		this.capacity = capacity;
		this.toInitialize = true;
	}
	
	/***
	 * Save the the model in a file, and also in a local HashMap
	 */
	@Override
	public void save(DataModel<T> entity) {
		try {
			if (entity != null) {
				this.dataModelCache.put(entity.getDataModelId(), entity);
				writeMapToFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * Delete the entity from the file, and also in the local HashMap
	 */
	@Override
	public void delete(DataModel<T> entity) throws IllegalArgumentException {
		if (entity == null) {
			throw new IllegalArgumentException();
		}

		try {
			if (entity.getDataModelId() != null) {
				this.dataModelCache.remove(entity.getDataModelId(), entity);
				writeMapToFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * Return the entity from the File/HashMap
	 */
	@Override
	public DataModel<T> find(Long id) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException("Illegal argument.");
		}else if (this.dataModelCache.containsKey(id))
			return this.dataModelCache.get(id);
		return null;
	}

	

	/**
	 * Write the current map to the datasource file
	 * 
	 * @throws IOException -
	 */
	private void writeMapToFile() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.filePath, false))) {
			outputStream.writeObject(this.dataModelCache);
		} catch (Exception ex) {

		}
	}

}
