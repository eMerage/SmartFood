package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressItems implements Serializable {


    @SerializedName("userAddressID")
    String addressId;
    @SerializedName("addressName")
    String addressName;



    @SerializedName("city")
    String addressCity;

    @SerializedName("mainRoad")
    String mainRoad;

    @SerializedName("subRoad")
    String subRoad;

    @SerializedName("addressNo")
    String addressNumber;

    @SerializedName("Floor")
    String addressFloor;

    @SerializedName("ApartmentName")
    String addressApartmentName;

    @SerializedName("landMark")
    String addressLandmark;

    @SerializedName("CompanyName")
    String addressCompanyName;

    @SerializedName("departmentName")
    String addressCompanyDepartment;

    @SerializedName("deliveryInstruction")
    String addressDeliveryInstructions;

    @SerializedName("latitude")
    Double addressLatitude;

    @SerializedName("longitude")
    Double addressLongitude;

    @SerializedName("addressRoad")
    String addressRoad;

    String address;

    boolean isCheck;


    public AddressItems() {

    }

    public AddressItems(String addressId, String addressName, String addressCity, String addressNumber, String addressRoad, boolean isCheck) {
        this.addressId = addressId;
        this.addressName = addressName;
        this.addressCity = addressCity;
        this.addressNumber = addressNumber;
        this.addressRoad = addressRoad;
        this.isCheck = isCheck;
    }



    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }


    public String getMainRoad() {
        return mainRoad;
    }

    public void setMainRoad(String mainRoad) {
        this.mainRoad = mainRoad;
    }

    public String getSubRoad() {
        return subRoad;
    }

    public void setSubRoad(String subRoad) {
        this.subRoad = subRoad;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressFloor() {
        return addressFloor;
    }

    public void setAddressFloor(String addressFloor) {
        this.addressFloor = addressFloor;
    }

    public String getAddressApartmentName() {
        return addressApartmentName;
    }

    public void setAddressApartmentName(String addressApartmentName) {
        this.addressApartmentName = addressApartmentName;
    }

    public String getAddressLandmark() {
        return addressLandmark;
    }

    public void setAddressLandmark(String addressLandmark) {
        this.addressLandmark = addressLandmark;
    }

    public String getAddressCompanyName() {
        return addressCompanyName;
    }

    public void setAddressCompanyName(String addressCompanyName) {
        this.addressCompanyName = addressCompanyName;
    }

    public String getAddressCompanyDepartment() {
        return addressCompanyDepartment;
    }

    public void setAddressCompanyDepartment(String addressCompanyDepartment) {
        this.addressCompanyDepartment = addressCompanyDepartment;
    }

    public String getAddressDeliveryInstructions() {
        return addressDeliveryInstructions;
    }

    public void setAddressDeliveryInstructions(String addressDeliveryInstructions) {
        this.addressDeliveryInstructions = addressDeliveryInstructions;
    }

    public Double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(Double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public Double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(Double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }


    public String getAddressRoad() {
        return addressRoad;
    }

    public void setAddressRoad(String addressRoad) {
        this.addressRoad = addressRoad;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
