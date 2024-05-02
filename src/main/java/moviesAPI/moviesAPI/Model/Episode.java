package moviesAPI.moviesAPI.Model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer numberOfEpisode;
    public Double score;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData){
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.numberOfEpisode = episodeData.number();
        try {
            this.score = Double.valueOf(episodeData.score());
        } catch (NumberFormatException exception){
            this.score = 0.0;
        }

        try{
            this.releaseDate = LocalDate.parse(episodeData.releaseDate());
        } catch (DateTimeParseException exception){
            this.releaseDate = null;
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

    public Integer getNumberOfEpisodes() {
        return numberOfEpisode;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisode = numberOfEpisodes;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "season=" + season +
                ", title='" + title + '\'' +
                ", numberOfEpisode=" + numberOfEpisode +
                ", score=" + score +
                ", releaseDate=" + releaseDate;
    }
}
