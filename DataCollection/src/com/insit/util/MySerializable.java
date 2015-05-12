/**
 * DataCollection/com.insit.util/MySerializable.java
 * 2014-3-21/下午4:26:48 by nano
 */
package com.insit.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;

import net.sf.json.JSONObject;

/**
 * @author nano
 *
 */
@SuppressWarnings("serial")
public class MySerializable implements Serializable {

	@Override
    public String toString() {
    	JSONObject json = new JSONObject();
    	Field[] fields = this.getClass().getDeclaredFields();
    	try {
        	for (Field field : fields) {
        		field.setAccessible(true);
        		if (field.getType().getPackage().getName().equals("com.insit.model")) {
        			JSONObject index = JSONObject.fromObject(field.get(this).toString());
        			Iterator it = index.keys();
        			while (it.hasNext()) {
        				Object key = it.next();
        				json.put(key, index.get(key));
        			}
        		} else {
        			json.put(field.getName(), field.get(this));
        		}
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return json.toString();
    }
}
