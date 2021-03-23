package domain;

import java.util.List;

public class Aventurier {

    private String nomAventurier;

    private Position position;

    private Orientation orientation;

    private List<String> actionList;

    private int nbTresorCollecte = 0;


    public Aventurier() {

    }

    public Aventurier(Position position, String nomAventurier, Orientation orientation, int nbTresorCollecte) {

        this.position = position;
        this.nomAventurier = nomAventurier;
        this.orientation = orientation;
        this.nbTresorCollecte = nbTresorCollecte;
    }

    public Aventurier(Position position, String nomAventurier, Orientation orientation, List<String> actionList) {

        this.position = position;
        this.nomAventurier = nomAventurier;
        this.orientation = orientation;
        this.actionList = actionList;
    }

    public String getNomAventurier() {
        return nomAventurier;
    }

    public void setNomAventurier(String nomAventurier) {
        this.nomAventurier = nomAventurier;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    public int getNbTresorCollecte() {
        return nbTresorCollecte;
    }

    public void setNbTresorCollecte(int nbTresorCollecte) {
        this.nbTresorCollecte = nbTresorCollecte;
    }
}
