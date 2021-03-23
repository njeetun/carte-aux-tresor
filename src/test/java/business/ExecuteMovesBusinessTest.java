package business;

import domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteMovesBusinessTest {

    private ExecuteMovesBusiness executeMovesBusiness;

    @Before
    public void init() {
        executeMovesBusiness = new ExecuteMovesBusiness();
    }


    @Test
    public void checkCaseLimitTest() {

        Assert.assertTrue(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(2, 2)));
        Assert.assertFalse(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(3, 2)));
        Assert.assertFalse(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(2, 4)));
        Assert.assertFalse(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(2, 5)));
        Assert.assertFalse(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(5, 3)));
        Assert.assertFalse(executeMovesBusiness.checkCaseLimit(new Position(3, 4), new Position(-1, 3)));

    }

    @Test
    public void checkAnotherAventurierInCaseFoundTest() {

        List<Aventurier> aventurierList = new ArrayList<>();

        Aventurier aventurierOne = new Aventurier();
        Position positionOne = new Position(2, 3);
        aventurierOne.setPosition(positionOne);

        Aventurier aventurierTwo = new Aventurier();
        Position positionTwo = new Position(2, 5);
        aventurierTwo.setPosition(positionTwo);

        aventurierList.add(aventurierOne);
        aventurierList.add(aventurierTwo);

        Assert.assertTrue(executeMovesBusiness.checkAnotherAventurierInCase(aventurierList, new Position(2, 3)));
    }

    @Test
    public void checkAnotherAventurierInCaseNotFoundTest() {

        List<Aventurier> aventurierList = new ArrayList<>();

        Aventurier aventurierOne = new Aventurier();
        Position positionOne = new Position(2, 3);
        aventurierOne.setPosition(positionOne);

        Aventurier aventurierTwo = new Aventurier();
        Position positionTwo = new Position(2, 5);
        aventurierTwo.setPosition(positionTwo);

        aventurierList.add(aventurierOne);
        aventurierList.add(aventurierTwo);

        Assert.assertFalse(executeMovesBusiness.checkAnotherAventurierInCase(aventurierList, new Position(2, 4)));
    }

    @Test
    public void checkMontainObstacleFoundTest() {

        Carte carte = new Carte();
        Montagne montagne = new Montagne(new Position(1, 2));

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 2), montagne);


        List<Aventurier> aventurierList = new ArrayList<>();
        Aventurier aventurier = new Aventurier();
        aventurier.setPosition(new Position(2, 3));
        aventurierList.add(aventurier);

        carte.setCaseMap(caseMap);
        carte.setAventurierList(aventurierList);

        Assert.assertTrue(executeMovesBusiness.checkObstacle(carte, new Position(1, 2)));
    }

    @Test
    public void checkAventurierObstacleFoundTest() {

        Carte carte = new Carte();
        Montagne montagne = new Montagne(new Position(1, 2));

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 2), montagne);


        List<Aventurier> aventurierList = new ArrayList<>();
        Aventurier aventurier = new Aventurier();
        aventurier.setPosition(new Position(2, 3));
        aventurierList.add(aventurier);

        carte.setCaseMap(caseMap);
        carte.setAventurierList(aventurierList);

        Assert.assertTrue(executeMovesBusiness.checkObstacle(carte, new Position(2, 3)));
    }

    @Test
    public void checkObstacleNotFoundTest() {

        Carte carte = new Carte();
        Montagne montagne = new Montagne(new Position(1, 2));

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 2), montagne);


        List<Aventurier> aventurierList = new ArrayList<>();
        Aventurier aventurier = new Aventurier();
        aventurier.setPosition(new Position(2, 3));
        aventurierList.add(aventurier);

        carte.setCaseMap(caseMap);
        carte.setAventurierList(aventurierList);

        Assert.assertFalse(executeMovesBusiness.checkObstacle(carte, new Position(4, 4)));
    }


    @Test
    public void checkMountainPresentFoundTest() {

        Carte carte = new Carte();
        Montagne montagneOne = new Montagne(new Position(1, 2));
        Montagne montagneTwo = new Montagne(new Position(2, 3));

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 2), montagneOne);
        caseMap.put(new Position(2, 3), montagneTwo);

        carte.setCaseMap(caseMap);
        Position nextPosition = new Position(2, 3);

        Assert.assertTrue(executeMovesBusiness.checkMountainPresent(carte, nextPosition));
    }

    @Test
    public void checkMountainPresentNotFoundTest() {

        Carte carte = new Carte();
        Montagne montagneOne = new Montagne(new Position(1, 2));
        Montagne montagneTwo = new Montagne(new Position(2, 3));

        Map<Position, Case> caseMap = new HashMap<>();
        caseMap.put(new Position(1, 2), montagneOne);
        caseMap.put(new Position(2, 3), montagneTwo);

        carte.setCaseMap(caseMap);
        Position nextPosition = new Position(4, 4);

        Assert.assertFalse(executeMovesBusiness.checkMountainPresent(carte, nextPosition));
    }


    @Test
    public void defineNextPositionWhenMovingForward() {

        Aventurier aventurierFacingNorth = new Aventurier();
        aventurierFacingNorth.setOrientation(Orientation.NORTH);
        aventurierFacingNorth.setPosition(new Position(1, 1));
        Position positionNorth = executeMovesBusiness.defineNextPositionWhenMoving(aventurierFacingNorth);
        Assert.assertEquals(1, positionNorth.getX());
        Assert.assertEquals(0, positionNorth.getY());

        Aventurier aventurierFacingSouth = new Aventurier();
        aventurierFacingSouth.setOrientation(Orientation.SOUTH);
        aventurierFacingSouth.setPosition(new Position(1, 1));
        Position positionSouth = executeMovesBusiness.defineNextPositionWhenMoving(aventurierFacingSouth);
        Assert.assertEquals(1, positionSouth.getX());
        Assert.assertEquals(2, positionSouth.getY());

        Aventurier aventurierFacingEast = new Aventurier();
        aventurierFacingEast.setOrientation(Orientation.EAST);
        aventurierFacingEast.setPosition(new Position(1, 1));
        Position positionEast = executeMovesBusiness.defineNextPositionWhenMoving(aventurierFacingEast);
        Assert.assertEquals(2, positionEast.getX());
        Assert.assertEquals(1, positionEast.getY());

        Aventurier aventurierFacingWest = new Aventurier();
        aventurierFacingWest.setOrientation(Orientation.WEST);
        aventurierFacingWest.setPosition(new Position(1, 1));
        Position positionWest = executeMovesBusiness.defineNextPositionWhenMoving(aventurierFacingWest);
        Assert.assertEquals(0, positionWest.getX());
        Assert.assertEquals(1, positionWest.getY());
    }

    @Test
    public void defineNextPositionWhenMovingLeft() {

        Aventurier aventurierFacingNorth = new Aventurier();
        aventurierFacingNorth.setOrientation(Orientation.NORTH);
        executeMovesBusiness.defineOrientationWhenMovingLeft(aventurierFacingNorth);
        Assert.assertEquals(Orientation.WEST, aventurierFacingNorth.getOrientation());

        Aventurier aventurierFacingSouth = new Aventurier();
        aventurierFacingSouth.setOrientation(Orientation.SOUTH);
        executeMovesBusiness.defineOrientationWhenMovingLeft(aventurierFacingSouth);
        Assert.assertEquals(Orientation.EAST, aventurierFacingSouth.getOrientation());

        Aventurier aventurierFacingEast = new Aventurier();
        aventurierFacingEast.setOrientation(Orientation.EAST);
        executeMovesBusiness.defineOrientationWhenMovingLeft(aventurierFacingEast);
        Assert.assertEquals(Orientation.NORTH, aventurierFacingEast.getOrientation());

        Aventurier aventurierFacingWest = new Aventurier();
        aventurierFacingWest.setOrientation(Orientation.WEST);
        executeMovesBusiness.defineOrientationWhenMovingLeft(aventurierFacingWest);
        Assert.assertEquals(Orientation.SOUTH, aventurierFacingWest.getOrientation());
    }

    @Test
    public void defineOrientationWhenMovingRight() {

        Aventurier aventurierFacingNorth = new Aventurier();
        aventurierFacingNorth.setOrientation(Orientation.NORTH);
        executeMovesBusiness.defineOrientationWhenMovingRight(aventurierFacingNorth);
        Assert.assertEquals(Orientation.EAST, aventurierFacingNorth.getOrientation());

        Aventurier aventurierFacingSouth = new Aventurier();
        aventurierFacingSouth.setOrientation(Orientation.SOUTH);
        executeMovesBusiness.defineOrientationWhenMovingRight(aventurierFacingSouth);
        Assert.assertEquals(Orientation.WEST, aventurierFacingSouth.getOrientation());

        Aventurier aventurierFacingEast = new Aventurier();
        aventurierFacingEast.setOrientation(Orientation.EAST);
        executeMovesBusiness.defineOrientationWhenMovingRight(aventurierFacingEast);
        Assert.assertEquals(Orientation.SOUTH, aventurierFacingEast.getOrientation());

        Aventurier aventurierFacingWest = new Aventurier();
        aventurierFacingWest.setOrientation(Orientation.WEST);
        executeMovesBusiness.defineOrientationWhenMovingRight(aventurierFacingWest);
        Assert.assertEquals(Orientation.NORTH, aventurierFacingWest.getOrientation());
    }

    @Test
    public void executeMovementsInCarteTest() {

        //Aventurier
        Aventurier aventurier = new Aventurier();
        aventurier.setNomAventurier("Lara");
        aventurier.setPosition(new Position(1, 1));
        aventurier.setOrientation(Orientation.SOUTH);

        List<String> actionsOfAventurier = new ArrayList<>();
        actionsOfAventurier.add("A");
        actionsOfAventurier.add("A");
        actionsOfAventurier.add("D");
        actionsOfAventurier.add("A");
        actionsOfAventurier.add("D");
        actionsOfAventurier.add("A");
        actionsOfAventurier.add("G");
        actionsOfAventurier.add("G");
        actionsOfAventurier.add("A");
        aventurier.setActionList(actionsOfAventurier);

        Aventurier secondAventurier = new Aventurier();
        secondAventurier.setNomAventurier("Jennie");
        secondAventurier.setPosition(new Position(0, 1));
        secondAventurier.setOrientation(Orientation.NORTH);
        secondAventurier.setActionList(actionsOfAventurier);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(aventurier);
        //   aventurierList.add(secondAventurier);


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


        Carte carte = new Carte();
        carte.setDimension(new Position(3, 4));
        carte.setAventurierList(aventurierList);
        carte.setCaseMap(caseMap);


        executeMovesBusiness.executeMovementsInCarte(carte);
        Assert.assertEquals(3, carte.getAventurierList().get(0).getNbTresorCollecte());
        Assert.assertEquals(Orientation.SOUTH, carte.getAventurierList().get(0).getOrientation());

        Assert.assertEquals(0, ((Tresor) carte.getCaseMap().get(new Position(0, 3))).getNbTresor());
    }

}