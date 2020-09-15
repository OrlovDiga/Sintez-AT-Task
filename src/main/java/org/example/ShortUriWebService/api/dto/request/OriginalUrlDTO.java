package org.example.ShortUriWebService.api.dto.request;

/**
 * @author Orlov Diga
 */
public class OriginalUrlDTO {

    private String original;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OriginalUrlDTO that = (OriginalUrlDTO) o;

        return original != null ? original.equals(that.original) : that.original == null;
    }

    @Override
    public int hashCode() {
        return original != null ? original.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShortUrlDTO{" +
                "original='" + original + '\'' +
                '}';
    }
}
