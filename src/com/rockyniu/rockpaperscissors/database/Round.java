package com.rockyniu.rockpaperscissors.database;

public class Round {
	private String resultUuid;
	private int result;
	private String userId;
	private int selfChoice;
	private String competitorUuid;
	private int competitorChoice;
	private Long selfPlayTime;
	private Long competitorPlayTime;
	private Long modifiedTime;
	private boolean deleted;
	/**
	 * @return the resultUuid
	 */
	public String getId() {
		return resultUuid;
	}
	/**
	 * @param resultUuid the resultUuid to set
	 */
	public void setId(String resultUuid) {
		this.resultUuid = resultUuid;
	}
	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the selfUuid
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the selfUuid to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the selfChoice
	 */
	public int getSelfChoice() {
		return selfChoice;
	}
	/**
	 * @param selfChoice the selfChoice to set
	 */
	public void setSelfChoice(int selfChoice) {
		this.selfChoice = selfChoice;
	}
	/**
	 * @return the competitorUuid
	 */
	public String getCompetitorId() {
		return competitorUuid;
	}
	/**
	 * @param competitorUuid the competitorUuid to set
	 */
	public void setCompetitorId(String competitorUuid) {
		this.competitorUuid = competitorUuid;
	}
	/**
	 * @return the competitorChoice
	 */
	public int getCompetitorChoice() {
		return competitorChoice;
	}
	/**
	 * @param competitorChoice the competitorChoice to set
	 */
	public void setCompetitorChoice(int competitorChoice) {
		this.competitorChoice = competitorChoice;
	}
	/**
	 * @return the selfPlayTime
	 */
	public Long getSelfPlayTime() {
		return selfPlayTime;
	}
	/**
	 * @param selfPlayTime the selfPlayTime to set
	 */
	public void setSelfPlayTime(Long selfPlayTime) {
		this.selfPlayTime = selfPlayTime;
	}
	/**
	 * @return the competitorPlayTime
	 */
	public Long getCompetitorPlayTime() {
		return competitorPlayTime;
	}
	/**
	 * @param competitorPlayTime the competitorPlayTime to set
	 */
	public void setCompetitorPlayTime(Long competitorPlayTime) {
		this.competitorPlayTime = competitorPlayTime;
	}
	/**
	 * @return the updatedTime
	 */
	public Long getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}	
}
