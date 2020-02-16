package com.lacsmx.chargingsessionstore.service;

import java.util.Collection;

/**
 * Interface for a CRU API
 *
 * @author CS Luis Date: Jan 18, 2020
 */
public interface GeneralServiceInterface<T> {
    public T create(T newRecord);
    public T update(String id, T updatedRecord);
    public T getById(String id);
    public Collection<T> getAll();
}
