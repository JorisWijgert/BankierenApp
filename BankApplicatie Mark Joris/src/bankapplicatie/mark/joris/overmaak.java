/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapplicatie.mark.joris;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Slashy
 */
public class overmaak extends UnicastRemoteObject implements Iovermaak{
    
    List<Bank> banken;
    private int nieuwReknr;
    
    
    public overmaak() throws RemoteException
    {
      banken = new ArrayList<>(); 
      nieuwReknr = 100000000;	
    }
    
    @Override
    public void addbank(Bank bank) throws RemoteException
    {
        banken.add(bank);
    }
    
    @Override
    public IRekening zoeken(int destination) throws RemoteException
    {
        for (Bank bank : banken)
        {
           IRekening rekening = bank.getRekening(destination);
           if(rekening != null)
           {
               return rekening;
           }
        }
        return null;
    }
    
    @Override
    public int getNieuwRekNR() throws RemoteException
    {
        nieuwReknr++;
        return nieuwReknr;
    }
    
    public void updatebank(Bank bank) throws RemoteException
    {
        Bank wegbank = null;
        for (Bank oudbank : banken)
        {
            if(oudbank.getName().equals(bank.getName()))
            {
                wegbank = oudbank;
            }
        }        
        banken.remove(wegbank);
        banken.add(bank);
        
    }
    
    public IBank getbank(Bank bank) throws RemoteException
    {        
        for (Bank nieuwbank : banken)
        {
            if(nieuwbank.getName().equals(bank.getName()))
            {
                return nieuwbank;
            }
        } 
        return null;
    }
}
