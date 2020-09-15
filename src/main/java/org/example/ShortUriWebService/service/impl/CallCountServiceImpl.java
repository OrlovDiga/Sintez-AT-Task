package org.example.ShortUriWebService.service.impl;

import org.example.ShortUriWebService.repo.UrlEntityDAO;
import org.example.ShortUriWebService.repo.impl.UrlEntityDAOImpl;
import org.example.ShortUriWebService.service.CallCountService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Orlov Diga
 */
public class CallCountServiceImpl implements CallCountService {

    private final Map<String, Integer> callCountCache = new ConcurrentHashMap<>();
    private UrlEntityDAO entityDAO;

    private static CallCountServiceImpl instance = new CallCountServiceImpl();

    public static CallCountServiceImpl getInstance() {
        return instance;
    }

    private CallCountServiceImpl() {
        this.entityDAO = new UrlEntityDAOImpl();
    }

    @Override
    public void updateDB() {
        entityDAO.updateEntitiesCount(callCountCache);
    }

    @Override
    public void updateCount(String key) {
        if (callCountCache.containsKey(key)) {
            callCountCache.put(key, callCountCache.get(key) + 1);
        } else {
            callCountCache.put(key, 1);
        }
    }

    @Override
    public void clearCache() {
        callCountCache.clear();
    }
}
