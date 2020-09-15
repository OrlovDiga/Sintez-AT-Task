package org.example.ShortUriWebService.service.impl;

import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.repo.UrlEntityDAO;
import org.example.ShortUriWebService.repo.impl.UrlEntityDAOImpl;
import org.example.ShortUriWebService.service.CallCountService;
import org.example.ShortUriWebService.service.UrlEntityService;
import org.example.ShortUriWebService.util.LFUCache;
import org.example.ShortUriWebService.util.ShortURLGenerator;

import java.util.function.Function;

/**
 * @author Orlov Diga
 */
public class UrlEntityServiceImpl implements UrlEntityService {

    private UrlEntityDAO entityDAO;
    private LFUCache cache;
    private CallCountService callCountService;
    private static final int CACHE_CAPACITY = 100;

    public UrlEntityServiceImpl() {
        this.entityDAO = new UrlEntityDAOImpl();
        cache = new LFUCache(CACHE_CAPACITY);
        callCountService = CallCountServiceImpl.getInstance();
    }

    @Override
    public UrlEntity saveUrlEntity(String origUrl) {
        Function<Integer, String> fn = ShortURLGenerator::encode;

        UrlEntity entity = entityDAO.saveUrlEntity(origUrl, fn);
        cache.set(entity.getShortUrl(), entity.getOriginalUrl());

        return entity;
    }

    @Override
    public String findByShortUrl(String shortUrl) {
        String origUrl = cache.get(shortUrl);

        if (origUrl == null) {
            UrlEntity entity = entityDAO.findByShortUrl(shortUrl);
            if (entity != null) {
                origUrl = entity.getOriginalUrl();
            }
        }

        if (origUrl != null && !origUrl.isEmpty()) {
            callCountService.updateCount(shortUrl);
        }

        return origUrl;
    }
}
