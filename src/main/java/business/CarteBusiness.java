package business;

import domain.*;
import exceptions.BusinessCarteException;
import exceptions.RepositoryCarteException;
import repository.CarteRepository;
import utils.CarteUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CarteBusiness {

    private static final String FILENAME = "carte_aux_tresors";

    private CarteRepository carteRepository;

    private static final Logger LOGGER = Logger.getLogger(CarteBusiness.class.getName());

    public CarteBusiness(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    public Carte createCarte(String filename) throws RepositoryCarteException, BusinessCarteException {

        LOGGER.log(Level.INFO, "Lecture du fichier : [{}] ", filename);
        List<String> carteStringList = carteRepository.readFile(filename);

        LOGGER.log(Level.INFO, "Création de la carte avec les informations présentes dans le fichier en entrée");
        Carte carte = makeCarte(carteStringList);

        if (carte == null) {
            throw new BusinessCarteException("Il y a aucune carte à créer");
        }

        return carte;

    }

    /**
     * Creation de la carte avec la liste du contenu du fichier en entrée
     *
     * @param carteStringList liste string
     * @return carte
     * @throws BusinessCarteException
     */
    public Carte makeCarte(List<String> carteStringList) throws BusinessCarteException {

        if (carteStringList == null || carteStringList.isEmpty()) {
            throw new BusinessCarteException("Une erreur s'est produite - Pas de carte à créer");
        }

        //Vérifier si la liste de string contient une ligne commence par C - sinon fait du traitement
        if (!checkCarte(carteStringList)) {
            throw new BusinessCarteException("Une erreur s'est produite - Pas de carte détecté dans le fichier en entrée");
        }

        //Supprimer/ignorer toutes les lignes commencant par #
        filterIgnoredLines(carteStringList);

        //Sinon pas d'exception , continue

        String[] carteLine = carteStringList.get(0).replace(" ", "").split("-");
        Position dimensionCarte = new Position(Integer.parseInt(carteLine[1]), Integer.parseInt(carteLine[2]));

        //Map ayant comme clé une position et pour valeur un objet de type Case ( Montagne, Tresor, Aventurier)
        Map<Position, Case> caseHashMap = new HashMap<>();
        List<Aventurier> aventurierList = new ArrayList<>();


        for (int i = 1; i < carteStringList.size(); i++) {

            //Suppression des espace
            String line = carteStringList.get(i).replace(" ", "");
            String[] lineSplit = line.split("-");

            switch (lineSplit[0]) {
                case "M":
                    //DoSomething
                    Montagne montagne = makeMontagne(lineSplit);
                    caseHashMap.put(montagne.getPosition(), montagne);
                    break;
                case "T":
                    Tresor tresor = makeTresor(lineSplit);
                    caseHashMap.put(tresor.getPosition(), tresor);
                    break;
                case "A":
                    Aventurier aventurier = makeAventurier(lineSplit);
                    aventurierList.add(aventurier);
                    break;
                default:
                    throw new BusinessCarteException("Objet inconnu à la ligne: " + i);
            }
        }

        return new Carte(dimensionCarte, caseHashMap, aventurierList);


    }

    /**
     * Vérifier si une carte est bien présente dans le fichier en entrée
     *
     * @param carteStringList liste de string de du fichier
     */
    public boolean checkCarte(List<String> carteStringList) {
        return carteStringList.stream().anyMatch(c -> c.startsWith("C"));
    }


    /**
     * filtrer la liste de string en entrée en supprimant les lignes commencant par "#"
     *
     * @param carteStringList la liste de string
     */
    public void filterIgnoredLines(List<String> carteStringList) {
        List<String> linesToIgnore = carteStringList.stream().filter(s -> s.startsWith("#")).collect(Collectors.toList());
        carteStringList.removeAll(linesToIgnore);
    }

    /**
     * Créer la montagne
     *
     * @param ligneSplit la ligne contenant la description de la montagne
     * @return Montagne
     */
    public Montagne makeMontagne(String[] ligneSplit) {

        int x = Integer.parseInt(ligneSplit[1]);
        int y = Integer.parseInt(ligneSplit[2]);

        return new Montagne(new Position(x, y));
    }

    /**
     * Créer le trésor
     *
     * @param ligneSplit la ligne contenant la description du trésor
     * @return Tresor
     */
    public Tresor makeTresor(String[] ligneSplit) {

        int x = Integer.parseInt(ligneSplit[1]);
        int y = Integer.parseInt(ligneSplit[2]);
        int nbTresor = Integer.parseInt(ligneSplit[3]);

        Position position = new Position(x, y);
        return new Tresor(position, nbTresor);
    }

    /**
     * Créer l'aventurier
     *
     * @param ligneSplit la ligne contenant la description de l'aventurier
     * @return Aventurier
     * @throws BusinessCarteException
     */
    public Aventurier makeAventurier(String[] ligneSplit) throws BusinessCarteException {

        String nomAventurier = ligneSplit[1];
        int x = Integer.parseInt(ligneSplit[2]);
        int y = Integer.parseInt(ligneSplit[3]);

        Position position = new Position(x, y);

        //orientation de l'aventurier
        Orientation orientation = setOrientationForAventurier(ligneSplit[4]);

        //Liste d'action de l'aventurier
        List<String> actionList = makeActionList(ligneSplit[5]);

        return new Aventurier(position, nomAventurier, orientation, actionList);
    }

    /**
     * Créer l'orientation de l'aventurier
     *
     * @param orientationAventurier orientation de l'orientation en string
     * @return Orientation
     * @throws BusinessCarteException dans le cas aucune orinetation ne correspond
     */
    public Orientation setOrientationForAventurier(String orientationAventurier) throws BusinessCarteException {

        switch (orientationAventurier) {
            case "N":
                return Orientation.NORTH;
            case "S":
                return Orientation.SOUTH;
            case "E":
                return Orientation.EAST;
            case "O":
                return Orientation.WEST;
            default:
                throw new BusinessCarteException("Une exception s'est produite - l'orientation de l'aventurier inconnu");
        }

    }

    /**
     * Créer la liste d'action de l'aventurier
     *
     * @param actions action de l'aventurier à transormer en liste
     * @return liste de string contenant la liste d'action de l'aventurier
     * @throws BusinessCarteException dans le cas aucune action présente
     */
    public List<String> makeActionList(String actions) throws BusinessCarteException {
        if (actions.isEmpty()) {
            throw new BusinessCarteException("Une execption s'est produite - la séquence de mouvement est manquante");
        }
        return Arrays.asList(actions.split(""));
    }


    /**
     * Ecriture du contenu dans un fichier
     *
     * @param carteLines les lignes à insérer dans le fichier en sortie
     * @throws IOException
     */
    public void writeFileWithContents(List<String> carteLines) throws IOException {
        String timestamp = CarteUtils.convertLocalDateTimeToString(LocalDateTime.now(), "yyyy-MM-dd_HH-mm-ss");

        String filename = FILENAME + "_" + timestamp;
        String contents = carteLines.stream().map(Object::toString).collect(Collectors.joining("\n"));
        File file = carteRepository.createFileWithContent(filename, contents);

        String message = "Le fichier est crée dans : " + file.getAbsolutePath();
        LOGGER.log(Level.INFO, message);

    }

}
