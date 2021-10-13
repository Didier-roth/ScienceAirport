package database.utilities;
import java.sql.*;
import java.io.Serializable;

/**
 *
 * @author didierroth
 */
public class AccessBD implements Serializable {
        
    public static final String MYSQL = "com.mysql.cj.jdbc.Driver";
    //public static final String ORACLE = "com.mysql.jdbc.Driver";
    
    private Class driver;
    private String table = null;
    private String condition = null;
    private String host = null;
    private String port;
    private String id;
    private String passwd;
    private ResultSet resultat;
    private Connection connexion;
    
    
    public AccessBD() 
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

    public String getHost() {
        return host;
    }

    public void setHost(String bd) {
        this.host = bd;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    public void init() throws ClassNotFoundException, SQLException
    {
        String co ="";
     
        co = "jdbc:mysql://"+getHost() + ":" + getPort() + "/bd_airport";
        
        connexion = DriverManager.getConnection(co, this.getId(), this.getPasswd());
    }
    
    public void select() throws SQLException
    {
        java.sql.Statement instruc = connexion.createStatement();
        if(condition == null)
        {
            this.resultat = instruc.executeQuery("select * from " + this.getTable());
            System.out.println("select * from " + this.getTable());
        }
        else
        {
            this.resultat = instruc.executeQuery("select * from " + this.getTable() + " where " + this.getCondition());
        }
    }
    
    public void count() throws SQLException
    {
        java.sql.Statement instruc = connexion.createStatement();
        if(condition == null)
        {
            this.resultat = instruc.executeQuery("select count() from " + this.getTable());
            System.out.println("select * from " + this.getTable());
        }
        else
        {
            this.resultat = instruc.executeQuery("select * from " + this.getTable() + " where " + this.getCondition());
        }
    }
    
}