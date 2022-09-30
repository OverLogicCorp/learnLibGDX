package com.mygdx.game.utils;

public enum Learn {
    LEARN_1("The world, bodies, figures and fixtures"),
    LEARN_2("Body types: dynamic, static, kinematic"),
    LEARN_3("Friction, density and restitution"),
    LEARN_4("Forces, impulses and linear velocity"),
    LEARN_5("Bodies and sprites"),
    LEARN_6("Collisions"),
    LEARN_7("Collisions with animated sprites");

    public final String name;

    Learn(String label) {
        this.name = label;
    }

}
