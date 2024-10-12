package com.toby959.screenmatch_2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(@JsonAlias("Title") String title,
                         Integer totalSeasons,
                         @JsonAlias("imdbRating") String evaluation) {
}
// @JsonAlias read
// @JsonProperty read, write