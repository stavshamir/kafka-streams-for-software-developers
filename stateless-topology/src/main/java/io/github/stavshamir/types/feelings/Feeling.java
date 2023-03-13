package io.github.stavshamir.types.feelings;

import java.util.Random;

public abstract class Feeling {

    public float getValue() {
        return new Random().nextFloat();
    }

}
