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
public class Vol implements Serializable{
   String NumVol;
   String destination;
   Date HeureDepart;
   Date HeureArrivee;
   Date HeureArriveePrevue;
   String AvionID;

    @Override
    public String toString() {
        return "Vol " + NumVol + " - " + destination + " " + HeureDepart;
    }
   
}

