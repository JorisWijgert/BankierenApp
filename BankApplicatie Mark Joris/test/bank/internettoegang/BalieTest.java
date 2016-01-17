/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.server.BalieServer;
import bankapplicatie.mark.joris.Iovermaak;
import bankapplicatie.mark.joris.overmaak;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;
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
    
    IBank bank;
    IBalie balie;
    Iovermaak OV;
    BalieServer bs;
            
    public BalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {        
        try {
            OV = new overmaak();
            bank = new Bank("Rabobank", OV);
            BalieServer bs = new BalieServer();
            bs.startBalie(bank.getName());
            OV.addbank(bank.getName());
            balie = bs.getbalie();
        } catch (NotBoundException ex) {
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
        try {
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "", ""));
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "Castenray", ""));
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "", "Cavia"));
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "Castenray", ""));
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("", "Castenray", "Cavia"));
            assertNull("leeg invoerveld niet afgevangen", balie.openRekening("Tom", "", "Cavia"));
            assertNull("te lang wachtwoord niet afgevangen", balie.openRekening("Tom", "Castenray", "Cavia1234939"));
            assertNotNull("geen random getal teruggekregen", balie.openRekening("Tom", "Castenray", "Cavia"));
            assertNotNull("geen random getal teruggekregen", balie.openRekening("Henk", "Oirlo", "Hamster"));
            assertNotNull("geen random getal teruggekregen", balie.openRekening("Kees", "Valkenswaard", "Konijn"));
            assertNotNull("geen random getal teruggekregen", balie.openRekening("fred", "Eindhoven", "fredje"));
        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void login(){
        try {
            String inlogAccountcode1 = balie.openRekening("Tom", "Castenray", "Cavia");
            String inlogAccountcode2 = balie.openRekening("Henk", "Oirlo", "Hamster");
            String inlogAccountcode3 = balie.openRekening("Kees", "Valkenswaard", "Konijn");
            String inlogAccountcode4 = balie.openRekening("fred", "Eindhoven", "fredje");
            try {
                assertNull("account niet gevonden", balie.logIn("", ""));
                assertNull("account niet gevonden", balie.logIn("Mark", ""));
                assertNull("password incorrect", balie.logIn("Tom", ""));
                assertNull("password incorrect", balie.logIn("Tom", "Hamster"));
                assertNull("password incorrect", balie.logIn("Kees", "fredje"));
                assertNotNull("account niet ingelogd", balie.logIn(inlogAccountcode1, "Cavia"));
                assertNotNull("account niet ingelogd", balie.logIn(inlogAccountcode2, "Hamster"));
                assertNotNull("account niet ingelogd", balie.logIn(inlogAccountcode3, "Konijn"));
                assertNotNull("account niet ingelogd", balie.logIn(inlogAccountcode4, "fredje"));
                // assertNull("account is ingelogd", balie.logIn(inlogAccountcode4, "Hamster"));
            } catch (RemoteException ex) {
                Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(BalieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
