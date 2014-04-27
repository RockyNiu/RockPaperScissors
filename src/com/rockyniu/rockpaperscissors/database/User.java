package com.rockyniu.rockpaperscissors.database;

public class User {
	private String uuid;
	private String name;
	private String encryptedPwd;
	/**
	 * @return the uuid
	 */
	public String getId() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setId(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the encryptedPwd
	 */
	public String getPwd() {
		return encryptedPwd;
	}
	/**
	 * @param encryptedPwd the encryptedPwd to set
	 */
	public void setPwd(String encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}
}
