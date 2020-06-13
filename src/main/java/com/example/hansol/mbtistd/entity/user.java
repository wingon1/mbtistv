package com.example.hansol.mbtistd.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class user {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idx;
    
    private String userConnectIp;

    private String userNum;

    private String userMbti;

    private Integer userType;

    private String userNm;

    private String lastConnectDateTime;

    private String firstConnectDateTime;

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getUserConnectIp() {
		return userConnectIp;
	}

	public void setUserConnectIp(String userConnectIp) {
		this.userConnectIp = userConnectIp;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserMbti() {
		return userMbti;
	}

	public void setUserMbti(String userMbti) {
		this.userMbti = userMbti;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getLastConnectDateTime() {
		return lastConnectDateTime;
	}

	public void setLastConnectDateTime(String lastConnectDateTime) {
		this.lastConnectDateTime = lastConnectDateTime;
	}

	public String getFirstConnectDateTime() {
		return firstConnectDateTime;
	}

	public void setFirstConnectDateTime(String firstConnectDateTime) {
		this.firstConnectDateTime = firstConnectDateTime;
	}

    


}