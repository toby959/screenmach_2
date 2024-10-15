package com.toby959.screenmatch_2.model;

import java.time.LocalDate;

public class Episode {

    private Integer season;

    private String title;

    private Integer numberEpisode;

    private Double evaluation;

    private LocalDate releaseDate;

    public Episode(Integer number, DataEpisode episode) {
        this.season = number;
        this.title = episode.title();
        this.numberEpisode = episode.numberEpisode();

        try {
        if (episode.evaluation() != null && !episode.evaluation().equals("N/A") && !episode.evaluation().equals("null")) {
            this.evaluation = Double.valueOf(episode.evaluation());
        } else {
            this.evaluation = 0.0; // Default value if evaluation is invalid
        }
    } catch (NumberFormatException e) {
        this.evaluation = 0.0; // Default value in case of error
    }

    // Date handling with validation
        if (episode.releaseDate() != null && !episode.releaseDate().equals("N/A")) {
        this.releaseDate = LocalDate.parse(episode.releaseDate());
    } else {
        this.releaseDate = null; // Date handling with validation
    }
}

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", evaluation=" + evaluation +
                ", releaseDate=" + releaseDate;

    }
}
