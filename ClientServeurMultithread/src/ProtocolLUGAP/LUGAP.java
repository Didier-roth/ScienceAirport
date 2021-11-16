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
public class LUGAP implements Serializable
{
    /* -_-_-_-_-_-_-_-_-_-_- construteur  -_-_-_-_-_-_-_-_-_*/
    public LUGAP() 
    {
        this.NumVol = null;
        this.bagages = null;
        this.login = null;
        this.loginstatus = null;
        this.numRequete = 0;
        this.vol = null;
    }

    
    
    public LUGAP(int numRequete, String NumVol) {
        this.numRequete = numRequete;
        this.NumVol = NumVol;
    }
    
    
    /* -_-_-_-_-_-_-_-_-_-_- static  -_-_-_-_-_-_-_-_-_*/
    public static int LOGIN = 0;
    
    public static int INFO_BAGAGES = 1;
 
    public static int INFO_VOL = 2;
    
    
    
    
    /* -_-_-_-_-_-_-_-_-_-_- variable membre  -_-_-_-_-_-_-_-_-_*/
    private int numRequete;
   
    /* LOGIN */
    Boolean loginstatus;
    Login login;
    
    /* INFO BAGAGES */
    String NumVol;
    Bagage bagages;
    
    /* INFO VOL */
    Vol vol;
    
    /* -_-_-_-_-_-_-_-_-_-_- setter getter  -_-_-_-_-_-_-_-_-_*/
    public int getNumRequete() {
        return numRequete;
    }

    public void setNumRequete(int numRequete) {
        this.numRequete = numRequete;
    }

    public Boolean getLoginStatus() {
        return loginstatus;
    }

    public void setLoginStatus(Boolean loginstatus) {
        this.loginstatus = loginstatus;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getNumVol() {
        return NumVol;
    }

    public void setNumVol(String NumVol) {
        this.NumVol = NumVol;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }
      
    /* -_-_-_-_-_-_-_-_-_-_- methodes  -_-_-_-_-_-_-_-_-_*/

    @Override
    public String toString() {
        return "LUGAP{" + "numRequete=" + numRequete + ", loginstatus=" + loginstatus + ", login=" + login + ", NumVol=" + NumVol + ", bagages=" + bagages + ", vol=" + vol + '}';
    }
}


