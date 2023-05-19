package io.github.stavshamir.types.feelings;

import java.util.Random;

public abstract class Feeling {

    public float getValue() {
        return new Random().nextFloat();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{value=" + getValue() + "}";
    }

}
