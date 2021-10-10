/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.utilities;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author didierroth
 */
public class SelectBean implements Serializable {
        
    private String driver = null;
    private String table = null;
    private String condition = null;
    private 
    
        
    public SelectBean() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of driver
     *
     * @return the value of driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Set the value of driver
     *
     * @param driver new value of driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    
}
