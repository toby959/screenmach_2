package com.toby959.screenmatch_2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(@JsonAlias("Title") String title,
                          @JsonAlias("Year") String year,
                          @JsonAlias("Runtime") String runtime,
                          @JsonAlias("Director") String director,
                          @JsonAlias("Episode") Integer numberEpisode,
                          @JsonAlias("imdbRating") String evaluation,
                          @JsonAlias("Released") String releaseDate) {
}
