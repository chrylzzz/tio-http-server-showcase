package org.tio.http.server.showcase.init;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性
 * @author tanyaowu
 */
public class P {
	private static Logger log = LoggerFactory.getLogger(P.class);

	public static Configuration config;

	public static void init() {
		Configurations configs = new Configurations();
		try {
			config = configs.properties("app.properties");
		} catch (ConfigurationException e) {
			log.error("", e);
		}
	}
	
	public static String getString(String key) {
		return config.getString(key);
	}
	
	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}
	
	public static Integer getInt(String key) {
		return config.getInt(key);
	}
	
	public static Integer getInt(String key, Integer defaultValue) {
		return config.getInt(key, defaultValue);
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {
		init();
	}

	/**
	 *
	 * @author tanyaowu
	 */
	public P() {
	}
}
