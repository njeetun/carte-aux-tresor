package business;

import domain.*;
import exceptions.BusinessCarteException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.CarteRepository;

import java.util.ArrayList;
import java.util.List;

public class CarteBusinessTest {

    private List<String> carteList = new ArrayList<>();

    private CarteBusiness carteBusiness;

    @Before
    public void init() {

        CarteRepository carteRepository = new CarteRepository();
        carteBusiness = new CarteBusiness(carteRepository);

        String ligneUne = "C - 3 - 4";
        String montagneCommentaire = "# {M comme Montagne} - {Axe horizontal} - {Axe vertical}";
        String ligneDeux = "M - 1 - 0";
        String ligneTrois = "M - 2 - 1";
        String ligneQuatre = "T - 0 - 3 - 2";
        String ligneCinq = "T - 1 - 3 - 3";
        String aventurierCommentaire = "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axevertical} - {Orientation} - {Séquence de mouvement}";
        String ligneSix = "A - Lara - 1 - 1 - S - AADADAGGA";

        carteList.add(ligneUne);
        carteList.add(montagneCommentaire);
        carteList.add(ligneDeux);
        carteList.add(ligneTrois);
        carteList.add(ligneQuatre);
        carteList.add(ligneCinq);
        carteList.add(aventurierCommentaire);
        carteList.add(ligneSix);

    }

    @Test
    public void makeCarteTest() throws BusinessCarteException {

        Carte carte = carteBusiness.makeCarte(carteList);

        Assert.assertNotNull(carte);
        Assert.assertEquals(3, carte.getDimension().getX());
        Assert.assertEquals(4, carte.getDimension().getY());
        Assert.assertEquals(1, carte.getAventurierList().size());
    }

    @Test
    public void makeCarteTwoAventurierTest() throws BusinessCarteException {

        carteList.add("A - Croft - 1 - 1 - N - AADADAGGA");
        Carte carte = carteBusiness.makeCarte(carteList);

        Assert.assertNotNull(carte);
        Assert.assertEquals(3, carte.getDimension().getX());
        Assert.assertEquals(4, carte.getDimension().getY());
        Assert.assertEquals(2, carte.getAventurierList().size());
    }


    @Test
    public void checkCarteTest() {

        Assert.assertTrue(carteBusiness.checkCarte(carteList));

        Assert.assertFalse(carteBusiness.checkCarte(new ArrayList<>()));
    }


    @Test
    public void makeMontagneTest() {

        String[] montagneLine = {"M", "1", "1"};

        Montagne montagne = carteBusiness.makeMontagne(montagneLine);
        Assert.assertNotNull(montagne);
        Assert.assertEquals(1, montagne.getPosition().getX());
        Assert.assertEquals(1, montagne.getPosition().getY());

    }

    @Test
    public void makeTresorTest() {

        String[] tresorLine = {"T", "0", "3", "3"};

        Tresor tresor = carteBusiness.makeTresor(tresorLine);
        Assert.assertNotNull(tresor);
        Assert.assertEquals(0, tresor.getPosition().getX());
        Assert.assertEquals(3, tresor.getPosition().getY());
        Assert.assertEquals(3, tresor.getNbTresor());
    }

    @Test
    public void makeAventurierTest() throws BusinessCarteException {

        String[] aventurierLine = {"A", "Indiana", "1", "1", "S", "AADADA"};

        Aventurier aventurier = carteBusiness.makeAventurier(aventurierLine);
        Assert.assertNotNull(aventurier);
        Assert.assertEquals("Indiana", aventurier.getNomAventurier());
        Assert.assertEquals(1, aventurier.getPosition().getX());
        Assert.assertEquals(1, aventurier.getPosition().getY());
        Assert.assertEquals(Orientation.SOUTH, aventurier.getOrientation());
        Assert.assertEquals(6, aventurier.getActionList().size());

    }

    @Test
    public void setOrientationForAventurierTest() throws BusinessCarteException {


        Assert.assertEquals(Orientation.NORTH, carteBusiness.setOrientationForAventurier("N"));
        Assert.assertEquals(Orientation.SOUTH, carteBusiness.setOrientationForAventurier("S"));
        Assert.assertEquals(Orientation.EAST, carteBusiness.setOrientationForAventurier("E"));
        Assert.assertEquals(Orientation.WEST, carteBusiness.setOrientationForAventurier("O"));
    }

    @Test(expected = BusinessCarteException.class)
    public void setOrientationForAventurierFailTest() throws BusinessCarteException {
        Orientation orientation = carteBusiness.setOrientationForAventurier("NE");
    }

    @Test
    public void makeActionsListTest() throws BusinessCarteException {

        String actions = "AADADAGGA";
        List<String> stringAction = carteBusiness.makeActionList(actions);
        Assert.assertNotNull(stringAction);
        Assert.assertEquals(9, stringAction.size());
        Assert.assertEquals("A", stringAction.get(0));
        Assert.assertEquals("D", stringAction.get(2));
    }

    @Test(expected = BusinessCarteException.class)
    public void makeActionsListFailTest() throws BusinessCarteException {

        String actions = "";
        List<String> stringAction = carteBusiness.makeActionList(actions);

    }

}