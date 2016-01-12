/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapplicatie.mark.joris;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Slashy
 */
public interface Iovermaak extends Remote {
    
    public void addbank(String nameBank) throws RemoteException;
    
    public boolean zoeken(int destination, Money money) throws RemoteException;
    
    public int getNieuwRekNR() throws RemoteException;    
    
}
