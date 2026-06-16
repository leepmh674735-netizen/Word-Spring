package com.springinpractice.ch08.domain;

import java.io.Serializable;
import java.util.Date;

public class UserMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String text;
    private String referer;
    private String ipAddress;
    private String acceptLanguage;
    private String userAgent;
    private Date dateCreated = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "UserMessage [name=" + name + ", email=" + email + ", text=" + text + ", referer=" + referer
                + ", ipAddress=" + ipAddress + ", acceptLanguage=" + acceptLanguage + ", userAgent=" + userAgent
                + ", dateCreated=" + dateCreated + "]";
    }
}
