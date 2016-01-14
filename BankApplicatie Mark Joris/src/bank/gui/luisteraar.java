/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.gui;

import bank.bankieren.IRekening;
import fontys.util.InvalidSessionException;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Slashy
 */
public class luisteraar extends UnicastRemoteObject implements ILuisteraar {
    
    private BankierSessieController BSC;
    public luisteraar(BankierSessieController BSC) throws RemoteException 
    {
        this.BSC = BSC;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        try {
            IRekening rekening = BSC.getsessie().getRekening();
            if(rekening.getNr() == (int) evt.getNewValue())
            {
              BSC.saldoupdate();
            }            
        } catch (InvalidSessionException ex) {
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
