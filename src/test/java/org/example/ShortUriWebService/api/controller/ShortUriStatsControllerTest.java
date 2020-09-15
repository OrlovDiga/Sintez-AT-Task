package org.example.ShortUriWebService.api.controller;

import org.example.ShortUriWebService.api.dto.response.UrlEntityWithRankDTO;
import org.example.ShortUriWebService.service.StatisticsUrlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

/**
 * @author Orlov Diga
 */
@RunWith(MockitoJUnitRunner.class)
public class ShortUriStatsControllerTest {

    @Mock
    private StatisticsUrlService statisticsUrlService;

    @InjectMocks
    private ShortUrlStatsController controller;

    private List<UrlEntityWithRankDTO> entityWithRankList;
    private UrlEntityWithRankDTO entityWithRank;
    private static final String NON_EXISTENT_URL = "nonExistentUrl";

    @Before
    public void setUp() {
        UrlEntityWithRankDTO entityWithRank1 = new UrlEntityWithRankDTO();
        entityWithRank1.setRank("1");
        entityWithRank1.setCallCount("10");
        entityWithRank1.setOriginalUrl("http://localhost:8080/veryLongUrl1");
        entityWithRank1.setShortUrl("/l/short1");

        UrlEntityWithRankDTO entityWithRank2 = new UrlEntityWithRankDTO();
        entityWithRank2.setRank("2");
        entityWithRank2.setCallCount("5");
        entityWithRank2.setOriginalUrl("http://localhost:8080/veryLongUrlLongUrl2");
        entityWithRank2.setShortUrl("/l/short2");

        UrlEntityWithRankDTO entityWithRank3 = new UrlEntityWithRankDTO();
        entityWithRank3.setRank("3");
        entityWithRank3.setCallCount("2");
        entityWithRank3.setOriginalUrl("http://localhost:8080/veryLongUrlVeryUrl3");
        entityWithRank3.setShortUrl("/l/short3");

        entityWithRankList = new ArrayList<>(Arrays.asList(
                entityWithRank1,
                entityWithRank2,
                entityWithRank3));

        entityWithRank = entityWithRank1;

        doReturn(entityWithRank)
                .when(statisticsUrlService)
                .findByShortUrlWithRank(entityWithRank.getShortUrl());
        doReturn(null)
                .when(statisticsUrlService)
                .findByShortUrlWithRank(NON_EXISTENT_URL);
        doReturn(entityWithRankList.subList(0, 1))
                .when(statisticsUrlService)
                .getAllByPageAndCount(1, 1);
        doReturn(entityWithRankList)
                .when(statisticsUrlService)
                .getAllByPageAndCount(1, 100);

    }

    @Test
    public void getStatsByShortUrlTestSuccess() {
        Response response = controller.getStatsByShortUrl(entityWithRank.getShortUrl());

        assertEquals(
                entityWithRank,
                response.getEntity());
        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus());
    }

    @Test
    public void getStatsByShortUrlTestFailed() {
        Response response = controller.getStatsByShortUrl(NON_EXISTENT_URL);

        assertEquals(
                Response.Status.BAD_REQUEST.getStatusCode(),
                response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    public void gelAllStaticTestSuccess() {
        Response response1 = controller.getAllStatic(1, 1);

        assertEquals(
                entityWithRankList.subList(0, 1),
                response1.getEntity());
        assertEquals(
                Response.Status.OK.getStatusCode(),
                response1.getStatus());

        Response response2 = controller.getAllStatic(0, 101);

        assertEquals(
                entityWithRankList,
                response2.getEntity());
        assertEquals(
                Response.Status.OK.getStatusCode(),
                response2.getStatus());
    }
}