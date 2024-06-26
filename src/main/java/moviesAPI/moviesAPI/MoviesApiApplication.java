package moviesAPI.moviesAPI;

import moviesAPI.moviesAPI.Model.EpisodeData;
import moviesAPI.moviesAPI.Model.SeasonData;
import moviesAPI.moviesAPI.Model.SerieData;
import moviesAPI.moviesAPI.Service.ConsumeAPI;
import moviesAPI.moviesAPI.Service.ConvertData;
import moviesAPI.moviesAPI.principal.Principal;
import moviesAPI.moviesAPI.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MoviesApiApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MoviesApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.showMenu();
	}
}
