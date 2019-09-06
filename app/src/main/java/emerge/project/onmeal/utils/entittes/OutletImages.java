package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutletImages implements Serializable {

    @SerializedName("id")
    String outletImageId;

    @SerializedName("name")
    String outletImageName;

    @SerializedName("imageUrl")
    String imageOutletsUrl;





    public OutletImages() {
    }

    public OutletImages(String outletImageId, String outletImageName, String outletImageUrl) {
        this.outletImageId = outletImageId;
        this.outletImageName = outletImageName;
        this.imageOutletsUrl = outletImageUrl;
    }


    public String getOutletImageId() {
        return outletImageId;
    }

    public void setOutletImageId(String outletImageId) {
        this.outletImageId = outletImageId;
    }

    public String getOutletImageName() {
        return outletImageName;
    }

    public void setOutletImageName(String outletImageName) {
        this.outletImageName = outletImageName;
    }

    public String getImageOutletsUrl() {
        return imageOutletsUrl;
    }

    public void setImageOutletsUrl(String imageOutletsUrl) {
        this.imageOutletsUrl = imageOutletsUrl;
    }
}
