package domain;

public enum Orientation {

    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("O");

    private String id;

    Orientation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
