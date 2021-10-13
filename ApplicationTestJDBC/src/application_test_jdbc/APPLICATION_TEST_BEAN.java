/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application_test_jdbc;
import java.sql.*;
import database.utilities.AccessBD;
import java.beans.Beans;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author murad
 */
public class APPLICATION_TEST_BEAN
{
    public static void main(String[] args)
    {
        AccessBD bean = null;
        
        try
        {
            bean = (AccessBD) Beans.instantiate(null, "database.utilities.AccessBD");
        } catch (IOException ex) {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        bean.setBd("bd_airport");
         bean.setHost("localhost");
         bean.setId("student");
         bean.setPasswd("student1");
         bean.setPort("3306");
         bean.setTable("Agent");
         
        try {
            bean.setDriver(AccessBD.MYSQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try 
        {
            bean.init();
            bean.select();
            System.out.println("test apres run");
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            int cpt = 0;
            while(bean.getResultat().next())
            {
                if (cpt==0) 
                    System.out.println("Parcours du curseur"); 
                cpt++;
//                int id = bean.getResultat().getInt("RegIDAgent");
//                String nom = bean.getResultat().getString("nom");
//                String prenom = bean.getResultat().getString("prenom");
//                System.out.println(cpt + "\nid : " + id + "\nnom : " + nom + "\nprenom : " + prenom);
                for(int i = 0; i < bean.getResultat().getMetaData().getColumnCount(); i++)
                    System.out.println(bean.getResultat().getObject(i+1));
                System.out.println();
                
            }
        }
       catch(Exception e)
        {
            Logger.getLogger(APPLICATION_TEST_BEAN.class.getName()).log(Level.SEVERE, null, e);
        }
       
    }
}
