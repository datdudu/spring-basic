package moviesAPI.moviesAPI;

import moviesAPI.moviesAPI.Model.SerieData;
import moviesAPI.moviesAPI.Service.ConsumeAPI;
import moviesAPI.moviesAPI.Service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumeAPI consumeAPI = new ConsumeAPI();
		var json = consumeAPI.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=f469a9a5");
		System.out.println(json);
		ConvertData converter = new ConvertData();
		SerieData data = converter.getData(json, SerieData.class);
		System.out.println(data);
	}
}
