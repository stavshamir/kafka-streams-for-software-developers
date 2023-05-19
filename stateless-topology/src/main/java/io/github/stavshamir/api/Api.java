package io.github.stavshamir.api;

import io.github.stavshamir.types.feelings.*;

import java.util.List;
import java.util.UUID;

public class Api {

    public static String getLyrics(UUID trackId) {
        return "mock lyrics for track id " + trackId;
    }

    public static List<Feeling> getFeelingsFromLyrics(String lyrics) {
        return List.of(new Anger(), new Sadness(), new Happiness(), new Love(), new Fear());
    }

    public static List<Feeling> getFeelingsById(UUID trackId) {
        return List.of(new Anger(), new Sadness(), new Happiness(), new Love(), new Fear());
    }

}
