package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimeSlots implements Serializable {

    @PrimaryKey
    int rowId;

    @SerializedName("timeSlotID")
    int timeSlotId;

    @SerializedName("timeSlot")
    String deliveryTimeSlot;


    @SerializedName("timeFrom")
    String deliveryTimeFrom;


    @SerializedName("timeTo")
    String deliveryTimeTo;

    boolean isSelected;

    public TimeSlots() { }


    public TimeSlots(int timeslotId, String deliveryTimeSlot, String deliveryTimeFrom, String deliveryTimeTo, boolean isselected) {
        this.timeSlotId = timeslotId;
        this.deliveryTimeSlot = deliveryTimeSlot;
        this.deliveryTimeFrom = deliveryTimeFrom;
        this.deliveryTimeTo = deliveryTimeTo;
        this.isSelected = isselected;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int id) {
        this.timeSlotId = id;
    }

    public String getDeliveryTimeSlot() {
        return deliveryTimeSlot;
    }

    public void setDeliveryTimeSlot(String deliveryTimeSlot) {
        this.deliveryTimeSlot = deliveryTimeSlot;
    }

    public String getDeliveryTimeFrom() {
        return deliveryTimeFrom;
    }

    public void setDeliveryTimeFrom(String deliveryTimeFrom) {
        this.deliveryTimeFrom = deliveryTimeFrom;
    }

    public String getDeliveryTimeTo() {
        return deliveryTimeTo;
    }

    public void setDeliveryTimeTo(String deliveryTimeTo) {
        this.deliveryTimeTo = deliveryTimeTo;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
}
