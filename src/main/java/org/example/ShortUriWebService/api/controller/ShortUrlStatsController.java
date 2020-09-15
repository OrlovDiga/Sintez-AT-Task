package org.example.ShortUriWebService.api.controller;

import com.sun.jersey.spi.resource.Singleton;
import org.example.ShortUriWebService.api.dto.response.UrlEntityWithRankDTO;
import org.example.ShortUriWebService.service.StatisticsUrlService;
import org.example.ShortUriWebService.service.impl.StatisticsUrlServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Orlov Diga
 */
@Singleton
@Path("/stats")
public class ShortUrlStatsController {

    private StatisticsUrlService statisticsUrlService;

    public ShortUrlStatsController() {
        this.statisticsUrlService = new StatisticsUrlServiceImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{shortUrl}")
    public Response getStatsByShortUrl(@PathParam("shortUrl") String shortUrl) {
        UrlEntityWithRankDTO entityWithRank = statisticsUrlService.findByShortUrlWithRank(shortUrl);

        return (entityWithRank == null) ?
                Response.status(Response.Status.BAD_REQUEST).build() :
                Response.ok(entityWithRank).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatic(@QueryParam("page") int page, @QueryParam("count") int count) {

        if (count > 100 || count <= 0) {
            count = 100;
        }
        if (page <= 0) {
            page = 1;
        }

        List<UrlEntityWithRankDTO> urlEntityWithRankList =
                statisticsUrlService.getAllByPageAndCount(page, count);

        return Response.ok(urlEntityWithRankList).build();
    }
}
