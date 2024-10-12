package com.toby959.screenmatch_2;

import com.toby959.screenmatch_2.main.Main;
import com.toby959.screenmatch_2.main.StreamExamples;
import com.toby959.screenmatch_2.model.DataEpisode;
import com.toby959.screenmatch_2.model.DataSeason;
import com.toby959.screenmatch_2.model.DataSeries;
import com.toby959.screenmatch_2.service.ConsumeAPI;
import com.toby959.screenmatch_2.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Screenmatch2Application implements CommandLineRunner {

//	@Autowired
//    private ConsumeAPI consumeAPI;

    public static void main(String[] args) {
		SpringApplication.run(Screenmatch2Application.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
/*
		var consumeApi = new ConsumeAPI();
		var json = consumeAPI.getData("https://www.omdbapi.com/?t=game+of+thrones&apikey=2731cf3c");

//        var json = consumeAPI.getData("https://cofee.alexflipnote.dev/random.json");
		System.out.println(json);
//############ ONE RECORD ###########################
		ConvertData convert = new ConvertData();
		var data = convert.getData(json, DataSeries.class);
		System.out.println(data);
//############ TWO RECORD ###########################
		json = consumeApi.getData("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=2731cf3c");
		var inform = convert.getData(json, DataEpisode.class);
		System.out.println(inform);
//############ TREE RECORD ##########################
		List<DataSeason> seasons = new ArrayList<>();
		for (int i = 1; i <= data.totalSeasons(); i++) {
			json = consumeApi.getData("https://www.omdbapi.com/?t=game+of+thrones&Season=" + i + "&apikey=2731cf3c");

			var dataseasons = convert.getData(json, DataSeason.class);
			seasons.add(dataseasons);
		}
		seasons.forEach(System.out::println);

||||||||||||||||||||||  example two   |||||||||||||||||||||||||||||||||||||*/

		Main main = new Main();
		main.showTheMenu();
//||||||||||||||||||||  example tree  ||||||||||||||||||||||||
		//StreamExamples examples = new StreamExamples();
		//examples.showExample();
	}
}


