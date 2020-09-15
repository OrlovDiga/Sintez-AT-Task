package org.example.ShortUriWebService.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Orlov Diga
 */
@Entity
public class UrlEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String originalUrl;
    @Column
    private String shortUrl;
    @Column
    private int callCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlEntity entity = (UrlEntity) o;

        if (callCount != entity.callCount) return false;
        if (id != null ? !id.equals(entity.id) : entity.id != null) return false;
        if (originalUrl != null ? !originalUrl.equals(entity.originalUrl) : entity.originalUrl != null) return false;
        return shortUrl != null ? shortUrl.equals(entity.shortUrl) : entity.shortUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (originalUrl != null ? originalUrl.hashCode() : 0);
        result = 31 * result + (shortUrl != null ? shortUrl.hashCode() : 0);
        result = 31 * result + callCount;
        return result;
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", callCount=" + callCount +
                '}';
    }
}

