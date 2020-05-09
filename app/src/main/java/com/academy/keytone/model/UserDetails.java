
package com.academy.keytone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class UserDetails {

    @SerializedName("user_email")
    @Expose
    private String userEmail;
    private String userId;
    private String roleIds;
    private String groupIds;
    private String departmentIds;
    private String isFirstLogin;
    private String fname;
    private String lname;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @SerializedName("email_list")
    @Expose
    private List<EmailList> emailList;
    @SerializedName("phone_list")
    @Expose
    private List<PhoneList> phoneList;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(String isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<EmailList> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<EmailList> emailList) {
        this.emailList = emailList;
    }

    public List<PhoneList> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<PhoneList> phoneList) {
        this.phoneList = phoneList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
