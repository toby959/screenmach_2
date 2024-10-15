package com.toby959.screenmatch_2.main;

import com.toby959.screenmatch_2.model.DataSeason;
import com.toby959.screenmatch_2.model.DataSeries;
import com.toby959.screenmatch_2.model.Episode;
import com.toby959.screenmatch_2.service.ConsumeAPI;
import com.toby959.screenmatch_2.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner search = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private Dotenv dotenv = Dotenv.load(); // Load environment variables
    private final String API_KEY = "&apikey=" + dotenv.get("API_KEY"); // Get the API_KEY from the .env
    private ConvertData convert = new ConvertData();
    private List<Episode> episodes;



    public void showTheMenu() {
        while (true) {
            System.out.println("#################%%%%%%%%%%%%%%%%%%#####################");
            System.out.println("Seleccione una opción: \uD83C\uDFA5");
            System.out.println("* 1 Buscar serie \uD83E\uDDC9");
            System.out.println("* 2 Mostrar evaluaciones por temporada \uD83C\uDF40");
            System.out.println("* 3 Mostrar títulos de episodios por temporada \uD83E\uDD16");
            System.out.println("* 4 Buscar episodios por año \uD83D\uDD0D");
            System.out.println("* 5 Mostrar Top 5 episodios \uD83D\uDE0E");
            System.out.println("* 6 Buscar episodios por parte del título. Primero, asegúrate de haber buscado la serie antes!!! \uD83E\uDDE9");
            System.out.println("* 7 Salir \uD83D\uDEEB ");
            System.out.println("#################%%%%%%%%%%%%%%%%%%#####################");
            int option = search.nextInt();
            search.nextLine();

            switch (option) {
                case 1:
                    searchSeries();
                    break;
                case 2:
                    showReviewsBySeason();
                    break;
                case 3:
                    showTitlesEpisodesBySeason();
                    break;
                case 4:
                    searchEpisodesFromYear();
                    break;
                case 5:
                     showTopEpisodes();
                     break;
                case 6:
                    searchEpisodesByTitlePiece();
                    break;
                case 7:
                    System.out.println("Saliendo de la API \uD83D\uDC08\u200D⬛...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
// -- one --
    private void searchSeries() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar.");
        var seriesName = search.nextLine();
        // Encode the series name
        String encodedSearch = URLEncoder.encode(seriesName, StandardCharsets.UTF_8);
        var json = consumeAPI.getData(URL_BASE + encodedSearch + API_KEY);

        var data = convert.getData(json, DataSeries.class);
        // Validate if the data obtained is null
        if (data == null || data.title() == null || data.totalSeasons() == null) {
            System.out.println("No se encontraron resultados para la serie '" + seriesName + "'. Por favor, verifica el nombre e inténtalo de nuevo.");
            return;
        }
        System.out.println(data);

        List<DataSeason> seasons = getSeasons(seriesName, data);
        episodes = convertToEpisodes(seasons); // Save episodes for later use

        if (!episodes.isEmpty()) {
            System.out.println("Episodios encontrados. ¿Desea ver las evaluaciones por temporada? (sí/no)");
            String response = search.nextLine().trim().toLowerCase();
            if (response.equals("sí") || response.equals("si")) {
                showReviewsBySeason();
            }
        } else {
            System.out.println("No hay episodios disponibles. Por favor, busque otra serie.");
        }
    }



    private List<DataSeason> getSeasons(String seriesName, DataSeries data) {
        List<DataSeason> seasons = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            String json = consumeAPI.getData(URL_BASE + seriesName.replace(" ", "+") + "&Season=" + i + API_KEY);
            try {
                var dataseasons = convert.getData(json, DataSeason.class);
                seasons.add(dataseasons);
            } catch (Exception e) {
                System.out.println("Error al procesar los datos de la temporada " + i + ": " + e.getMessage());
            }
        }
        return seasons;
    }

    private List<Episode> convertToEpisodes(List<DataSeason> seasons) {
        return seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.number(), d)))
                .collect(Collectors.toList());
    }
// -- two --
    private void showReviewsBySeason() {
        if (episodes == null || episodes.isEmpty()) {
            System.out.println("No hay episodios disponibles. Por favor, busque una serie primero.");
            return;
        }

        Map<Integer, Double> evaluationBySeason = calculateEvaluationsBySeason(episodes);

        System.out.println("Evaluaciones por Temporada:");
        evaluationBySeason.forEach((season, average) -> {
            double roundedAverage = Math.round(average * 100.0) / 100.0;
            System.out.printf("Temporada %d: %.2f%n", season, roundedAverage);
        });
    }

    private Map<Integer, Double> calculateEvaluationsBySeason(List<Episode> episodes) {
        return episodes.stream()
                .filter(e -> e.getEvaluation() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getEvaluation)));
    }
// -- tree --
    private void showTitlesEpisodesBySeason() {
        if (episodes == null || episodes.isEmpty()) {
            System.out.println("No hay episodios disponibles. Por favor, busque una serie primero.");
            return;
        }

        // Display episode titles by season using lambda
        episodes.stream()
                .collect(Collectors.groupingBy(Episode::getSeason))
                .forEach((season, episodeList) -> {
                    System.out.printf("Temporada %d:%n", season);
                    episodeList.forEach(e -> System.out.println(e.getTitle()));
                });
    }

// -- four  --
        private void searchEpisodesFromYear() {
            if (episodes == null || episodes.isEmpty()) {
                System.out.println("No hay episodios disponibles. Por favor, busque una serie primero.");
                return;
            }

            System.out.println("Por favor indica el año a partir del cual deseas ver los episodios:");

            int year = 0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    year = search.nextInt();
                    search.nextLine();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un año válido.");
                    search.nextLine(); // Clear the incorrect entry
                }
            }

            LocalDate searchDate = LocalDate.of(year, 1, 1); // Create date from the first day of the year
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Date format

            episodes.stream()
                    .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                    .forEach(e -> System.out.printf(
                            "Temporada %d | Episodio: %s | Fecha de Lanzamiento: %s%n",
                            e.getSeason(),
                            e.getTitle(),
                            e.getReleaseDate().format(dtf)
                    ));
        }
// -- five --
            private void showTopEpisodes() {
                if (episodes == null || episodes.isEmpty()) {
                    System.out.println("No hay episodios disponibles. Por favor, busque una serie primero.");
                    return;
                }

                System.out.println("Top 5 episodios:");

                episodes.stream()
                        .filter(e -> e.getEvaluation() != null)
                        .sorted(Comparator.comparingDouble(e -> {
                            try {
                                return Double.parseDouble(String.valueOf(e.getClass())); // Convert to Double
                            } catch (NumberFormatException ex) {
                                return Double.NEGATIVE_INFINITY; // Place at the end in case of error
                            }
                        }).reversed())
                        .map(e -> e.getTitle().toUpperCase())
                        .limit(5)
                        .forEach(System.out::println);
            }
// -- six --
    private void searchEpisodesByTitlePiece() {
        if (episodes == null || episodes.isEmpty()) {
            System.out.println("No hay episodios disponibles. Por favor, busque una serie primero.");
            return;
        }

        System.out.println("Por favor escriba el título del episodio que desea ver:");
        var titlePiece = search.nextLine().trim();

        Optional<Episode> episodeSearch = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titlePiece.toUpperCase()))
                .findFirst();

        if (episodeSearch.isPresent()) {
            System.out.println("Episodio Encontrado:");
            System.out.println("Los datos son: " + episodeSearch.get());
        } else {
            System.out.println("Episodio no encontrado!!!");
        }
    }
}














