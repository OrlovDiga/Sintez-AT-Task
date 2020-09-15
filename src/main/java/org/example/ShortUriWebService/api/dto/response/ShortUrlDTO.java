package org.example.ShortUriWebService.api.dto.response;

/**
 * @author Orlov Diga
 */
public class ShortUrlDTO {

    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortUrlDTO that = (ShortUrlDTO) o;

        return link != null ? link.equals(that.link) : that.link == null;
    }

    @Override
    public int hashCode() {
        return link != null ? link.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShortUrlDTO{" +
                "link='" + link + '\'' +
                '}';
    }
}
