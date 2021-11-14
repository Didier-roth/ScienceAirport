/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolLUGAP;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author murad
 */
public class LUGAP implements Serializable
{
    /* -_-_-_-_-_-_-_-_-_-_- construteur  -_-_-_-_-_-_-_-_-_*/
    public LUGAP() {}

    
    
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
    private List<Bagage> bagages;
    
    /* INFO VOL */
    private List<Vol> vols;
    
    /* -_-_-_-_-_-_-_-_-_-_- setter getter  -_-_-_-_-_-_-_-_-_*/
    public int getNumRequete() {
        return numRequete;
    }

    public void setNumRequete(int numRequete) {
        this.numRequete = numRequete;
    }

    public List<Bagage> getBagages() {
        return bagages;
    }

    public void setBagages(List<Bagage> bagages) {
        this.bagages = bagages;
    }

    public List<Vol> getVols() {
        return vols;
    }

    public void setVols(List<Vol> vols) {
        this.vols = vols;
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
      
    /* -_-_-_-_-_-_-_-_-_-_- methodes  -_-_-_-_-_-_-_-_-_*/
}


