package io.github.stavshamir.types;

import java.util.UUID;

public class Track {

    private UUID id;
    private int secondsListened;
    private boolean isInstrumental;

    public Track() {
    }

    public Track(UUID id, int secondsListened, boolean isInstrumental) {
        this.id = id;
        this.secondsListened = secondsListened;
        this.isInstrumental = isInstrumental;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSecondsListened(int secondsListened) {
        this.secondsListened = secondsListened;
    }

    public void setIsInstrumental(boolean instrumental) {
        isInstrumental = instrumental;
    }

    public UUID getId() {
        return id;
    }

    public int getSecondsListened() {
        return secondsListened;
    }

    public boolean getIsInstrumental() {
        return isInstrumental;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", secondsListened=" + secondsListened +
                ", isInstrumental=" + isInstrumental +
                '}';
    }

}
