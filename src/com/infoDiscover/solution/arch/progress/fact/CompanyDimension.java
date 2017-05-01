package com.infoDiscover.solution.arch.progress.fact;

/**
 * Created by sun.
 */
public class CompanyDimension {
    private String providerID;
    private String providerName;
    private String providerClassification;
    private String address;
    private String contact;
    private String contactMobile;

    public CompanyDimension(String providerID, String providerName, String providerClassification, String
            address, String contact, String contactMobile) {
        this.providerID = providerID;
        this.providerName = providerName;
        this.providerClassification = providerClassification;
        this.address = address;
        this.contact = contact;
        this.contactMobile = contactMobile;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderClassification() {
        return providerClassification;
    }

    public void setProviderClassification(String providerClassification) {
        this.providerClassification = providerClassification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }
}
