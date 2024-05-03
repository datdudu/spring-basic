package moviesAPI.moviesAPI.repository;

import moviesAPI.moviesAPI.Model.Category;
import moviesAPI.moviesAPI.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);
    List<Serie> findByActorsContainingIgnoreCase(String actorName);

    List<Serie> findTop5ByOrderByScore();

    List<Serie> findByGenre(Category category);
}
