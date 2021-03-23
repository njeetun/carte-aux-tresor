package domain;

/**
 * Une case peut contenir les objets de types Montagne, Trésor ou Aventurier
 */
public class Case {

    private Position position;

    public Case(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
