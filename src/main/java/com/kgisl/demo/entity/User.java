


package com.kgisl.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User
 */
@Entity
@Table(name = "agent")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    
	
    private String userEmail;

    private String agentCode;

    private String agentName;
    
    public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	private String DOB;
	private int age;
	
	public String getYear() {
		return year;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setYear(String year) {
		this.year = year;
	}

	private String year;
    
    private String maritalStatus;
    
    @Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", agentCode="
				+ agentCode + ", agentName=" + agentName + ", icNo=" + icNo + ", businessRegNo=" + businessRegNo
				+ ", Address1=" + Address1 + ", Address2=" + Address2 + ", postCode=" + postCode + ", state=" + state
				+ ", mobileNo=" + mobileNo + "]";
	}

	private String icNo;

    private String businessRegNo;

    private String Address1;

    private String Address2;

    private String postCode;

    private String state;

    private String mobileNo;
    
    private String userMessage;
    
    private String gender;


 
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	/**
     * @return the userid
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param username the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

      /**
     * @return the useremail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param useremail the useremail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

     public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public String getBusinessRegNo() {
		return businessRegNo;
	}

	public void setBusinessRegNo(String businessRegNo) {
		this.businessRegNo = businessRegNo;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}