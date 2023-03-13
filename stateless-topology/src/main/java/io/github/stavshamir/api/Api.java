package io.github.stavshamir.api;

import io.github.stavshamir.types.Track;
import io.github.stavshamir.types.feelings.*;

import java.util.List;

public class Api {
    public static List<Feeling> getFeelingsFromLyrics(String lyrics) {
        return List.of(new Anger(), new Sadness(), new Happiness(), new Love(), new Fear());
    }

    public static List<Feeling> getFeelingsById(Track track) {
        return List.of(new Anger(), new Sadness(), new Happiness(), new Love(), new Fear());
    }

    public static String getLyrics(Track track) {
        return "";
    }
}
