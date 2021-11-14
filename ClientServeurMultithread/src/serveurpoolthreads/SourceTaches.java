/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurpoolthreads;

import java.net.Socket;

/**
 *
 * @author murad
 */

public interface SourceTaches
// synchronized ne s'utilise pas dans un interface
{
    public Socket getClient() throws InterruptedException;
    public boolean existClients();
    public void addClient (Socket s);
}