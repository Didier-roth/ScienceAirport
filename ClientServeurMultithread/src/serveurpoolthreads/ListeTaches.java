/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurpoolthreads;

import java.util.*;
import java.net.Socket;

/**
 *
 * @author murad
 */


public class ListeTaches implements SourceTaches
{
    private LinkedList<Socket> listeClients;
    
    public ListeTaches()
    {
        listeClients = new LinkedList<Socket>();
    }
    
    @Override
    public synchronized boolean existClients()
    {
        return !listeClients.isEmpty();
    }
    
    @Override
    public synchronized void addClient (Socket s)
    {
        listeClients.addLast(s);
        System.out.println("ListeTaches : Socket associe au client ajoute dans la file");
        notify();
    }
    
    @Override
    public synchronized Socket getClient() throws InterruptedException
    {
        System.out.println("getClient avant wait");
        while (!existClients()) 
            wait();
        return (Socket)listeClients.remove(); //pas besoin d'enlever de la liste quand le client se déconnectera, il est enlevé déja ici de la liste
    }
   
}