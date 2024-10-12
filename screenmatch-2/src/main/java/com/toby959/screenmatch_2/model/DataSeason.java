package com.toby959.screenmatch_2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Title") String title,
                         @JsonAlias("Season") Integer number,
                         @JsonAlias("Episodes") List<DataEpisode> episodes,
                         @JsonAlias("imdbRating") String evaluation
) {
}
