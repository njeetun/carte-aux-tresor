package business;

import domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarteConverterTest {

    private CarteConverter carteConverter;

    private static final String ADVENTURER_COMMENTARY = "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe\n" +
            "vertical} - {Orientation} - {Nb. trésors ramassés}";
    private static final String TREASURE_COMMENTARY = "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}";

    @Before
    public void init() {
        carteConverter = new CarteConverter();
    }

    @Test
    public void generateFileDataTest() {
        Carte carte = new Carte();
        carte.setDimension(new Position(2, 4));

        //objets de la carte
        Montagne montagneone = new Montagne(new Position(1, 0));
        Montagne montagneTwo = new Montagne(new Position(2, 1));
        Tresor tresorOne = new Tresor(new Position(0, 3), 2);
        Tresor tresorTwo = new Tresor(new Position(1, 3), 3);

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 0), montagneone);
        caseMap.put(new Position(2, 1), montagneTwo);
        caseMap.put(new Position(0, 3), tresorOne);
        caseMap.put(new Position(1, 3), tresorTwo);
        carte.setCaseMap(caseMap);

        Aventurier aventurier = new Aventurier();
        aventurier.setNomAventurier("Lara");
        aventurier.setPosition(new Position(2, 4));
        aventurier.setOrientation(Orientation.WEST);
        aventurier.setNbTresorCollecte(2);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(aventurier);
        carte.setAventurierList(aventurierList);

        List<String> carteListString = carteConverter.generateFileData(carte);
        Assert.assertEquals(9, carteListString.size());
        Assert.assertEquals("C - 2 - 4", carteListString.get(0));

        Assert.assertEquals("M - 1 - 0", carteListString.get(1));
        Assert.assertEquals("M - 2 - 1", carteListString.get(2));
        Assert.assertEquals(TREASURE_COMMENTARY, carteListString.get(3));
        Assert.assertEquals("T - 1 - 3 - 3", carteListString.get(4));
        Assert.assertEquals(TREASURE_COMMENTARY, carteListString.get(5));
        Assert.assertEquals("T - 0 - 3 - 2", carteListString.get(6));

        Assert.assertEquals(ADVENTURER_COMMENTARY, carteListString.get(7));
        Assert.assertEquals("A - Lara - 2 - 4 - O - 2", carteListString.get(8));

    }

    @Test
    public void generateCarteInfo() {

        Carte carte = new Carte();
        carte.setDimension(new Position(2, 4));

        String carteInfo = carteConverter.generateCarteInfo(carte);
        Assert.assertEquals("C - 2 - 4", carteInfo);
    }

    @Test
    public void generateCarteObjectsTest() {

        //objets de la carte
        Montagne montagneone = new Montagne(new Position(1, 0));
        Montagne montagneTwo = new Montagne(new Position(2, 1));
        Tresor tresorOne = new Tresor(new Position(0, 3), 2);
        Tresor tresorTwo = new Tresor(new Position(1, 3), 3);

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 0), montagneone);
        caseMap.put(new Position(2, 1), montagneTwo);
        caseMap.put(new Position(0, 3), tresorOne);
        caseMap.put(new Position(1, 3), tresorTwo);

        List<String> objectsList = carteConverter.generateCarteObjects(caseMap);
        Assert.assertEquals(6, objectsList.size());
        Assert.assertEquals("M - 1 - 0", objectsList.get(0));
        Assert.assertEquals("M - 2 - 1", objectsList.get(1));
        Assert.assertEquals(TREASURE_COMMENTARY, objectsList.get(2));
        Assert.assertEquals("T - 1 - 3 - 3", objectsList.get(3));
        Assert.assertEquals(TREASURE_COMMENTARY, objectsList.get(4));
        Assert.assertEquals("T - 0 - 3 - 2", objectsList.get(5));

    }


    @Test
    public void generateMountainInfoTest() {
        Montagne montagne = new Montagne(new Position(2, 3));
        String montagneInfo = carteConverter.generateMountainInfo(montagne);
        Assert.assertEquals("M - 2 - 3", montagneInfo);
    }

    @Test
    public void generateTreasureInfoTest() {
        Tresor tresor = new Tresor(new Position(2, 3), 2);
        String tresorInfo = carteConverter.generateTreasureInfo(tresor);
        Assert.assertEquals("T - 2 - 3 - 2", tresorInfo);
    }

    @Test
    public void generateAventrurierInfoListTest() {
        Aventurier adventurerOne = new Aventurier();
        adventurerOne.setNomAventurier("Lara");
        adventurerOne.setPosition(new Position(2, 4));
        adventurerOne.setOrientation(Orientation.WEST);
        adventurerOne.setNbTresorCollecte(2);

        Aventurier adventurerTwo = new Aventurier();
        adventurerTwo.setNomAventurier("Jennie");
        adventurerTwo.setPosition(new Position(3, 2));
        adventurerTwo.setOrientation(Orientation.NORTH);
        adventurerTwo.setNbTresorCollecte(0);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(adventurerOne);
        aventurierList.add(adventurerTwo);

        List<String> listAventurier = carteConverter.generateAventrurierInfoList(aventurierList);
        Assert.assertEquals(4, listAventurier.size());
        Assert.assertEquals(ADVENTURER_COMMENTARY, listAventurier.get(0));
        Assert.assertEquals("A - Lara - 2 - 4 - O - 2", listAventurier.get(1));
        Assert.assertEquals(ADVENTURER_COMMENTARY, listAventurier.get(2));
        Assert.assertEquals("A - Jennie - 3 - 2 - N - 0", listAventurier.get(3));
    }

    @Test
    public void generateAventrurierInfoTest() {
        Aventurier aventurier = new Aventurier();
        aventurier.setNomAventurier("Lara");
        aventurier.setPosition(new Position(2, 4));
        aventurier.setOrientation(Orientation.WEST);
        aventurier.setNbTresorCollecte(2);

        String aventurierInfo = carteConverter.generateAventrurierInfo(aventurier);
        Assert.assertEquals("A - Lara - 2 - 4 - O - 2", aventurierInfo);
    }
}