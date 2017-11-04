
package com.christiankula.boatstagram.feed.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo {

    @JsonProperty("has_next_page")
    private boolean hasNextPage;
    @JsonProperty("end_cursor")
    private String endCursor;

    @JsonProperty("has_next_page")
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    @JsonProperty("has_next_page")
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    @JsonProperty("end_cursor")
    public String getEndCursor() {
        return endCursor;
    }

    @JsonProperty("end_cursor")
    public void setEndCursor(String endCursor) {
        this.endCursor = endCursor;
    }

}
