/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapplicatie.mark.joris;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Slashy
 */
public interface Iovermaak extends Remote {
    
    public void addbank(Bank bank) throws RemoteException;
    
    public IRekening zoeken(int destination) throws RemoteException;
    
    public int getNieuwRekNR() throws RemoteException;
    
    public void updatebank(Bank bank) throws RemoteException;
    
    public IBank getbank(Bank bank) throws RemoteException;
}
