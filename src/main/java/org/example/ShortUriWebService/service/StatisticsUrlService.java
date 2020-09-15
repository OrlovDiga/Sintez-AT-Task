package org.example.ShortUriWebService.service;

import org.example.ShortUriWebService.domain.UrlEntityWithRank;

import java.util.List;

/**
 * @author Orlov Diga
 */
public interface StatisticsUrlService {

    UrlEntityWithRank findByShortUrlWithRank(String shortUrl);
    List<UrlEntityWithRank> getAllByPageAndCount(int page, int count);

}
