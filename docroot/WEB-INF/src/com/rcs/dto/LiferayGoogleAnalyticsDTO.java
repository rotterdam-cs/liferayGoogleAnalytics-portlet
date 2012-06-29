package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiferayGoogleAnalyticsDTO  implements Serializable {
	private static final long serialVersionUID = 1L;	
	boolean isSuccess = false;
	String message = "";	
	
	List<String> dates = new ArrayList<String>();
	List<String> pastDates = new ArrayList<String>();
	LiferayGoogleAnalyticsValuesDTO visits = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO visitors = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO pageviews = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO pageviewsPerVisit = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO visitBounceRate = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO avgTimeOnSite = new LiferayGoogleAnalyticsValuesDTO();
	LiferayGoogleAnalyticsValuesDTO percentNewVisits = new LiferayGoogleAnalyticsValuesDTO();
	String organicVisits;
	String referralVisits;
	String directVisits;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public void addDates(String date){
		dates.add(date);
	}
	public List<String> getPastDates() {
		return pastDates;
	}
	public void setPastDates(List<String> pastDates) {
		this.pastDates = pastDates;
	}
	public void addPastDates(String date){
		pastDates.add(date);
	}
	public LiferayGoogleAnalyticsValuesDTO getVisits() {
		return visits;
	}
	public void setVisits(LiferayGoogleAnalyticsValuesDTO visits) {
		this.visits = visits;
	}
	public LiferayGoogleAnalyticsValuesDTO getVisitors() {
		return visitors;
	}
	public void setVisitors(LiferayGoogleAnalyticsValuesDTO visitors) {
		this.visitors = visitors;
	}
	public LiferayGoogleAnalyticsValuesDTO getPageviews() {
		return pageviews;
	}
	public void setPageviews(LiferayGoogleAnalyticsValuesDTO pageviews) {
		this.pageviews = pageviews;
	}
	public LiferayGoogleAnalyticsValuesDTO getPageviewsPerVisit() {
		return pageviewsPerVisit;
	}
	public void setPageviewsPerVisit(LiferayGoogleAnalyticsValuesDTO pageviewsPerVisit) {
		this.pageviewsPerVisit = pageviewsPerVisit;
	}
	public LiferayGoogleAnalyticsValuesDTO getVisitBounceRate() {
		return visitBounceRate;
	}
	public void setVisitBounceRate(LiferayGoogleAnalyticsValuesDTO visitBounceRate) {
		this.visitBounceRate = visitBounceRate;
	}
	public LiferayGoogleAnalyticsValuesDTO getAvgTimeOnSite() {
		return avgTimeOnSite;
	}
	public void setAvgTimeOnSite(LiferayGoogleAnalyticsValuesDTO avgTimeOnSite) {
		this.avgTimeOnSite = avgTimeOnSite;
	}
	public LiferayGoogleAnalyticsValuesDTO getPercentNewVisits() {
		return percentNewVisits;
	}
	public void setPercentNewVisits(LiferayGoogleAnalyticsValuesDTO percentNewVisits) {
		this.percentNewVisits = percentNewVisits;
	}
	public String getOrganicVisits() {
		return organicVisits;
	}
	
	
	public void setOrganicVisits(String organicVisits) {
		this.organicVisits = organicVisits;
	}
	public String getReferralVisits() {
		return referralVisits;
	}
	public void setReferralVisits(String referralVisits) {
		this.referralVisits = referralVisits;
	}
	public String getDirectVisits() {
		return directVisits;
	}
	public void setDirectVisits(String directVisits) {
		this.directVisits = directVisits;
	}
	
	

}
