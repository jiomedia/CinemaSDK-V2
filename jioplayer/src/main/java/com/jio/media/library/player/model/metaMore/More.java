
package com.jio.media.library.player.model.metaMore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class More {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("format")
    @Expose
    private Object format;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("year")
    @Expose
    private Object year;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("totalDuration")
    @Expose
    private String totalDuration;
    @SerializedName("srt")
    @Expose
    private String srt;
    @SerializedName("isOriginal")
    @Expose
    private boolean isOriginal;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("totalDurationString")
    @Expose
    private String totalDurationString;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Object getFormat() {
        return format;
    }

    public void setFormat(Object format) {
        this.format = format;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getYear() {
        return year;
    }

    public void setYear(Object year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getSrt() {
        return srt;
    }

    public void setSrt(String srt) {
        this.srt = srt;
    }

    public boolean isIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTotalDurationString() {
        return totalDurationString;
    }

    public void setTotalDurationString(String totalDurationString) {
        this.totalDurationString = totalDurationString;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
