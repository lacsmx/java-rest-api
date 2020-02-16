package com.lacsmx.chargingsessionstore.storage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements an inmemory repository for a generic Object T
 * A treemap is used to store K,T pairs
 * It provides save, update, detele, search operations over the treemap
 *
 * @author CS Luis Date: Jan 18, 2020
 * @param <T>
 */
public class InMemoryStorage<T> implements DataRepositoryInterface<T> {
    // Inmemory storage to save any object
    private SortedMap<String, T> inMemoryStorage = null;

    /**
     * Constructor initializes our inmemory storare and cast it to be safe
     */
    public InMemoryStorage(){
        inMemoryStorage = Collections.synchronizedSortedMap(new TreeMap<String, T>());
    }

    @Override
    public void connect() {
        System.out.println("Connection to InMemory Storage.");
    }

    /**
     * Takes an id as K and store the object T in our treemap
     * @param id
     * @param newRecord
     * @return
     */
    @Override
    public T save(String id, T newRecord) {
        inMemoryStorage.put(id, newRecord);
        return inMemoryStorage.get(id);
    }

    /**
     * Takes an id to delete the current record in the treemap and
     * insert the new T object in it
     * @param id
     * @param newRecord
     * @return
     */
    @Override
    public T update(String id, T newRecord) {
        this.delete(id);
        this.save(id, newRecord);
        return inMemoryStorage.get(id);
    }

    /**
     * Takes an id to remove it from the treemap
     * @param id
     */
    @Override
    public void delete(String id) {
        inMemoryStorage.remove(id);
    }

    /**
     * Return the element, stored in the treemap, based on id param
     * @param id
     * @return
     */
    @Override
    public T search(String id) {
        return inMemoryStorage.get(id);
    }

    /**
     * Retrieve all the current values in the treemap as a List
     * @return
     */
    @Override
    public List<T> getAll() {
        return inMemoryStorage.values().stream().collect(Collectors.toList());
    }

}
