package org.example.ShortUriWebService.repo;

import com.sun.istack.NotNull;
import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.api.dto.response.UrlEntityWithRankDTO;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Orlov Diga
 */
public interface UrlEntityDAO  {

    UrlEntity saveUrlEntity(String origUrl, Function<Integer, String> fn);
    List<UrlEntity> findAll();
    void removeUrlEntity(@NotNull final Long id);
    void updateEntitiesCount(@NotNull final Map<String, Integer> callCount);
    UrlEntity findByShortUrl(@NotNull final String shortUrl);
    UrlEntity findById(Long id);
    UrlEntityWithRankDTO findByShortUrlWithRank(String shortUrl);
    List<UrlEntityWithRankDTO> findAllByPageAndCount(int page, int count);

    }
