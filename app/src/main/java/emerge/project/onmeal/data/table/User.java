package emerge.project.onmeal.data.table;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Serializable {

    @PrimaryKey
    int rowId;

    @SerializedName("id")
    String userId;

    @SerializedName("userName")
    String userName;

    @SerializedName("name")
    String uName;

    @SerializedName("eMail")
    String userEmail;

    @SerializedName("mobileNo")
    String userPhoneNumber;

    @SerializedName("Password")
    String userPassword;

    @SerializedName("gender")
    String userGender;

    @SerializedName("SocialMediaTokenId")
    String userSocialMediaTokenId;

    @SerializedName("PushTokenId")
    String userPushTokenId;

    @SerializedName("userLoginTypeCode")
    String socialMediaType;

    @SerializedName("userTypeCode")
    String userTypeID;

    @SerializedName("smsCode")
    String smsCode;

    @SerializedName("isVerified")
    boolean IsVerified;


    @SerializedName("dateOfBirth")
    String dateOfBirth;





    public User() {}


    public User(String userName, String uName, String userEmail, String userPhoneNumber, String userPassword, String userGender, String userSocialMediaTokenId, String userPushTokenId, String socialMediaType, String userTypeID) {
        this.userName = userName;
        this.uName = uName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userSocialMediaTokenId = userSocialMediaTokenId;
        this.userPushTokenId = userPushTokenId;
        this.socialMediaType = socialMediaType;
        this.userTypeID = userTypeID;
    }

    public User(int rowId, String userId, String userName, String userEmail, String userPhoneNumber, String userPassword, String userSocialMediaTokenId, String userPushTokenId, String socialMediaType) {
        this.rowId = rowId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassword = userPassword;
        this.userSocialMediaTokenId = userSocialMediaTokenId;
        this.userPushTokenId = userPushTokenId;
        this.socialMediaType = socialMediaType;
    }


    protected User(Parcel in) {
        rowId = in.readInt();
        userId = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPhoneNumber = in.readString();
        userPassword = in.readString();
        userSocialMediaTokenId = in.readString();
        userPushTokenId = in.readString();
        socialMediaType = in.readString();
    }


    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSocialMediaTokenId() {
        return userSocialMediaTokenId;
    }

    public void setUserSocialMediaTokenId(String userSocialMediaTokenId) {
        this.userSocialMediaTokenId = userSocialMediaTokenId;
    }

    public String getUserPushTokenId() {
        return userPushTokenId;
    }

    public void setUserPushTokenId(String userPushTokenId) {
        this.userPushTokenId = userPushTokenId;
    }

    public String getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(String socialMediaType) {
        this.socialMediaType = socialMediaType;


    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isVerified() {
        return IsVerified;
    }

    public void setVerified(boolean verified) {
        IsVerified = verified;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
