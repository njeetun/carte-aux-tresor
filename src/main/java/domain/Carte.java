package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Une carte contient plusieurs case dans laquelle peut se trouver un objet de type Montagne, Tresor ou Aventurier
 */
public class Carte {

    private Position dimension;

    //Une carte contient plusieurs case
    private Map<Position, Case> caseMap = new HashMap<>();

    private List<Aventurier> aventurierList = new ArrayList<>();

    public Carte() {

    }

    public Carte(Position dimension, Map<Position, Case> caseMap, List<Aventurier> aventurierList) {
        this.dimension = dimension;
        this.caseMap = caseMap;
        this.aventurierList = aventurierList;
    }

    public Position getDimension() {
        return dimension;
    }

    public void setDimension(Position dimension) {
        this.dimension = dimension;
    }

    public Map<Position, Case> getCaseMap() {
        return caseMap;
    }

    public void setCaseMap(Map<Position, Case> caseMap) {
        this.caseMap = caseMap;
    }

    public List<Aventurier> getAventurierList() {
        return aventurierList;
    }

    public void setAventurierList(List<Aventurier> aventurierList) {
        this.aventurierList = aventurierList;
    }
}
