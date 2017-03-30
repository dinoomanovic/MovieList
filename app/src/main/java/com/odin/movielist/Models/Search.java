package com.odin.movielist.Models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by mobil on 16.03.2017.
 */

public class Search implements Serializable{
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("imdbID")
    @Expose
    private String imdbID;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Poster")
    @Expose
    private String poster;
    private final static long serialVersionUID = -5694370592020319577L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Search() {
    }

    /**
     *
     * @param title
     * @param poster
     * @param year
     * @param imdbID
     * @param type
     */
    public Search(String title, String year, String imdbID, String type, String poster) {
        super();
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

}
