package de.minestar.Webpanel.units;

public enum UserLevel {

    DEFAULT(-1),

    PROBE(0),

    FREE(10),

    PAY(20),

    MOD(30),

    ADMIN(40);

    private final int level;

    private UserLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
