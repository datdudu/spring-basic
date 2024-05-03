package moviesAPI.moviesAPI.repository;

import moviesAPI.moviesAPI.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
