/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProtocolLUGAP;

import java.io.Serializable;

/**
 *
 * @author didierroth
 */
public class Bagage implements Serializable
{
    String BagageID;
    int poid;
    String typeBagages;
    int RegIDAgent;
    String NumBillet;

    public String getBagageID() {
        return BagageID;
    }

    public void setBagageID(String BagageID) {
        this.BagageID = BagageID;
    }

    public int getPoid() {
        return poid;
    }

    public void setPoid(int poid) {
        this.poid = poid;
    }

    public String getTypeBagages() {
        return typeBagages;
    }

    public void setTypeBagages(String typeBagages) {
        this.typeBagages = typeBagages;
    }

    public int getRegIDAgent() {
        return RegIDAgent;
    }

    public void setRegIDAgent(int RegIDAgent) {
        this.RegIDAgent = RegIDAgent;
    }

    public String getNumBillet() {
        return NumBillet;
    }

    public void setNumBillet(String NumBillet) {
        this.NumBillet = NumBillet;
    }

    @Override
    public String toString() {
        return "Bagage{" + "BagageID=" + BagageID + ", poid=" + poid + ", typeBagages=" + typeBagages + ", RegIDAgent=" + RegIDAgent + ", NumBillet=" + NumBillet + '}';
    }
    
    
}
