package com.example.chordify;

public class FavouritesRecycler {

    private String mode;
    private String key;
    private String progression;

    private FavouritesRecycler() {}

    private FavouritesRecycler(String mode, String key, String progression) {
        this.mode= mode;
        this.key = key;
        this.progression = progression;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }



}
