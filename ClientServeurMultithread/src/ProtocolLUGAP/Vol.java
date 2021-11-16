/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolLUGAP;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author didierroth
 */
public class Vol implements Serializable
{
   String NumVol;
   String destination;
   Date HeureDepart;
   Date HeureArrivee;
   Date HeureArriveePrevue;
   String AvionID;

    public Vol()
    {
        
    }

    @Override
    public String toString() {
        return "Vol " + NumVol + " - " + destination + " " + HeureDepart;
    }

    public Vol(String NumVol, String destination, Date HeureDepart, Date HeureArrivee, Date HeureArriveePrevue, String AvionID) {
        this.NumVol = NumVol;
        this.destination = destination;
        this.HeureDepart = HeureDepart;
        this.HeureArrivee = HeureArrivee;
        this.HeureArriveePrevue = HeureArriveePrevue;
        this.AvionID = AvionID;
    }

    public String getNumVol() {
        return NumVol;
    }

    public void setNumVol(String NumVol) {
        this.NumVol = NumVol;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getHeureDepart() {
        return HeureDepart;
    }

    public void setHeureDepart(Date HeureDepart) {
        this.HeureDepart = HeureDepart;
    }

    public Date getHeureArrivee() {
        return HeureArrivee;
    }

    public void setHeureArrivee(Date HeureArrivee) {
        this.HeureArrivee = HeureArrivee;
    }

    public Date getHeureArriveePrevue() {
        return HeureArriveePrevue;
    }

    public void setHeureArriveePrevue(Date HeureArriveePrevue) {
        this.HeureArriveePrevue = HeureArriveePrevue;
    }

    public String getAvionID() {
        return AvionID;
    }

    public void setAvionID(String AvionID) {
        this.AvionID = AvionID;
    }
   
}

