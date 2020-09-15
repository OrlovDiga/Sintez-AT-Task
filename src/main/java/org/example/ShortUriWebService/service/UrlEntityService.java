package org.example.ShortUriWebService.service;

import org.example.ShortUriWebService.domain.UrlEntity;

/**
 * @author Orlov Diga
 */
public interface UrlEntityService {

    String findByShortUrl(String shortUrl);
    UrlEntity saveUrlEntity(String origUrl);
}
