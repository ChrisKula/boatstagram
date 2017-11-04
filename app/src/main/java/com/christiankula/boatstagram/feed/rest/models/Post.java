
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {

    @JsonProperty("comments_disabled")
    private boolean commentsDisabled;
    @JsonProperty("id")
    private String id;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;
    @JsonProperty("thumbnail_resources")
    private List<Object> thumbnailResources = null;
    @JsonProperty("is_video")
    private boolean isVideo;
    @JsonProperty("code")
    private String code;
    @JsonProperty("date")
    private int date;
    @JsonProperty("display_src")
    private String displaySrc;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("likes")
    private Likes likes;
    @JsonProperty("video_views")
    private int videoViews;

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

    @JsonProperty("thumbnail_resources")
    public List<Object> getThumbnailResources() {
        return thumbnailResources;
    }

    @JsonProperty("thumbnail_resources")
    public void setThumbnailResources(List<Object> thumbnailResources) {
        this.thumbnailResources = thumbnailResources;
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

    @JsonProperty("date")
    public int getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(int date) {
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