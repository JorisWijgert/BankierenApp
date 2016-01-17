/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import bank.server.BalieServer;
import bankapplicatie.mark.joris.Iovermaak;
import bankapplicatie.mark.joris.overmaak;
import fontys.util.NumberDoesntExistException;
import java.rmi.NotBoundException;
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
public class BankTest {

    private static IBank bank;
    private static Iovermaak OV;

    public BankTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            OV = new overmaak();
            bank = new Bank("Rabobank", OV);
            BalieServer bs = new BalieServer();
            bs.startBalie(bank.getName());
            OV.addbank(bank.getName());
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /**
     * Test of de bankconstructor werkt
     */
    @Test
    public void testBankConst() {
        IBank bank2 = new Bank("ABN AMRO", OV);

    }

    /**
     * Test het verkrijgen van de banknaam
     */
    @Test
    public void testGetBankNaam() {
        assertEquals("Banknamen zijn niet gelijk...", "Rabobank", bank.getName());
    }

    /**
     * Test het openen van een rekening op alle relevante manieren
     */
    @Test
    public void testOpenRekening() {
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100100001, bank.openRekening("Joris", "Oploo"));
        assertEquals("Rekeningnummer is onbedoeld goed aangemaakt", -1, bank.openRekening("", "Oploo"));
        assertEquals("Rekeningnummer is onbedoeld goed aangemaakt", -1, bank.openRekening("Joris", ""));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100100002, bank.openRekening("Joris", "Oploo"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100100003, bank.openRekening("Mark", "Oploo"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100100004, bank.openRekening("Joris", "Castenray"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100100005, bank.openRekening("Mark", "Castenray"));
    }

    @Test
    public void testGetRekening() {
        IRekening testRekening = new Rekening(100100001, new Klant("Joris", "Oploo"), "Euro");
        bank.openRekening("Joris", "Oploo");
        assertTrue("Rekeningen wordt niet goed opgehaald", testRekening.equals(bank.getRekening(100100001)));
    }

    @Test(expected = RuntimeException.class)
    public void maakoverzelfnummer() throws RuntimeException, NumberDoesntExistException {
        Money geld = new Money(20, Money.EURO);
        bank.maakOver(100000001, 100000001, geld);

    }

    @Test(expected = RuntimeException.class)
    public void maakovernietpositief() throws RuntimeException, NumberDoesntExistException {
        Money geld = new Money(-20, Money.EURO);
        bank.maakOver(100000001, 100000002, geld);

    }

    @Test(expected = NumberDoesntExistException.class)
    public void maakovernullrekening() throws NumberDoesntExistException {
        Money geld = new Money(20, Money.EURO);

        bank.maakOver(12, 100000002, geld);

    }

    @Test(expected = NumberDoesntExistException.class)
    public void maakovernietbestaandown() throws NumberDoesntExistException {
        Money geld = new Money(20, Money.EURO);

        bank.maakOver(12, 100000002, geld);

    }

    @Test(expected = NumberDoesntExistException.class)
    public void maakovernietbestaanddest() throws NumberDoesntExistException {
        bank.openRekening("Joris", "Oploo");
        Money geld = new Money(20, Money.EURO);

        bank.maakOver(100000001, 12, geld);

    }

    @Test
    public void maakover() throws NumberDoesntExistException {
        bank.openRekening("Joris", "Oploo");
        bank.openRekening("Mark", "Castenray");
        bank.openRekening("Tom", "Jones");
        Money geld1 = new Money(20, Money.EURO);
        Money geld2 = new Money(100, Money.EURO);
        Money geld3 = new Money(40, Money.EURO);
        Money geld4 = new Money(11000, Money.EURO);

        assertTrue("niet overgemaakt", bank.maakOver(100100001, 100100002, geld1));
        assertTrue("niet overgemaakt", bank.maakOver(100100001, 100100003, geld2));
        assertTrue("niet overgemaakt", bank.maakOver(100100002, 100100003, geld3));
        assertTrue("niet overgemaakt", bank.maakOver(100100003, 100100002, geld1));
        assertTrue("niet overgemaakt", bank.maakOver(100100003, 100100001, geld2));
        assertTrue("niet overgemaakt", bank.maakOver(100100002, 100100001, geld3));
        assertFalse("toch overgemaakt", bank.maakOver(100100002, 100100001, geld4));

    }
    
}
