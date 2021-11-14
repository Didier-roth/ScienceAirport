/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurpoolthreads;

import java.net.*;
import java.io.*;
import requetepoolthreads.Requete;

/**
 *
 * @author murad
 */

    
public class ThreadServeur extends Thread
{
    private int port;
    private SourceTaches clientsAGerer;
    private ConsoleServeur guiApplication;
    private ServerSocket SSocket = null;
    
    public ThreadServeur(int p, SourceTaches st, ConsoleServeur fs)
    {
        port = p; clientsAGerer = st; guiApplication = fs;
    }
    public void run()
    {
        try
        {
            SSocket = new ServerSocket(port);
            System.out.println("Le serveur ecoute desormais sur le port : " + port);
            
        }
        catch (IOException e)
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
        }
        
        // Démarrage du pool de threads
        for (int i=0; i<3; i++) // 3 devrait être constante ou une propriété du fichier de config
        {
            ThreadClient thr = new ThreadClient (clientsAGerer, "Thread du pool n°" + String.valueOf(i)); //on passe aux threads un objet implémentant SourceTaches (la liste de taches)
            thr.start();
        }
        
        // Mise en attente du serveur
        Socket CSocket = null; //socket qui sera attachée à un client
        
        while (!isInterrupted())
        {
            try
            {
                System.out.println("************ Serveur en attente");
                CSocket = SSocket.accept();
                guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+ "#accept#thread serveur");
            }
            catch (IOException e)
            {
                System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); System.exit(1);
            }
            
            /*
            //pas besoin de cette partie car on ne donne plus un runnable au thread mais une socket pour qu'il s'occupe de la communication
            ObjectInputStream ois=null;
            Requete req = null;
            try
            {
                ois = new ObjectInputStream(CSocket.getInputStream());
                req = (Requete)ois.readObject();
                System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());
            }
            catch (ClassNotFoundException e)
            {
                System.err.println("Erreur de def de classe [" + e.getMessage() + "]");
            }
            catch (IOException e)
            {
                System.err.println("Erreur ? [" + e.getMessage() + "]");
            }
            
            Runnable travail = req.createRunnable(CSocket, guiApplication);
            if (travail != null)
            {
                clientsAGerer.addClient(travail);
                System.out.println("Travail mis dans la file");
            }
            else System.out.println("Pas de mise en file");
            */
             
            // on ajoute la socket client qu'on a reçu dans la LinkedList que les threads clients pourront consulter
            clientsAGerer.addClient(CSocket);
            
        }
    }
}
