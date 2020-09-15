package org.example.ShortUriWebService.service;

/**
 * @author Orlov Diga
 */
public interface CallCountService {

    void updateDB();
    void updateCount(String key);
    void clearCache();
}
