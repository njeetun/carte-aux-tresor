package service;

import business.CarteBusiness;
import business.CarteConverter;
import business.ExecuteMovesBusiness;
import domain.Carte;
import exceptions.BusinessCarteException;
import exceptions.RepositoryCarteException;

import java.io.IOException;
import java.util.List;

public class CarteService {


    private CarteBusiness carteBusiness;
    private ExecuteMovesBusiness executeMovesBusiness;
    private CarteConverter carteConverter;

    public CarteService(CarteBusiness carteBusiness, ExecuteMovesBusiness executeMovesBusiness, CarteConverter carteConverter) {
        this.carteBusiness = carteBusiness;
        this.executeMovesBusiness = executeMovesBusiness;
        this.carteConverter = carteConverter;
    }

    public Carte createCarte(String filename) throws RepositoryCarteException, BusinessCarteException {
        return carteBusiness.createCarte(filename);
    }

    public void executeMovements(Carte carte) {
        //executions des movements ici
        executeMovesBusiness.executeMovementsInCarte(carte);
    }

    public List<String> createLinesForFile(Carte carte) {
        return carteConverter.generateFileData(carte);
    }

    public void writeFile(List<String> carteLines) throws IOException {
        carteBusiness.writeFileWithContents(carteLines);
    }


}
