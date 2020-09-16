package org.example.ShortUriWebService.api.controller;

import com.sun.jersey.spi.resource.Singleton;
import org.example.ShortUriWebService.api.dto.request.OriginalUrlDTO;
import org.example.ShortUriWebService.api.dto.response.ShortUrlDTO;
import org.example.ShortUriWebService.domain.UrlEntity;
import org.example.ShortUriWebService.service.UrlEntityService;
import org.example.ShortUriWebService.service.impl.UrlEntityServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Orlov Diga
 */
@Singleton
@Path("/")
public class ShortUrlController {

    private UrlEntityService urlEntityService;

    public ShortUrlController() {
        this.urlEntityService = new UrlEntityServiceImpl();
    }

    @Path("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response getShortUrl(OriginalUrlDTO urlDTO) {
        UrlEntity savedUrlEntity =
                urlEntityService.saveUrlEntity(urlDTO.getOriginal());

        ShortUrlDTO shortUrlDTO = new ShortUrlDTO();
        shortUrlDTO.setLink("/l/" + savedUrlEntity.getShortUrl());

        return Response.ok(shortUrlDTO).build();
    }

    @GET
    @Path("/l/{short-url}")
    public Response redirectToOriginalUrl(@PathParam("short-url") final String shortUrl) {

        String origUrl = urlEntityService.findByShortUrl(shortUrl);

        try {
            return (origUrl == null) ?
                    Response.status(400).build() :
                    Response.temporaryRedirect(new URI(origUrl)).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Wrong format original URL.").build();
        }
    }
}
