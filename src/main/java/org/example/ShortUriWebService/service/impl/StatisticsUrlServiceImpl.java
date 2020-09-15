package org.example.ShortUriWebService.service.impl;

import org.example.ShortUriWebService.domain.UrlEntityWithRank;
import org.example.ShortUriWebService.repo.UrlEntityDAO;
import org.example.ShortUriWebService.repo.impl.UrlEntityDAOImpl;
import org.example.ShortUriWebService.service.CallCountService;
import org.example.ShortUriWebService.service.StatisticsUrlService;

import java.util.List;

/**
 * @author Orlov Diga
 */
public class StatisticsUrlServiceImpl implements StatisticsUrlService {

    private CallCountService callCountService;
    private UrlEntityDAO entityDAO;

    public StatisticsUrlServiceImpl() {
        this.entityDAO = new UrlEntityDAOImpl();
        this.callCountService = CallCountServiceImpl.getInstance();
    }

    @Override
    public UrlEntityWithRank findByShortUrlWithRank(String shortUrl) {
        callCountService.updateDB();
        callCountService.clearCache();
        return entityDAO.findByShortUrlWithRank(shortUrl);
    }

    @Override
    public List<UrlEntityWithRank> getAllByPageAndCount(int page, int count) {
        callCountService.updateDB();
        callCountService.clearCache();
        return entityDAO.findAllByPageAndCount(page, count);
    }
}
