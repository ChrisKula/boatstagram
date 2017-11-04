
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstagramTagResult {

    @JsonProperty("tag")
    private Tag tag;

    @JsonProperty("tag")
    public Tag getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
