package moviesAPI.moviesAPI.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
@Table
@Entity(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer season;
    private String title;
    private Integer numberOfEpisode;
    public Double score;
    private LocalDate releaseDate;

    @ManyToOne
    private Serie serie;

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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getNumberOfEpisode() {
        return numberOfEpisode;
    }

    public void setNumberOfEpisode(Integer numberOfEpisode) {
        this.numberOfEpisode = numberOfEpisode;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Episode() {

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
