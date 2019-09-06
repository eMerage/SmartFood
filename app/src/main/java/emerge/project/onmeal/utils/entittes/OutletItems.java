package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class OutletItems implements Serializable {

    @SerializedName("id")
    String outletId;

    @SerializedName("name")
    String outletName;

    @SerializedName("imageUrl")
    String outletImageUrl;


    @SerializedName("ownerName")
    String outletOwnersName;

    @SerializedName("latitude")
    Double outletLatitude;

    @SerializedName("longitude")
    Double outletLongitude;


    @SerializedName("city")
    String outletCity;

    @SerializedName("outletTypeID")
    int outletType;


    @SerializedName("images")
    ArrayList<OutletImages> OutletImages = new ArrayList<OutletImages>();


    @SerializedName("phone01")
    String phoneNUmber;

    @SerializedName("eMail")
    String eMail;



    public OutletItems(String outletId, String outletName, String outletImageUrl, int outlettype,String outletcity,String ownerName,
                       Double latitude,Double longitude,ArrayList<OutletImages> images,String pnumber,String email) {
        this.outletId = outletId;
        this.outletName = outletName;
        this.outletImageUrl = outletImageUrl;
        this.outletType = outlettype;
        this.outletCity = outletcity;
        this.outletOwnersName = ownerName;
        this.outletLatitude = latitude;
        this.outletLongitude = longitude;
        this.OutletImages = images;

        this.phoneNUmber = pnumber;
        this.eMail = email;
    }

 /*   public OutletItems(JSONObject orderOutlet) throws JSONException {


        this.outletId = orderOutlet.getString("id");
        this.outletName = orderOutlet.getString("name");

    }*/

/*
    public OutletItems(String outletId, String outletName, String outletImageUrl, int outlettype,String outletcity,String ownerName,Double latitude,Double longitude,ArrayList<OutletImages> images ) {
        this.outletId = outletId;
        this.outletName = outletName;
        this.outletImageUrl = outletImageUrl;
        this.outletType = outlettype;
        this.outletCity = outletcity;

        this.outletOwnersName = ownerName;
        this.outletLatitude = latitude;
        this.outletLongitude = longitude;

        this.OutletImages = images;
    }
*/

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletImageUrl() {
        return outletImageUrl;
    }

    public void setOutletImageUrl(String outletImageUrl) {
        this.outletImageUrl = outletImageUrl;
    }


    public int getOutletType() {
        return outletType;
    }

    public void setOutletType(int outletType) {
        this.outletType = outletType;
    }

    public String getOutletCity() {
        return outletCity;
    }

    public void setOutletCity(String outletCity) {
        this.outletCity = outletCity;
    }


    public String getOutletOwnersName() {
        return outletOwnersName;
    }

    public void setOutletOwnersName(String outletOwnersName) {
        this.outletOwnersName = outletOwnersName;
    }

    public Double getOutletLatitude() {
        return outletLatitude;
    }

    public void setOutletLatitude(Double outletLatitude) {
        this.outletLatitude = outletLatitude;
    }

    public Double getOutletLongitude() {
        return outletLongitude;
    }

    public void setOutletLongitude(Double outletLongitude) {
        this.outletLongitude = outletLongitude;
    }

    public ArrayList<OutletImages> getOutletImages() {
        return OutletImages;
    }

    public void setOutletImages(ArrayList<OutletImages> outletImages) {
        OutletImages = outletImages;
    }


    public String getPhoneNUmber() {
        return phoneNUmber;
    }

    public void setPhoneNUmber(String phoneNUmber) {
        this.phoneNUmber = phoneNUmber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
