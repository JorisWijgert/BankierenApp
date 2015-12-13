/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

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

    public BankTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        bank = new Bank("Rabobank");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
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
        IBank bank2 = new Bank("ABN AMRO");

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
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100000000, bank.openRekening("Joris", "Oploo"));
        assertEquals("Rekeningnummer is onbedoeld goed aangemaakt", -1, bank.openRekening("", "Oploo"));
        assertEquals("Rekeningnummer is onbedoeld goed aangemaakt", -1, bank.openRekening("Joris", ""));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100000001, bank.openRekening("Joris", "Oploo"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100000002, bank.openRekening("Mark", "Oploo"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100000003, bank.openRekening("Joris", "Castenray"));
        assertEquals("Rekeningnummer is niet goed aangemaakt", 100000004, bank.openRekening("Mark", "Castenray"));
    }

    
    @Test
    public void testGetRekening(){
        IRekening testRekening = new Rekening(100000000, new Klant("Joris", "Oploo"), "Euro");
        bank.openRekening("Joris", "Oploo");
        assertTrue("Rekeningen wordt niet goed opgehaald", testRekening.equals(bank.getRekening(100000000)));
    }
}
