// $Id$
/*
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ftpserver.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.ftpserver.ftplet.Configuration;
import org.apache.ftpserver.ftplet.FtpException;

/**
 * XML based configuration element.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public
class XmlConfiguration implements Configuration {
    
    private String name;
    private String value;
    private ArrayList children;
    
    /**
     * Create a new <code>XmlConfiguration</code> instance.
     */
    public XmlConfiguration(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of this element.
     */
    String getName() {
        return name;
    }
    
    /**
     * Return count of children.
     */
    int getChildCount() {
        if( null == children ) {
            return 0;
        }
        return children.size();
    }
    
    /**
     * Set data to the value of this element.
     */
    void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Add a child <code>XmlConfiguration</code> to this element.
     */
    void addChild(XmlConfiguration elem) {
        if(children == null) {
            children = new ArrayList();
        }

        children.add( elem );
    }
    
    /**
     * Get string - if not found throws FtpException.
     */
    public String getString(String param) throws FtpException {
        XmlConfiguration child = (XmlConfiguration)getConfiguration(param);
        String val = child.value;
        if(val == null) {
            throw new FtpException("Not found : " + param);
        }
        return val;
    }
    
    /**
     * Get string - if not found returns the default value.
     */
    public String getString(String param, String defaultVal) {
        try {
            return getString(param);
        }
        catch(Exception ex) {
            return defaultVal;
        }
    }
    
    
    /**
     * Get integer - if not found throws FtpException.
     */
    public int getInt(String param) throws FtpException {
        String val = getString(param);
        try {
            return Integer.parseInt(val);
        }
        catch(Exception ex) {
            throw new FtpException("XmlConfiguration.getInt()", ex);
        }
    }
    
    /**
     * Get integer - if not found returns the default value.
     */
    public int getInt(String param, int defaultVal) {
        int retVal = defaultVal;
        try {
            retVal = getInt(param);
        }
        catch(Exception ex) {
        }
        return retVal;
    }
    
    /**
     * Get long - if not found throws FtpException.
     */
    public long getLong(String param) throws FtpException {
        String val = getString(param);
        try {
            return Long.parseLong(val);
        }
        catch(Exception ex) {
            throw new FtpException("XmlConfiguration.getLong()", ex);
        }
    }
    
    /**
     * Get long - if not found returns the default value.
     */
    public long getLong(String param, long defaultVal) {
        long retVal = defaultVal;
        try {
            retVal = getLong(param);
        }
        catch(Exception ex) {
        }
        return retVal;
    }
    
    /**
     * Get boolean - if not found throws FtpException.
     */
    public boolean getBoolean(String param) throws FtpException {
        String val = getString(param);
        return val.equalsIgnoreCase("true");
    }
    
    /**
     * Get boolean - if not found returns the default value.
     */
    public boolean getBoolean(String param, boolean defaultVal) {
        boolean retVal = defaultVal;
        try {
            retVal = getBoolean(param);
        }
        catch(Exception ex) {
        }
        return retVal;
    }
    
    /**
     * Get double - if not found throws FtpException.
     */
    public double getDouble(String param) throws FtpException {
        String val = getString(param);
        try {
            return Double.parseDouble(val);
        }
        catch(Exception ex) {
            throw new FtpException("XmlConfiguration.getDouble()", ex);
        }
    }
    
    /**
     * Get double - if not found returns the default value.
     */
    public double getDouble(String param, double defaultVal) {
        double retVal = defaultVal;
        try {
            retVal = getDouble(param);
        }
        catch(Exception ex) {
        }
        return retVal;
    }
    
    /**
     * Return the first <code>XmlConfiguration</code> object child of this
     * associated with the given name. 
     */
    public Configuration getConfiguration(String param) throws FtpException {
        XmlConfiguration child = null;
        
        if(children != null) {
            for(Iterator it=children.iterator(); it.hasNext(); ) {
                XmlConfiguration thisChild = (XmlConfiguration)it.next();
                if( thisChild.name.equals(param) ) {
                    child = thisChild;
                    break;
                }
            }
        } 
        
        if(child == null) {
            throw new FtpException("Not found : " + param);
        }
        return child;
    }

    /**
     * Return the first <code>XmlConfiguration</code> object child of this
     * associated with the given name. 
     */
    public Configuration getConfiguration(String param, Configuration defVal) {
        Configuration config = null;
        try {
            config = getConfiguration(param);
        }
        catch(Exception ex) {
            config = defVal;
        }
        if(config == null) {
            config = defVal;
        }
        return config;
    }
    
    /**
     * Get the configuration keys.
     */
    public Enumeration getKeys() {
        
        // add configuration keys
        ArrayList keys = new ArrayList();
        if(children != null) {
            for(int i=0; i<children.size(); ++i) {
                XmlConfiguration child = (XmlConfiguration)children.get(i);
                keys.add(child.name);
            }
        }
        return Collections.enumeration(keys);
    }
}
