/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japplication_mail;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.DefaultListModel;

/**
 *
 * @author didierroth
 */
public class threadRcv extends Thread
{
    public static int MINUTEUR = 2;
    private int NBMAIL = 0;
    jApplication_Mail jam;
    public threadRcv(jApplication_Mail jappm) 
    {
        jam = jappm;
    }
    
    
    
    @Override
    public void run() 
    {
        while(true)
        {
            try 
            {
                this.fctrcv();
                sleep(2 * 60 * 1000);
                //ajouter rafraichir
                //verifier piece jointe
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(threadRcv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void fctrcv()
    {
        DefaultListModel dlm = new DefaultListModel();
        Properties prop = System.getProperties(); 
        System.out.println("Création d'une session mail");
        prop.put("mail.pop3.host", jam.getParam().getPop3Server()); 
        prop.put("mail.pop3.socketFactory", jam.getParam().getPop3Port());
        prop.put("mail.pop3.user", jam.getParam().getPop3User());
        if(jam.getParam().getPop3Server().equals("pop.gmail.com"))
        {
            prop.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.pop3.socketFactory.fallback", "false");
            prop.put("mail.pop3.socketFactory.port", "995");
            prop.put("mail.store.protocol", "pop3");
            prop.put("mail.pop3.ssl.protocols", "TLSv1.2");
        }
        

        Authenticator auth = new Authenticator() 
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(jam.getParam().getPop3User(), jam.getParam().getPop3Password());
            }
        };

        Session session = Session.getDefaultInstance(prop, auth);

        try {
            String user = jam.getParam().getPop3User();
            String pwd = jam.getParam().getPop3Password();
            String host = jam.getParam().getPop3Server();

            System.out.println("Obtention d'un objet store");

            Store st = session.getStore("pop3");

            System.out.println("user : " + user);
            System.out.println("pswd : " + pwd);
            System.out.println("host : " + host);
            st.connect(host, user, pwd);

            System.out.println("Obtention d'un objet folder");
            Folder f = st.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            System.out.println("Obtention des messages");
            
            Message msg[] = f.getMessages();
            System.out.println("Nombre de messages : " + f.getMessageCount()); 
            jam.SetNbMail(f.getMessageCount());
            System.out.println("Nombre de nouveaux messages : " + f.getNewMessageCount());
            jam.SetNbMailNL(f.getNewMessageCount());

            System.out.println("nbail deja lu : " + f.getMessageCount());
            //f.getMessageCount() > 0
            if(f.getMessageCount() > NBMAIL)
            {
                System.out.println("msg length : " + msg.length);
                System.out.println("Liste des messages : "); 

                String pathFile = this.jam.getParam().getPopDownloadFolder();
                NBMAIL = f.getMessageCount();
                

                for (int i=0; i<msg.length; i++)
                {
                    //System.out.println("\n<nHeaders du message n°" + (i+1)); 
                    Enumeration e = msg[i].getAllHeaders();
                    Header h = (Header)e.nextElement();
                    ArrayList<String> arl = new ArrayList<>();
                    while (e.hasMoreElements())
                    {
                        if(h.getName().equalsIgnoreCase("Received"))
                        {
                            System.out.println(h.getName() + " --> " + h.getValue()); 
                            arl.add(h.getName() + " --> " + h.getValue());
                        }
                        h = (Header)e.nextElement();
                    }

                    if (msg[i].isMimeType("text/plain")) 
                    {
                        System.out.println("Expéditeur : " + msg[i].getFrom()[0] + "Sujet = " + msg[i].getSubject()); 
                        Mail mail = new Mail(msg[i].getFrom()[0].toString(),msg[i].getSubject(),(String)msg[i].getContent());
                        mail.setHeader(arl);
                        dlm.addElement(mail);
                    } 
                    else
                    {
                        ArrayList<String> piece = new ArrayList<>();

                        System.out.println("Expéditeur : " + msg[i].getFrom() [0] + "Sujet = " + msg[i].getSubject()); 

                        Mail mail = new Mail(msg[i].getFrom()[0].toString(),msg[i].getSubject(),"");

                        mail.setHeader(arl);

                        //Récupération du conteneur Multipart
                        Multipart msgMP = (Multipart)msg[i].getContent();
                        int np = msgMP.getCount();
                        System.out.println("-- Nombre de composantes = " + np);
                        // Scan des BodyPart

                        for (int j=0; j<np; j++) 
                        {
                            System.out.println("--Composante n° " + j); 
                            Part p = msgMP.getBodyPart(j);
                            String d = p.getDisposition();
                            if (p.isMimeType("text/plain"))
                            {
                                System.out.println("Texte : " + (String)p.getContent());
                                mail.setTexte((String)p.getContent());
                            }

                            if (d!=null && d.equalsIgnoreCase(Part.ATTACHMENT)) 
                            {
                                InputStream is = p.getInputStream();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
                                int c;
                                while ((c = is.read()) != -1) 
                                    baos.write(c);
                                baos.flush();
                                String nf = this.jam.getParam().getPopDownloadFolder() + "/" + p.getFileName();

                                
                                
                                FileOutputStream fos =new FileOutputStream(nf); 
                                baos.writeTo(fos);
                                fos.close();
                                System.out.println("Pièce attachée " + nf + " récupérée");
                                piece.add(nf);
                            }
                        } // fin for j
                        mail.setArrayPath(piece);
                        dlm.addElement(mail);
                    }
                }
                jam.SetListMail(dlm);
                System.out.println("Fin des messages"); 
            }
        }
        catch (NoSuchProviderException e) 
        {
            System.out.println("Erreur sur provider : " + e.getMessage());
        }
        catch (MessagingException e) 
        {
            System.out.println("Erreur sur message");
            Logger.getLogger(threadRcv.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (Exception e) 
        {
            System.out.println("Erreur indéterminée : " + e.getMessage()); 
        }
    }
    
}
