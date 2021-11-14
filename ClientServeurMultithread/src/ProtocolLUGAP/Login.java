/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolLUGAP;

import java.io.Serializable;

/**
 *
 * @author murad
 */
public class Login implements Serializable
{
    private String identifiant;
    private String password; 
    
    public Login(String id, String pwd)
    {
        identifiant = id;
        password = pwd;
    }
      
    public String getIdentifiant()
    {
        return identifiant;
    }
    
     public String getPassword()
    {
        return password;
    }
     
    
    public void Affiche()
    {
        System.out.println("Identifiant : " + identifiant);
        System.out.println("Mot de passe : " + password);
    }
}
