import business.CarteBusiness;
import business.CarteConverter;
import business.ExecuteMovesBusiness;
import domain.Carte;
import exceptions.BusinessCarteException;
import exceptions.RepositoryCarteException;
import repository.CarteRepository;
import service.CarteService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) throws RepositoryCarteException, BusinessCarteException, IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez entrer le fichier contenant les informations de la carte ");
        String carteFilename = sc.nextLine();
        sc.close();

        CarteRepository carteRepository = new CarteRepository();
        CarteBusiness carteBusiness = new CarteBusiness(carteRepository);
        ExecuteMovesBusiness executeMovesBusiness = new ExecuteMovesBusiness();
        CarteConverter carteConverter = new CarteConverter();
        CarteService carteService = new CarteService(carteBusiness, executeMovesBusiness, carteConverter);

        System.out.println("\nCréation de la carte...");
        Carte carte = carteService.createCarte(carteFilename);

        System.out.println("\nExécution des mouvements de la carte...");
        carteService.executeMovements(carte);

        System.out.println("\nGénération des différentes lignes de la carte à écrire dans le fichier en sortie...");
        List<String> carteLines = carteService.createLinesForFile(carte);

        System.out.println("\nEcriture dans un fichier text...");
        carteService.writeFile(carteLines);

    }
}
