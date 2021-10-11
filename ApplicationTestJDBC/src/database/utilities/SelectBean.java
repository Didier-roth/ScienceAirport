/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;
import java.sql.*;
import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author didierroth
 */
public class SelectBean implements Serializable {
        
    public static final String MYSQL = "com.mysql.jdbc.Driver";
    public static final String ORACLE = "com.mysql.jdbc.Driver";
    
    private Class driver;
    private String table = null;
    private String condition = null;
    private ResultSet resultat;
    private Connection connexion;
    
    
    public SelectBean() 
    {
        
    }
  
    /**
     * Get the value of driver
     *
     * @return the value of driver
     */
    public String getDriver() {
        return driver.getName();
    }

    /**
     * Set the value of driver
     *
     * @param driver new value of driver
     * @exception ClassNotFoundException is class not found
     */
    public void setDriver(String driver) throws ClassNotFoundException 
    {
            this.driver = Class.forName(driver);
    }

     
    /**
     * Get the value of Tbale
     *
     * @return the value of table (select * from table where condition)
     */
    public String getTable() {
        return table;
    }

    /**
     * Set the value of Table
     *
     * @param table new value of table (select * from table where condition)
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Get the value of Condition (where)
     *
     * @return the value of Condition (select * from table where condition)
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Set the value of Condition
     *
     * @param condition new value of condition (select * from table where condition)
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Get the value of resultat of the querry
     *
     * @return the resutlat of the querry (select * from table where condition)
     */
    public ResultSet getResultat() {
        return resultat;
    }

    /**
     * Get the value of Connexion 
     *
     * @return the value of Connexion
     */
    public Connection getConnexion() {
        return connexion;
    }
    
    
    public void init()
    {
        
    }
    
    public void run()
    {
        
    }
    
}
