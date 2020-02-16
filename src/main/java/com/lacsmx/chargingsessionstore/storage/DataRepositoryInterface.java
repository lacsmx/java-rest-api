package com.lacsmx.chargingsessionstore.storage;

import java.util.List;
/**
 * Interface for any kind of DataRepository
 * contract for save, update, create, search in every implementation
 * Inmemory / DB / files
 * depends on implementation
 *
 *
 * @author CS Luis Date: Jan 18, 2020
 * @param <T>
 */
public interface DataRepositoryInterface<T> {
    public void connect();
    public T save(String id, T newRecord);
    public T update(String id, T newRecord);
    public void delete(String id);
    public T search(String id);
    public List<T> getAll();
}
