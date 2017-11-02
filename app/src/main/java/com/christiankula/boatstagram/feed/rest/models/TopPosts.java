
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopPosts {

    @JsonProperty("nodes")
    private List<Post> posts = null;

    @JsonProperty("nodes")
    public List<Post> getPosts() {
        return posts;
    }

    @JsonProperty("nodes")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
