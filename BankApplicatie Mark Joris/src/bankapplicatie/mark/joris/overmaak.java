/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapplicatie.mark.joris;

import bank.bankieren.IRekening;
import bank.bankieren.Money;
import bank.internettoegang.IBalie;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Slashy
 */
public class overmaak extends UnicastRemoteObject implements Iovermaak{
    
    List<IBalie> balies;
    private int nieuwReknr;
    
    
    public overmaak() throws RemoteException
    {
      balies = new ArrayList<>(); 
      nieuwReknr = 100000000;	
    }
    
    @Override
    public void addbank(String nameBank) throws RemoteException
    {
        try {
            FileInputStream in = new FileInputStream(nameBank+".props");
            Properties props = new Properties();
            props.load(in);
            String rmiBalie = props.getProperty("balie");
            in.close();
            IBalie balie = (IBalie) Naming.lookup("rmi://" + rmiBalie);
            balies.add(balie);

            } catch (Exception exc) {
                exc.printStackTrace();
               
            }
    }
    
    @Override
    public boolean zoeken(int destination, Money money) throws RemoteException
    {
        boolean succes = false;
        for (IBalie balie : balies)
        {
           IRekening rekening = balie.getBank().getRekening(destination);
           if(rekening != null)
           {      
               succes = balie.ontvangen(rekening, money);
               return succes;
           }
        }
        return succes;
    }
    
    @Override
    public int getNieuwRekNR() throws RemoteException
    {
        nieuwReknr++;
        return nieuwReknr;
    }   
    
}
