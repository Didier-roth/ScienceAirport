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
import database.utilities.*;
import java.beans.Beans;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

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
    private int statemachine;
    
    private static int NOTLOGED = 0;
    private static int LOGINOK = 1;
    private static int VOLOK = 2;
    
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
        try
        {
            System.out.println("Tread client avant getClient");
            socketClient = clientsAGerer.getClient();
            System.out.println("test");
        }
        catch (InterruptedException e)
        {
            System.out.println("Interruption : " + e.getMessage());
        }
        
        System.out.println("Le thread : " + nom + " vient d'etre associé a un client sur la socket : " + socketClient);
            
        System.out.println("Attente d'une connexion..");
        
        try {
            ois = new ObjectInputStream(socketClient.getInputStream());
            oos = new ObjectOutputStream(socketClient.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.statemachine = ThreadClient.NOTLOGED;
        
        while (!isInterrupted())
        {

            
            //System.out.println("run de tachesencours");
            //tacheEnCours.run();
            try 
            {
                //lecture des informations recues
                lugap.setNumRequete(10);
                lugap = (LUGAP) ois.readObject();
                System.out.println("\t tc - Objet reçu : " + lugap);
            } 
            catch (ClassNotFoundException | IOException e) 
            {
                this.interrupt();
            }
            
            switch(lugap.getNumRequete())
            {
                case 0:
                {
                    System.out.println("\t tc - authentificcation");
                    //place la machine a etat a l'etat initial (reconnexion ou premiere connexion)
                    this.statemachine = ThreadClient.NOTLOGED;
                    
                    System.out.println("\t 0 ->tc- ## Le serveur test si les identifiants reçus sont corrects ## ");
            
                    if(tableLoginPwd.get(lugap.getLogin().getIdentifiant()) != null && tableLoginPwd.get(lugap.getLogin().getIdentifiant()).equals(lugap.getLogin().getPassword())) 
                    {
                        System.out.println("\t 0->tc - Login/Password OK ! Connexion acceptée");
                        lugap.setLoginStatus(Boolean.TRUE);
                    }
                    else
                    {
                        System.out.println("\t 0->tc - Login/Password not OK ! Connexion refusée");
                        lugap.setLoginStatus(Boolean.FALSE);
                    }
                    try 
                    {
                        //send connexion Ok or not
                        oos.writeObject(lugap);
                        this.statemachine = ThreadClient.LOGINOK;

                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                    
                case 1:
                {
                    System.out.println("\t tc - envoi des vols");
                    if(this.statemachine == ThreadClient.LOGINOK)
                    {
                        //envoie des Vols
                        AccessBD abd = null;
                        try {
                            abd = (AccessBD) Beans.instantiate(null, "database.utilities.AccessBD");
                            abd.setDriver(AccessBD.MYSQL);
                            abd.setBd("bd_airport");
                            abd.setHost("localhost");
                            abd.setId("student");
                            abd.setPasswd("student1");
                            abd.setPort("3306");
                            abd.init();
                            abd.setTable("Vols");
                            abd.setCondition("");
                            abd.select();


                            while(abd.getResultat().next())
                            {
                                lugap = new LUGAP();
                                lugap.setNumRequete(LUGAP.INFO_VOL);
                                Vol v = new Vol();
                                v.setNumVol(abd.getResultat().getString(1));
                                v.setDestination(abd.getResultat().getString(2));
                                v.setHeureDepart(abd.getResultat().getDate(3));
                                v.setHeureArrivee(abd.getResultat().getDate(4));
                                v.setHeureArriveePrevue(abd.getResultat().getDate(5));
                                v.setAvionID(abd.getResultat().getString(6));

                                lugap.setVol(v);

                                oos.writeObject(lugap);                        
                            }
                            lugap = new LUGAP();
                            oos.writeObject(lugap);
                            this.statemachine = ThreadClient.VOLOK;
                        } 
                        catch (IOException ex) {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (ClassNotFoundException ex) 
                        {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                
                case 2:
                {
                    if(this.statemachine == ThreadClient.VOLOK)
                    {
                        System.out.println("\t tc - bagages");
                        
                        AccessBD abd = null;
                        try {
                            abd = (AccessBD) Beans.instantiate(null, "database.utilities.AccessBD");
                            abd.setDriver(AccessBD.MYSQL);
                            abd.setBd("bd_airport");
                            abd.setHost("localhost");
                            abd.setId("student");
                            abd.setPasswd("student1");
                            abd.setPort("3306");
                            abd.init();
                            abd.setTable("bagage");
                            abd.setCondition("NumBillet IN (select NumBillet from Billet where NumVol like '" + lugap.getNumVol() + "')");
                            abd.select();

                            lugap = new LUGAP();
                            lugap.setNumRequete(LUGAP.INFO_BAGAGES);
                            ArrayList<Bagage> arb = new ArrayList<>(); 
                            lugap.setBagages(arb);
                            
                            while(abd.getResultat().next())
                            {
                                Bagage b = new Bagage();
                                b.setBagageID(abd.getResultat().getString(1));
                                b.setPoid(abd.getResultat().getInt(2));
                                b.setTypeBagages(abd.getResultat().getString(3));
                                b.setRegIDAgent(abd.getResultat().getInt(4));
                                b.setNumBillet(abd.getResultat().getString(5));
                                
                                lugap.getBagages().add(b);  
                                System.out.println("bagage : " + b);
                            }
                            oos.writeObject(lugap);
                            this.statemachine = ThreadClient.VOLOK;
                        } catch (IOException ex) {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                    break;
                }
                
                default :
                    break;
            }    
        }
    }
}