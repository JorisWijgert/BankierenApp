package bank.bankieren;

import bankapplicatie.mark.joris.Iovermaak;
import fontys.util.*;
import java.io.Serializable;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank implements IBank, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728841131739353765L;
	private Map<Integer,IRekeningTbvBank> accounts;
	private Collection<IKlant> clients;
	private int nieuwReknr;
	private String name;
        private Iovermaak OV;

	public Bank(String name, Iovermaak OV) {
		accounts = new HashMap<Integer,IRekeningTbvBank>();
		clients = new ArrayList<IKlant>();
		nieuwReknr = 0;	
		this.name = name;
                this.OV = OV;
	}

	public synchronized int openRekening(String name, String city) {
            try {
                if (name.equals("") || city.equals(""))
                    return -1;
                
                IKlant klant = getKlant(name, city);
                nieuwReknr = OV.getNieuwRekNR();
                IRekeningTbvBank account = new Rekening(nieuwReknr, klant, Money.EURO);
                accounts.put(nieuwReknr,account);
                OV.updatebank(this);
                return nieuwReknr;
            } catch (RemoteException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
	}

	private IKlant getKlant(String name, String city) {
		for (IKlant k : clients) {
			if (k.getNaam().equals(name) && k.getPlaats().equals(city))
				return k;
		}
		IKlant klant = new Klant(name, city);
		clients.add(klant);
		return klant;
	}

	public IRekening getRekening(int nr) {
		return accounts.get(nr);
	}

	public boolean maakOver(int source, int destination, Money money)
			throws NumberDoesntExistException {
            try {
                if (source == destination)
                    throw new RuntimeException(
                            "cannot transfer money to your own account");
                if (!money.isPositive())
                    throw new RuntimeException("money must be positive");
                
                IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
                if (source_account == null)
                    throw new NumberDoesntExistException("account " + source
                            + " unknown at " + name);
                
                Money negative = Money.difference(new Money(0, money.getCurrency()),
                        money);
                boolean success = source_account.muteer(negative);
                if (!success)
                {
                   return false; 
                }
                    
                
                
                System.out.println("kaas");
                
                IRekeningTbvBank dest_account = (IRekeningTbvBank) OV.zoeken(destination);
                if (dest_account == null)
                    throw new NumberDoesntExistException("account " + destination
                            + " unknown at " + name);
                success = dest_account.muteer(money);
                
                if (!success) // rollback
                    source_account.muteer(money);
                OV.updatebank(this);
                return success;
            } catch (RemoteException ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
	}

	@Override
	public String getName() {
		return name;
	}

}
