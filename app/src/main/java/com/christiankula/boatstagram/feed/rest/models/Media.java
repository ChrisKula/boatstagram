
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Media {

    @JsonProperty("nodes")
    private List<Post> posts = null;
    @JsonProperty("count")
    private int count;
    @JsonProperty("page_info")
    private PageInfo pageInfo;

    @JsonProperty("posts")
    public List<Post> getPosts() {
        return posts;
    }

    @JsonProperty("posts")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @JsonProperty("count")
    public int getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(int count) {
        this.count = count;
    }

    @JsonProperty("page_info")
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    @JsonProperty("page_info")
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

}
