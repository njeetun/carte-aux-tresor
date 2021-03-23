package domain;

/**
 * Un trésor est une case sur la carte avec une position et un nombre de trésor
 */
public class Tresor extends Case {

    private int nbTresor;

    public Tresor(Position position, int nbTresor) {
        super(position);
        this.nbTresor = nbTresor;
    }

    public int getNbTresor() {
        return nbTresor;
    }

    public void setNbTresor(int nbTresor) {
        this.nbTresor = nbTresor;
    }
}
