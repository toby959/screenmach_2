package com.toby959.screenmatch_2.main;

import java.util.Arrays;
import java.util.List;

public class StreamExamples {

    public void showExample() {
        List<String> names = List.of("Lionel Messi", "Zinedine Zidane", "Neymar Jr.", "Kylian Mbappé", "Andrés Iniesta");

        names.stream().sorted().limit(4).filter(n -> n.startsWith("L")).map(String::toUpperCase).forEach(System.out::println);
    }
}
