
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

@Parcel
@JsonIgnoreProperties({"thumbnail_resources"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {

    @JsonProperty("comments_disabled")
    boolean commentsDisabled;
    @JsonProperty("id")
    String id;
    @JsonProperty("dimensions")
    Dimensions dimensions;
    @JsonProperty("owner")
    Owner owner;
    @JsonProperty("thumbnail_src")
    String thumbnailSrc;
    @JsonProperty("is_video")
    boolean isVideo;
    @JsonProperty("code")
    String code;
    @JsonProperty("date")
    long date;
    @JsonProperty("display_src")
    String displaySrc;
    @JsonProperty("caption")
    String caption;
    @JsonProperty("comments")
    Comments comments;
    @JsonProperty("likes")
    Likes likes;
    @JsonProperty("video_views")
    int videoViews;

    @JsonProperty("comments_disabled")
    public boolean isCommentsDisabled() {
        return commentsDisabled;
    }

    @JsonProperty("comments_disabled")
    public void setCommentsDisabled(boolean commentsDisabled) {
        this.commentsDisabled = commentsDisabled;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("dimensions")
    public Dimensions getDimensions() {
        return dimensions;
    }

    @JsonProperty("dimensions")
    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("thumbnail_src")
    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    @JsonProperty("thumbnail_src")
    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }

    @JsonProperty("is_video")
    public boolean isIsVideo() {
        return isVideo;
    }

    @JsonProperty("is_video")
    public void setIsVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the post's date as epoch timestamp
     */
    @JsonProperty("date")
    public long getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(long date) {
        this.date = date;
    }

    @JsonProperty("display_src")
    public String getDisplaySrc() {
        return displaySrc;
    }

    @JsonProperty("display_src")
    public void setDisplaySrc(String displaySrc) {
        this.displaySrc = displaySrc;
    }

    @JsonProperty("caption")
    public String getCaption() {
        return caption;
    }

    @JsonProperty("caption")
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @JsonProperty("comments")
    public Comments getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    @JsonProperty("likes")
    public Likes getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

}
