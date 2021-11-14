/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurpoolthreads;

import java.io.*;
import java.net.*;
import ProtocolLUGAP.*;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author murad
 */

public class ThreadClient extends Thread
{
    private SourceTaches clientsAGerer;
    private String nom;
    private Login login; //objet Login que le serv va recevoir
    private LUGAP lugap;
    
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    
    public static Hashtable tableLoginPwd = new Hashtable();
    static
    {
        tableLoginPwd.put("hovsep", "murad");
        tableLoginPwd.put("didier", "roth");
    }
    
    //private Runnable tacheEnCours;
    private Socket socketClient; //socket associé au client, servira pour la communication
    
    public ThreadClient(SourceTaches st, String n )
    {
        clientsAGerer = st;
        nom = n;
    }
    public void run()
    {
        while (!isInterrupted())
        {
            try
            {
                System.out.println("Tread client avant getClient");
                socketClient = clientsAGerer.getClient();
            }
            catch (InterruptedException e)
            {
                System.out.println("Interruption : " + e.getMessage());
            }
            
            //System.out.println("run de tachesencours");
            //tacheEnCours.run();
            
            System.out.println("Le thread : " + nom + " vient d'etre associé a un client sur la socket : " + socketClient);
            
            System.out.println("Attente d'une connexion..");
            
            try 
            {
                ois = new ObjectInputStream(socketClient.getInputStream());
                oos = new ObjectOutputStream(socketClient.getOutputStream());
                //lecture des informations recues
                lugap = (LUGAP) ois.readObject();
                
                System.out.println("Objet reçu : ");
                
                lugap.getLogin().Affiche();
                
            } 
            catch (ClassNotFoundException | IOException e) {}
            
            
            
            System.out.println("## Le serveur test si les identifiants reçus sont corrects ## ");
            
            if(tableLoginPwd.get(lugap.getLogin().getIdentifiant()) != null && tableLoginPwd.get(lugap.getLogin().getIdentifiant()).equals(lugap.getLogin().getPassword())) 
            {
                System.out.println("Login/Password OK ! Connexion acceptée");
                lugap.setLoginStatus(Boolean.TRUE);
            }
            else
            {
                System.out.println("Login/Password not OK ! Connexion refusée");
                lugap.setLoginStatus(Boolean.FALSE);
            }
            
            try {
                oos.writeObject(lugap);
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            
            
            
        }
    }
}