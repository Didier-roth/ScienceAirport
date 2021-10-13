/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;
import java.beans.Beans;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author didierroth
 */
public class TestJCBD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException 
    {
        // TODO code application logic here
        SelectBean sb = null ;
        try 
        {
            sb = (SelectBean) Beans.instantiate(null, "database.utilities.SelectBean");
        } catch (IOException ex) 
        {
            Logger.getLogger(TestJCBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sb.init();
            sb.run();
        } catch (SQLException ex) {
            Logger.getLogger(TestJCBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sb.setDriver(SelectBean.MYSQL);
        sb.setCondition(null);
        sb.setHost("localhost");
        sb.setId("student");
        sb.setPasswd("student1");
        sb.setPort("3306");
        sb.setTable("Avion");
        try 
        {
            while(sb.getResultat().next())
            {
                System.out.println("agent : " + sb.getResultat().getInt("RegIDAgent"));
                System.out.println("nom : " + sb.getResultat().getNString("nom"));
                System.out.println("prenom : " + sb.getResultat().getString("prenom"));
                System.out.println("fonction : " + sb.getResultat().getString("poste"));
                System.out.println("ddn : " + sb.getResultat().getDate("dateNaissance"));
                
            }
        } catch (SQLException ex) 
        {
            Logger.getLogger(TestJCBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
