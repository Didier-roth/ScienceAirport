/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japplication_mail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author didierroth
 */
public class parametre {
    private static final String filename = "mails.properties";
    private Properties prop = new Properties();

    public void load()
    {
        try
        {
            prop.load(new FileReader(filename));
        } catch (IOException e)
        {
            try
            {
                new File(filename).createNewFile();
                
                setSmtpServer("?");
                setSmtpPort(0);
                setAuthentification(false);
                setEmail("?");
                setPassword("?");
                setTtls(false);
            
                setPop3Server("?");
                setPop3Port(0);
                setPop3User("?");
                setPop3Password("?");
                setPop3DownloadFolder("?");
                
                prop.store(new FileWriter(filename), "");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(parametre.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    public void setSmtpServer(String server)
    {
        prop.setProperty("smtp.server", server);
    }

    public void setSmtpPort(int port)
    {
        prop.setProperty("smtp.port", Integer.toString(port));
    }

    public void setTtls(boolean ttls)
    {
        prop.setProperty("ttls", Boolean.toString(ttls));
    }

    public void setAuthentification(boolean auth)
    {
        prop.setProperty("authentication", Boolean.toString(auth));
    }

    public void setEmail(String email)
    {
        prop.setProperty("email", email);
    }

    public void setPassword(String password)
    {
        prop.setProperty("password", password);
    }

    public void setPop3Server(String server)
    {
        prop.setProperty("pop3.server", server);
    }

    public void setPop3Port(int port)
    {
        prop.setProperty("pop3.port", Integer.toString(port));
    }
    
    public void setPop3User(String User)
    {
        prop.setProperty("pop3.user", User);
    }
    
    public void setPop3Password(String pwd)
    {
        prop.setProperty("pop3.password", pwd);
    }
    
    public void setPop3DownloadFolder(String foldr)
    {
        prop.setProperty("pop3.download.folder", foldr);
    }

    public String getPopDownloadFolder()
    {
        return prop.getProperty("pop3.download.folder");
    }
            
    public String getPop3User()
    {
        return prop.getProperty("pop3.user");
    }
    
    public String getPop3Password()
    {
        return prop.getProperty("pop3.password");
    }
    
    public String getSmtpServer()
    {
        return prop.getProperty("smtp.server");
    }

    public int getSmtpPort()
    {
        return Integer.parseInt(prop.getProperty("smtp.port"));
    }

    public Boolean getTtls()
    {
        return Boolean.parseBoolean(prop.getProperty("ttls"));
    }

    public Boolean getAuthentification()
    {
        return Boolean.parseBoolean(prop.getProperty("authentication"));
    }

    public String getEmail()
    {
        return prop.getProperty("email");
    }

    public String getPassword()
    {
        return prop.getProperty("password");
    }

    public String getPop3Server()
    {
        return prop.getProperty("pop3.server");
    }

    public int getPop3Port()
    {
        return Integer.parseInt(prop.getProperty("pop3.port"));
    }
}
