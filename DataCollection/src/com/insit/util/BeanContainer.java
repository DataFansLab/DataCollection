/**
 * DataCollection/com.insit.util/BeanContainer.java
 * 2014-3-24/下午3:38:48 by nano
 */
package com.insit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author nano
 *
 */
public class BeanContainer {

	private static ApplicationContext context;
	
	@Autowired
	public void setBeanFactory (ApplicationContext context) {
		this.context = context;
	}
	
	public static Object getBean(String name) {
		return context.getBean(name);
	}
}
