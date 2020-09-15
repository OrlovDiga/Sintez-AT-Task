package org.example.ShortUriWebService.api.dto.response;

import java.io.Serializable;

/**
 * @author Orlov Diga
 */
public class UrlEntityWithRankDTO implements Serializable {

    private String shortUrl;
    private String originalUrl;
    private String callCount;
    private String rank;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = "/l/" + shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCallCount() {
        return callCount;
    }

    public void setCallCount(String callCount) {
        this.callCount = callCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlEntityWithRankDTO that = (UrlEntityWithRankDTO) o;

        if (shortUrl != null ? !shortUrl.equals(that.shortUrl) : that.shortUrl != null) return false;
        if (originalUrl != null ? !originalUrl.equals(that.originalUrl) : that.originalUrl != null) return false;
        if (callCount != null ? !callCount.equals(that.callCount) : that.callCount != null) return false;
        return rank != null ? rank.equals(that.rank) : that.rank == null;
    }

    @Override
    public int hashCode() {
        int result = shortUrl != null ? shortUrl.hashCode() : 0;
        result = 31 * result + (originalUrl != null ? originalUrl.hashCode() : 0);
        result = 31 * result + (callCount != null ? callCount.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlEntityWithRank{" +
                "shortUrl='" + shortUrl + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", callCount='" + callCount + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
