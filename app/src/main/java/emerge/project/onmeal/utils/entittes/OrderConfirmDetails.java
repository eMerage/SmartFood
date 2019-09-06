package emerge.project.onmeal.utils.entittes;

import java.io.Serializable;

public class OrderConfirmDetails implements Serializable {

    String orderID;
    String orderCode;
    String orderDate;
    int addressID;
    String promoCode;
    String promoTitle;
    int outletID;
    String outlet;
    String outletCity;
    Double deliveryCost;
    Double orderTotal;
    String paymentTypeCode;
    String dispatchType;
    String pickUpTime;
    String deliveryTimeSlot;


    public OrderConfirmDetails(String orderId, String orderCode, String orderDate, int addressID, String promoCode,String promotitle, int outletID,String outlet,String outCity, Double deliveryCost, Double orderTotal, String paymentTypeCode, String dispatchType, String pickUpTime, String deliveryTimeSlot) {

        this.orderID = orderId;
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.addressID = addressID;
        this.promoCode = promoCode;
        this.promoTitle = promotitle;
        this.outletID = outletID;
        this.outlet = outlet;
        this.outletCity = outCity;
        this.deliveryCost = deliveryCost;
        this.orderTotal = orderTotal;
        this.paymentTypeCode = paymentTypeCode;
        this.dispatchType = dispatchType;
        this.pickUpTime = pickUpTime;
        this.deliveryTimeSlot = deliveryTimeSlot;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getDeliveryTimeSlot() {
        return deliveryTimeSlot;
    }

    public void setDeliveryTimeSlot(String deliveryTimeSlot) {
        this.deliveryTimeSlot = deliveryTimeSlot;
    }


    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getOutletCity() {
        return outletCity;
    }

    public void setOutletCity(String outletCity) {
        this.outletCity = outletCity;
    }


    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }
}
