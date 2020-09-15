package org.example.ShortUriWebService.service.impl;

import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.repo.UrlEntityDAO;
import org.example.ShortUriWebService.util.LFUCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

/**
 * @author Orlov Diga
 */
@RunWith(MockitoJUnitRunner.class)
public class UrlEntityServiceImplTest {

    @Mock
    private UrlEntityDAO entityDAO;
    @Mock
    private LFUCache cache;
    @Mock
    private CallCountServiceImpl callCountService;

    @InjectMocks
    private UrlEntityServiceImpl service;

    private UrlEntity entity;

    @Before
    public void setUp() {
        this.entity = new UrlEntity();
        entity.setId(1L);
        entity.setOriginalUrl("http://localhost:8080/veryLongUrlVeryUrl3");
        entity.setShortUrl("short");

    }

    @Test
    public void findByShortUrlFromCacheTestSuccess() {
        doReturn(entity.getOriginalUrl())
                .when(cache)
                .get(entity.getShortUrl());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        doNothing()
                .when(callCountService)
                .updateCount(captor.capture());

        String actualString = service.findByShortUrl(entity.getShortUrl());

        assertEquals(
                entity.getShortUrl(),
                captor.getValue());
        assertEquals(
                entity.getOriginalUrl(),
                actualString);
    }

    @Test
    public void findByShortUrlFromDBTestSuccess() {
        doReturn(null)
                .when(cache)
                .get(entity.getShortUrl());
        doReturn(entity)
                .when(entityDAO)
                .findByShortUrl(entity.getShortUrl());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        doNothing()
                .when(callCountService)
                .updateCount(captor.capture());

        String actualString = service.findByShortUrl(entity.getShortUrl());

        assertEquals(
                entity.getShortUrl(),
                captor.getValue());
        assertEquals(
                entity.getOriginalUrl(),
                actualString);
    }

    @Test
    public void findByShortUrlTestFailed() {
        doReturn(null)
                .when(cache)
                .get(entity.getShortUrl());
        doReturn(null)
                .when(entityDAO)
                .findByShortUrl(entity.getShortUrl());

        String actualString = service.findByShortUrl(entity.getShortUrl());

        assertNull(actualString);
    }
}