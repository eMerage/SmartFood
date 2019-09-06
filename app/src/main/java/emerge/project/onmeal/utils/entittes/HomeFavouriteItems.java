package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeFavouriteItems implements Serializable {

    @SerializedName("favID")
    String favId;
    @SerializedName("favName")
    String favName;
    @SerializedName("favUrl")
    String favImgUrl;
    @SerializedName("favType")
    String favType;

    @SerializedName("id")
    int favTypeId;

    public HomeFavouriteItems(String favId, String favName, String favImgUrl, String favType, int favtypeid) {
        this.favId = favId;
        this.favName = favName;
        this.favImgUrl = favImgUrl;
        this.favType = favType;
        this.favTypeId = favtypeid;
    }


    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }

    public String getFavImgUrl() {
        return favImgUrl;
    }

    public void setFavImgUrl(String favImgUrl) {
        this.favImgUrl = favImgUrl;
    }

    public String getFavType() {
        return favType;
    }

    public void setFavType(String favType) {
        this.favType = favType;
    }


    public int getFavTypeId() {
        return favTypeId;
    }

    public void setFavTypeId(int favTypeId) {
        this.favTypeId = favTypeId;
    }
}
