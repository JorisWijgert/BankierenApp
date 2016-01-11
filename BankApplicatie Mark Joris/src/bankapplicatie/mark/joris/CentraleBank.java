/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapplicatie.mark.joris;


import bank.server.BalieServer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author jvdwi
 */
public class CentraleBank extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FileOutputStream out = null;
            try {                
                String address = java.net.InetAddress.getLocalHost().getHostAddress();
                int port = 1099;
                Properties props = new Properties();
                String rmiOvermaak = address + ":" + port + "/" + "overmaak";
                props.setProperty("overmaak", rmiOvermaak);
                out = new FileOutputStream("overmaak" + ".props");
                props.store(out, null);
                out.close();
                java.rmi.registry.LocateRegistry.createRegistry(port);
                Iovermaak OM = new overmaak();
                Naming.rebind("overmaak", OM);               
                

            } catch (IOException ex) {
                Logger.getLogger(BalieServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(BalieServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
