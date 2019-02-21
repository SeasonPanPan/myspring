/**
 * 
 */
package com.plf.bean;

/**
 * bean属性
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public class PropertyDefinition {
	private String name;
	private String value;
	
	//引用类型
	private String ref;

	public PropertyDefinition(String name, String value, String ref) {
		this.name = name;
		this.value = value;
		this.ref = ref;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

}
