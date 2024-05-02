package moviesAPI.moviesAPI.principal;

import moviesAPI.moviesAPI.Model.Episode;
import moviesAPI.moviesAPI.Model.EpisodeData;
import moviesAPI.moviesAPI.Model.SeasonData;
import moviesAPI.moviesAPI.Model.SerieData;
import moviesAPI.moviesAPI.Service.ConsumeAPI;
import moviesAPI.moviesAPI.Service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner reader = new Scanner(System.in);
    private ConsumeAPI consume = new ConsumeAPI();
    private ConvertData converter = new ConvertData();
    private final String ADRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f469a9a5";

    public void showMenu(){

        System.out.println("Type the name of the serie");

        var serieName = reader.nextLine();
        var json = consume.getData(ADRESS + serieName.replace(" ", "+") + API_KEY);
        SerieData data = converter.getData(json, SerieData.class);
        System.out.println(data);
        List<SeasonData> seasons = new ArrayList<>();

		for(int i = 1; i <= data.totalSeasons(); i++){
			json = consume.getData(ADRESS + serieName.replace(" ", "+") + "&season=" + i +  API_KEY);
			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

//        for(int i = 0; i <data.totalSeasons(); i++){
//            List<EpisodeData> episodeData = seasons.get(i).episodes();
//
//            for(int j = 0; j < episodeData.size(); j++){
//                System.out.println(episodeData.get(j).title());
//            }
//        }

        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));
        seasons.forEach(System.out::println);

        List<EpisodeData> episodeData = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        episodeData.stream()
                .filter(e -> !e.score().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::score).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.number(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Type a part of a tytle of a episode you like");
        var partOfTitle = reader.nextLine();

        Optional<Episode> searchedEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(partOfTitle.toUpperCase()))
                .findFirst();

        if(searchedEpisode.isPresent()){
            System.out.println("Episode Founded!");
            System.out.println("Season " + searchedEpisode.get().getSeason());
        } else {
            System.out.println("There is no Episode with this name");
        }

//
//        System.out.println("Since which yar you want to see the episodes");
//
//        var year  = reader.nextInt();
//        reader.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year,1,1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodes.stream()
//                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season:" + e.getSeason() +
//                        "Episode: " + e.getTitle() +
//                        "Release Date:" + e.getReleaseDate().format(formatter)
//                ));
    }
}
