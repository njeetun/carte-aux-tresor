package business;

import domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarteConverter {

    private static final String DASH = "-";
    private static final String SPACE = " ";
    private static final String TREASURE_COMMENTARY = "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}";
    private static final String ADVENTURER_COMMENTARY = "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe\n" +
            "vertical} - {Orientation} - {Nb. trésors ramassés}";

    /**
     * Création de la liste de lignes de la carte
     *
     * @param carte la carte
     * @return liste de ligne
     */
    public List<String> generateFileData(Carte carte) {

        List<String> fileLines = new ArrayList<>();

        if (carte != null) {

            //information de la carte : C et dimension
            fileLines.add(generateCarteInfo(carte));

            //Information des différents objets M ou tresor
            fileLines.addAll(generateCarteObjects(carte.getCaseMap()));

            //Informations des aventuriers
            fileLines.addAll(generateAventrurierInfoList(carte.getAventurierList()));

        }

        return fileLines;

    }

    /**
     * Création de la ligne C - X - Y : information de la carte
     *
     * @param carte la carte
     * @return ligne C
     */
    public String generateCarteInfo(Carte carte) {

        return "C" +
                SPACE +
                DASH +
                SPACE +
                carte.getDimension().getX() +
                SPACE +
                DASH +
                SPACE +
                carte.getDimension().getY();

    }

    /**
     * Création d'une liste d'objets de la map
     *
     * @param caseMap map de la carte
     * @return liste de lignes
     */
    public List<String> generateCarteObjects(Map<Position, Case> caseMap) {

        List<String> objectsList = new ArrayList<>();

        for (Map.Entry<Position, Case> entry : caseMap.entrySet()) {

            if (entry.getValue() instanceof Montagne) {
                // Montagne montagne = (Montagne) entry.getValue();
                objectsList.add(generateMountainInfo((Montagne) entry.getValue()));
            } else if (entry.getValue() instanceof Tresor) {
                //commentaire du trésor
                objectsList.add(TREASURE_COMMENTARY);
                objectsList.add(generateTreasureInfo((Tresor) entry.getValue()));
            }
        }

        return objectsList;

    }

    /**
     * Création de la ligne M - X - Y : informations sur la montagne
     *
     * @param montagne la montagne
     * @return la ligne M
     */
    public String generateMountainInfo(Montagne montagne) {
        return "M" +
                SPACE +
                DASH +
                SPACE +
                montagne.getPosition().getX() +
                SPACE +
                DASH +
                SPACE +
                montagne.getPosition().getY();
    }


    /**
     * Création de la ligne T - X - Y - nbTresor: informations sur le trésor
     *
     * @param tresor le trésor
     * @return la ligne T
     */
    public String generateTreasureInfo(Tresor tresor) {
        return "T" +
                SPACE +
                DASH +
                SPACE +
                tresor.getPosition().getX() +
                SPACE +
                DASH +
                SPACE +
                tresor.getPosition().getY() +
                SPACE +
                DASH +
                SPACE +
                tresor.getNbTresor();
    }

    /**
     * Création d'une liste de ligne pour les aventurier
     *
     * @param aventurierList la liste des aventuriers
     * @return la liste de lignes
     */
    public List<String> generateAventrurierInfoList(List<Aventurier> aventurierList) {
        List<String> aventurierLines = new ArrayList<>();
        for (Aventurier aventurier : aventurierList) {
            aventurierLines.add(ADVENTURER_COMMENTARY);
            aventurierLines.add(generateAventrurierInfo(aventurier));
        }

        return aventurierLines;

    }

    /**
     * Création de la ligne A - nom - X - Y - nbTresor: informations sur l'aventurier
     *
     * @param aventurier l'aventurier
     * @return la ligne A
     */
    public String generateAventrurierInfo(Aventurier aventurier) {
        return "A" +
                SPACE +
                DASH +
                SPACE +
                aventurier.getNomAventurier() +
                SPACE +
                DASH +
                SPACE +
                aventurier.getPosition().getX() +
                SPACE +
                DASH +
                SPACE +
                aventurier.getPosition().getY() +
                SPACE +
                DASH +
                SPACE +
                aventurier.getOrientation().getId() +
                SPACE +
                DASH +
                SPACE +
                aventurier.getNbTresorCollecte();
    }
}
