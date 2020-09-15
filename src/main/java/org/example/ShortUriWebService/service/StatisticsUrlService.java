package org.example.ShortUriWebService.service;

import org.example.ShortUriWebService.api.dto.response.UrlEntityWithRankDTO;

import java.util.List;

/**
 * @author Orlov Diga
 */
public interface StatisticsUrlService {

    UrlEntityWithRankDTO findByShortUrlWithRank(String shortUrl);
    List<UrlEntityWithRankDTO> getAllByPageAndCount(int page, int count);

}
