/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japplication_mail;

import java.util.ArrayList;

/**
 *
 * @author didierroth
 */
public class Mail {
    private String expediteur;
    private String sujet;
    private String texte;
    private ArrayList<String> arrayPath;
    private ArrayList<String> Header;

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public ArrayList<String> getArrayPath() {
        return arrayPath;
    }

    public void setArrayPath(ArrayList<String> arrayPath) {
        this.arrayPath = arrayPath;
    }

    public Mail(String expediteur, String sujet, String texte) {
        this.expediteur = expediteur;
        this.sujet = sujet;
        this.texte = texte;
        this.arrayPath = null;
    }

    public Mail(String expediteur, String sujet, String texte, ArrayList<String> arrayPath) {
        this.expediteur = expediteur;
        this.sujet = sujet;
        this.texte = texte;
        this.arrayPath = arrayPath;
    }

    @Override
    public String toString() {
        return "(" + expediteur + ") - " + sujet;
    }
    
    public String getPrintableText()
    {
        String s ;
        s = "expediteur : " + this.getExpediteur() + "\n\n" + this.texte;
        if(arrayPath != null)
        {
            s += "\n";
            for (String st : arrayPath)
            {
                s = s + st + "\n";
            }
        }
        return s;
    }

    public ArrayList<String> getHeader() {
        return Header;
    }

    public void setHeader(ArrayList<String> Header) {
        this.Header = Header;
    }
    
    public String getPrintableHeader()
    {
        String s ;
        s="";
        if(Header != null)
        {
            for (String st : Header)
            {
                s = s + st + "\n";
            }
        }
        return s;
    }
}
