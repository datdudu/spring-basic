package moviesAPI.moviesAPI.principal;

import moviesAPI.moviesAPI.Model.*;
import moviesAPI.moviesAPI.Service.ConsumeAPI;
import moviesAPI.moviesAPI.Service.ConvertData;
import moviesAPI.moviesAPI.repository.SerieRepository;

import java.awt.desktop.PreferencesEvent;
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
    private List<SerieData> serieData = new ArrayList<>();

    private SerieRepository repository;

    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repository){
        this.repository = repository;
    }

    public void showMenu(){
        var option = -1;

        while(option != 0){
            var menu = """
                    1 - Search Series
                    2 - Search Episodes
                    3 - List Searched series
                    4 - Search Serie By Name
                    5 - Search Serie By Actor
                    6 - Top 5 Series
                    7 - Search Serie by Category
                    0 - Quit Menu
                    """;

            System.out.println(menu);

            option = reader.nextInt();
            reader.nextLine();

            switch (option) {
                case 1:
                    searchSerieWeb();
                    break;
                case 2:
                    searchEpisodeBySerie();
                    break;
                case 3:
                    showSearchedSeries();
                    break;
                case 4: 
                    searchSerieByName();
                    break;
                case 5:
                    searchSerieByActor();
                    break;
                case 6:
                    searchTopFiveSeries();
                    break;
                case 7:
                    searchSeriesByCategory();
                    break;
                case 0:
                    System.out.println("Exiting menu");
                    break;
                default:
                    System.out.println("Invalid Optiton");
            }
        }
    }

    private void searchSeriesByCategory() {
        System.out.println("Which serie's genre you want to search?");
        var nameGenre = reader.nextLine();
        Category category = Category.fromString(nameGenre);
        List<Serie> seriesByCategory = repository.findByGenre(category);

        System.out.println("Categry choose series: " + nameGenre);
        seriesByCategory.forEach(System.out::println);

    }

    private void searchTopFiveSeries() {

        List<Serie> seriesTop = repository.findTop5ByOrderByScore();
        System.out.println("The top five series are");
        seriesTop.forEach(s ->
                System.out.println(s.getTitle() + " score: " + s.getScore()));
    }

    private void searchSerieByActor() {
        System.out.println("Choose a serie by actor name: ");
        var actorName = reader.nextLine();

        List<Serie> foundSeries = repository.findByActorsContainingIgnoreCase(actorName);
        System.out.println("Series that the actor " + actorName + " worked: ");
        foundSeries.forEach(s ->
                System.out.println(s.getTitle()));
    }

    private void searchSerieByName() {
        System.out.println("Choose a serie by name: ");
        var serieName = reader.nextLine();

        Optional<Serie> searchedSerie = repository.findByTitleContainingIgnoreCase(serieName);

        if(searchedSerie.isPresent()){
            System.out.println("Serie Data: " + searchedSerie.get());
        }else{
            System.out.println("Serie Not Found");
        }
    }

    private void searchSerieWeb(){
        SerieData data = getSeriesData();
        Serie serie = new Serie(data);
//        serieData.add(data);
        repository.save(serie);
        System.out.println(data);
    }

    private SerieData getSeriesData() {
        System.out.println("Type the name of the serie");
        var serieName = reader.nextLine();
        var json = consume.getData(ADRESS + serieName.replace(" ", "+") + API_KEY);
        SerieData data = converter.getData(json, SerieData.class);

        return data;
    }

    private void searchEpisodeBySerie(){
        showSearchedSeries();

        System.out.println("Choose a serie by name");
        var serieName = reader.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitle().toLowerCase().contains((serieName.toLowerCase())))
                .findFirst();

        if(serie.isPresent()){
            var serieFound = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for(int i = 1; i <= serieFound.getTotalSeasons(); i++){
                var json = consume.getData(ADRESS + serieFound.getTitle().replace(" ", "+") + "&season=" + i +  API_KEY);
                SeasonData seasonData = converter.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }

            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                    .map(e -> new Episode(d.number(), e)))
                    .collect(Collectors.toList());

            serieFound.setListOfEpisodes(episodes);

            repository.save(serieFound);
        }else{
            System.out.println("Serie not found");
        }

    }

    private void showSearchedSeries(){
        series = repository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }
}
