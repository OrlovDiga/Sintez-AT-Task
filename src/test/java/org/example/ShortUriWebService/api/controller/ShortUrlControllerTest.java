package org.example.ShortUriWebService.api.controller;

import org.example.ShortUriWebService.api.dto.request.OriginalUrlDTO;
import org.example.ShortUriWebService.api.dto.response.ShortUrlDTO;
import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.service.impl.UrlEntityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

/**
 * @author Orlov Diga
 */
@RunWith(MockitoJUnitRunner.class)
public class ShortUrlControllerTest {

    @Mock
    private UrlEntityServiceImpl urlEntityService;

    @InjectMocks
    private ShortUrlController controller;

    private UrlEntity entity;
    private static final String NON_EXISTENT_URL = "nonExistentUrl";

    @Before
    public void setUp() {
        entity = new UrlEntity();
        entity.setCallCount(1);
        entity.setShortUrl("shortUrl");
        entity.setOriginalUrl("veryLongOriginalUrl");

        doReturn(entity)
                .when(urlEntityService)
                .saveUrlEntity(anyString());
        doReturn(null)
                .when(urlEntityService)
                .findByShortUrl(NON_EXISTENT_URL);
        doReturn(entity.getOriginalUrl())
                .when(urlEntityService)
                .findByShortUrl(entity.getShortUrl());
    }

    @Test
    public void getShortUrlTestSuccess() {
        OriginalUrlDTO requestBody = new OriginalUrlDTO();
        requestBody.setOriginal(entity.getOriginalUrl());

        ShortUrlDTO expectedBody = new ShortUrlDTO();
        expectedBody.setLink("/l/" + entity.getShortUrl());

        Response response = controller.getShortUrl(requestBody);
        ShortUrlDTO actualBody = (ShortUrlDTO) response.getEntity();

        assertEquals(
                expectedBody,
                actualBody);
        assertEquals(
                Response.Status.OK.getStatusCode(),
                response.getStatus());
    }

    @Test
    public void redirectToOriginalUrlTestSuccess() throws URISyntaxException {
        Response response = controller.redirectToOriginalUrl(entity.getShortUrl());

        assertEquals(
                Response.Status.TEMPORARY_REDIRECT.getStatusCode(),
                response.getStatus());
    }

    @Test
    public void redirectToOriginalUrlTestFailed() throws URISyntaxException {
        Response response = controller.redirectToOriginalUrl(NON_EXISTENT_URL);

        assertEquals(
                Response.Status.BAD_REQUEST.getStatusCode(),
                response.getStatus());
    }
}