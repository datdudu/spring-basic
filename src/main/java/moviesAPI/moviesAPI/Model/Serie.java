package moviesAPI.moviesAPI.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;

    private Integer totalSeasons;
    private Double score;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String actors;

    private String poster;
    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> listOfEpisodes = new ArrayList<>();

    public Serie() {

    }

    @Override
    public String toString() {
        return  "genre=" + genre +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", score=" + score +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\''+
                ", episodes=" + listOfEpisodes + '\'';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episode> getListOfEpisodes() {
        return listOfEpisodes;
    }

    public void setListOfEpisodes(List<Episode> listOfEpisodes) {
        listOfEpisodes.forEach(e -> e.setSerie(this));
        this.listOfEpisodes = listOfEpisodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Serie(SerieData serieData){
        this.title = serieData.title();
        this.totalSeasons = serieData.totalSeasons();
        this.score = OptionalDouble.of(Double.valueOf(serieData.score())).orElse(0);
        this.genre = Category.fromString(serieData.genre().split(",")[0].trim());
        this.actors = serieData.actors();
        this.poster = serieData.poster();
        this.sinopse = serieData.sinopse();

    }
}
