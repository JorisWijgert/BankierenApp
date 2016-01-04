/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jvdwi
 */
public class BalieTest {
    
    Bank bank;
    Balie balie;
    
    public BalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bank = new Bank("rabo");
        try {
            balie = new Balie(bank);
        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void openrekening(){
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "", ""));
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "Castenray", ""));
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "", "Cavia"));
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "Castenray", ""));
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "Castenray", "Cavia"));
    assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "", "Cavia"));
    assertNotNull("geen random getal teruggekregen", balie.openRekening("Tom", "Castenray", "Cavia"));
    assertNotNull("geen random getal teruggekregen", balie.openRekening("Henk", "Oirlo", "Hamster"));
    assertNotNull("geen random getal teruggekregen", balie.openRekening("Kees", "Valkenswaard", "Konijn"));
    assertNotNull("geen random getal teruggekregen", balie.openRekening("fred", "Eindhoven", "fredje"));
    }
    
    @Test
    public void login(){
        balie.openRekening("Tom", "Castenray", "Cavia");
        balie.openRekening("Henk", "Oirlo", "Hamster");
        balie.openRekening("Kees", "Valkenswaard", "Konijn");
        balie.openRekening("fred", "Eindhoven", "fredje");
        try {
            assertNull("account niet gevonden", balie.logIn("", ""));
            assertNull("account niet gevonden", balie.logIn("Mark", ""));
            assertNull("password incorrect", balie.logIn("Tom", ""));
            assertNull("password incorrect", balie.logIn("Tom", "Hamster"));
            assertNull("password incorrect", balie.logIn("Kees", "fredje"));
            assertNotNull("account niet ingelogd", balie.logIn("Tom", "Cavia"));
            assertNotNull("account niet ingelogd", balie.logIn("Henk", "Hamster"));
            assertNotNull("account niet ingelogd", balie.logIn("Kees", "Konijn"));
            assertNotNull("account niet ingelogd", balie.logIn("fred", "fredje"));
        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
