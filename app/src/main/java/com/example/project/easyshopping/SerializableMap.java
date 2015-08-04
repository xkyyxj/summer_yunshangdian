package com.example.project.easyshopping;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * SerializableMap.java
 * @author acer
 *
 *
 */
public class SerializableMap implements Serializable {

	/**
	 * serialVersionUID 随机生成
	 */
	private static final long serialVersionUID = 2639620495006189493L;
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}