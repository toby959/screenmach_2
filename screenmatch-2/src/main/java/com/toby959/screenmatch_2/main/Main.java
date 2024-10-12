package com.toby959.screenmatch_2.main;

import com.toby959.screenmatch_2.model.DataEpisode;
import com.toby959.screenmatch_2.model.DataSeason;
import com.toby959.screenmatch_2.model.DataSeries;
import com.toby959.screenmatch_2.service.ConsumeAPI;
import com.toby959.screenmatch_2.service.ConvertData;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner search = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private Dotenv dotenv = Dotenv.load(); // Load environment variables
    private final String API_KEY = "&apikey=" + dotenv.get("API_KEY"); // Get the API_KEY from the .env
    private ConvertData convert = new ConvertData();


    public void showTheMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar.");
// -- find general data for the series --
        var seriesName = search.nextLine();
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
// -- encode the name of the series --
        String encodedSearch = URLEncoder.encode(seriesName, StandardCharsets.UTF_8);
        var json = consumeAPI.getData(URL_BASE + encodedSearch + API_KEY);

//        var json = consumeAPI.getData(URL_BASE + seriesName.replace(" ", "+") + API_KEY);
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        var data = convert.getData(json, DataSeries.class);
        System.out.println(data);
// -- getAll DataSeason --
        List<DataSeason> seasons = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = consumeAPI.getData(URL_BASE + seriesName.replace(" ", "+") + "&Season=" + i + API_KEY);
// -- print the JSON for debugging
            System.out.println("Datos de la Temporada " + i + ": " + json);
            try {
                var dataseasons = convert.getData(json, DataSeason.class);
                seasons.add(dataseasons);
            } catch (Exception e) {
            System.out.println("Error al procesar los datos de la temporada " + i + ": " + e.getMessage());
            }
        }
// -- show only episode titles by season -- for --
//        for (int i = 0; i < data.totalSeasons(); i++) {
//            List<DataEpisode> episodeList = seasons.get(i).episodes();
//            for (int j = 0; j < episodeList.size() ; j++) {
//                System.out.println(episodeList.get(j).title());
//            }
//        }
// -- show only episode titles by season -- Lambda --
        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));
    }
}

