package io.github.stavshamir.types;

import java.util.UUID;

public class Track {

    private final UUID id;
    private final int secondsListened;
    private final boolean isInstrumental;

    public Track(UUID id, int secondsListened, boolean isInstrumental) {
        this.id = id;
        this.secondsListened = secondsListened;
        this.isInstrumental = isInstrumental;
    }

    public UUID getId() {
        return id;
    }

    public int getSecondsListened() {
        return secondsListened;
    }

    public boolean isInstrumental() {
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
