package business;

import domain.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuteMovesBusiness {

    private static final Logger LOGGER = Logger.getLogger(ExecuteMovesBusiness.class.getName());


    /**
     * Permet d'executer les différents movements sur la carte par aventurier
     *
     * @param carte la carte
     */
    public void executeMovementsInCarte(Carte carte) {

        for (Aventurier aventurier : carte.getAventurierList()) {

            String message = "Début du movement de l'aventurier : " + aventurier.getNomAventurier();
            LOGGER.log(Level.INFO, message);

            List<String> actionList = aventurier.getActionList();

            for (String action : actionList) {

                if (action.equals("A")) {
                    processOfMovement(carte, aventurier);
                } else if (action.equals("D")) {
                    defineOrientationWhenMovingRight(aventurier);
                    processOfMovement(carte, aventurier);
                } else if (action.equals("G")) {
                    defineOrientationWhenMovingLeft(aventurier);
                    processOfMovement(carte, aventurier);
                }
            }

            String messageAventurier = aventurier.getNomAventurier() + " " + "a collecté" + " " + aventurier.getNbTresorCollecte();
            LOGGER.log(Level.INFO, messageAventurier);

        }

    }

    /**
     * Selon l'action et l'orientation de l'aventurier, chercher la position suivante et un tresor
     *
     * @param carte      la carte
     * @param aventurier l'aventurier
     */
    public void processOfMovement(Carte carte, Aventurier aventurier) {
        Position nextPosition = defineNextPositionWhenMoving(aventurier);

        if (checkCaseLimit(carte.getDimension(), nextPosition)) {
            //vérifie si je n'ai pas d'obstacle de type autre aventurier ou montagne
            //Vérifie si j'ai un trésor et le collecter
            if (!checkObstacle(carte, nextPosition)) {

                //Si pas d'obstacle alors la position de l'aventurier devient nextPosition
                aventurier.setPosition(nextPosition);

                //Vérifie si un tresor est présent
                if (checkTreasure(carte, nextPosition)) {
                    Tresor tresorFound = (Tresor) carte.getCaseMap().get(nextPosition);
                    int nbTresor = tresorFound.getNbTresor();

                    String messageTresorFound = "Il existe" + nbTresor + "à la position : " + "(" + nextPosition.getX() + ";" + nextPosition.getY() + ")";
                    LOGGER.log(Level.INFO, messageTresorFound);

                    //Collecter le tresor et mettre à jour la map
                    ((Tresor) carte.getCaseMap().get(nextPosition)).setNbTresor(nbTresor - 1);

                    //Ajouter le nombre de tresor collecter pour l'aventurier
                    aventurier.setNbTresorCollecte(aventurier.getNbTresorCollecte() + 1);
                } else {
                    String message = "Aucun trésor présent à la position : " + "(" + nextPosition.getX() + ";" + nextPosition.getY() + ")";
                    LOGGER.log(Level.INFO, message);
                }
            } else {
                //Ce movement est ignoré car presente un obstacle
                String messageErreur = "Ce movement est innacessible car la case présente un obstacle à la position : " + "(" + nextPosition.getX() + ";" + nextPosition.getY() + ")";
                LOGGER.log(Level.WARNING, messageErreur);
            }
        } else {
            //Ce movement non accessible
            LOGGER.log(Level.WARNING, "Ce movement est impossible - le movement sera ignoré");
        }
    }


    /**
     * Vérifie si la position trouvée sur la case appartient bien dans la carte ( avec la dimension de la carte)
     *
     * @param dimensionCase dimension de la carte
     * @param currentCase   la position courante
     * @return vrai si la position appartient dans la carte
     */
    public boolean checkCaseLimit(Position dimensionCase, Position currentCase) {

        return dimensionCase.getX() > currentCase.getX() && currentCase.getX() >= 0 &&
                dimensionCase.getY() > currentCase.getY() && currentCase.getY() >= 0;

    }


    /**
     * Vérifie si un obstacle est trouvé à une position donnée sur la carte
     *
     * @param carte        la carte
     * @param nextPosition la position données
     * @return vrai si un obstacle est trouvé
     */
    public boolean checkObstacle(Carte carte, Position nextPosition) {

        //Si aventurier present - True
        //si montagne present - True
        return checkAnotherAventurierInCase(carte.getAventurierList(), nextPosition) || checkMountainPresent(carte, nextPosition);

    }

    /**
     * Vérifie si un autre aventurier est présent sur la carte à une position données
     *
     * @param aventuriers              liste d'aventuriers présents sur la carte
     * @param nextPositionOfAventurier la position données
     * @return vrai si un aventurier est trouvé
     */
    public boolean checkAnotherAventurierInCase(List<Aventurier> aventuriers, Position nextPositionOfAventurier) {

        boolean aventurierIsPresent = true;

        Aventurier aventurierPresent = aventuriers
                .stream()
                .filter(aventurier -> aventurier.getPosition().getX() == nextPositionOfAventurier.getX() && aventurier.getPosition().getY() == nextPositionOfAventurier.getY())
                .findAny()
                .orElse(null);

        if (aventurierPresent == null) {
            aventurierIsPresent = false;
        }

        return aventurierIsPresent;
    }

    /**
     * Vérifie si une montagne est présente à une position donnée
     *
     * @param carte        la carte
     * @param nextPosition la position données
     * @return vrai si une montagne est trouvée
     */
    public boolean checkMountainPresent(Carte carte, Position nextPosition) {
        return carte.getCaseMap().get(nextPosition) instanceof Montagne;
    }

    /**
     * Vérifier si à une position donnée se trouve un trésor
     *
     * @param carte        carte
     * @param nextPosition la position donnée
     * @return vrai si un tresor est trouvé
     */
    public boolean checkTreasure(Carte carte, Position nextPosition) {
        return carte.getCaseMap().get(nextPosition) instanceof Tresor;
    }


    /**
     * calcule le mouvement selon l'orientation de l'aventurier
     *
     * @param aventurier l'aventurier
     * @return la position trouvée
     */
    public Position defineNextPositionWhenMoving(Aventurier aventurier) {

        Position nextPosition = new Position();

        switch (aventurier.getOrientation()) {
            case NORTH:
                nextPosition.setX(aventurier.getPosition().getX());
                nextPosition.setY(aventurier.getPosition().getY() - 1);
                break;
            case SOUTH:
                nextPosition.setX(aventurier.getPosition().getX());
                nextPosition.setY(aventurier.getPosition().getY() + 1);
                break;
            case EAST:
                nextPosition.setX(aventurier.getPosition().getX() + 1);
                nextPosition.setY(aventurier.getPosition().getY());
                break;
            case WEST:
                nextPosition.setX(aventurier.getPosition().getX() - 1);
                nextPosition.setY(aventurier.getPosition().getY());
                break;
            default:
                break;
        }

        return nextPosition;
    }


    /**
     * Définit l'orientation de l'aventurier suite le movement à faire : Tourner à gauche
     *
     * @param aventurier l'aventurier
     */
    public void defineOrientationWhenMovingLeft(Aventurier aventurier) {

        switch (aventurier.getOrientation()) {
            case NORTH:
                //L'orientation de l'aventurier change - OUEST
                aventurier.setOrientation(Orientation.WEST);
                break;
            case SOUTH:
                //L'orientation de l'aventurier change - EST
                aventurier.setOrientation(Orientation.EAST);
                break;
            case EAST:
                //L'orientation de l'aventurier change - NORD
                aventurier.setOrientation(Orientation.NORTH);
                break;
            case WEST:
                //L'orientation de l'aventurier change - SUD
                aventurier.setOrientation(Orientation.SOUTH);
                break;
        }

    }


    /**
     * Définit l'orientation de l'aventurier suite le movement à faire : Tourner à droite
     *
     * @param aventurier aventurier
     */
    public void defineOrientationWhenMovingRight(Aventurier aventurier) {

        switch (aventurier.getOrientation()) {
            case NORTH:
                //L'orientation de l'aventurier change - EST
                aventurier.setOrientation(Orientation.EAST);
                break;
            case SOUTH:
                //L'orientation de l'aventurier change - OUEST
                aventurier.setOrientation(Orientation.WEST);
                break;
            case EAST:
                //L'orientation de l'aventurier change - SUD
                aventurier.setOrientation(Orientation.SOUTH);
                break;
            case WEST:
                //L'orientation de l'aventurier change - NORD
                aventurier.setOrientation(Orientation.NORTH);
                break;
        }
    }

}
