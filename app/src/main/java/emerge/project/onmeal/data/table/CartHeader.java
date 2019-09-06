package emerge.project.onmeal.data.table;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Himanshu on 9/8/2017.
 */

public class CartHeader extends RealmObject {

    @PrimaryKey
    Long cartHeaderId;
    int outletMenuTitleID;
    int outletID;
    String outlet;
    String name;
    String imageUrl;
    String size;
    int quantity;
    Double priceTotal;
    boolean isActive;


    public CartHeader() {
    }

    public CartHeader(Long cartHeaderId, int outletMenuTitleID, int outletID, String outlet, String name, String imageUrl, String size, int quantity, Double priceTotal,boolean isactive) {
        this.cartHeaderId = cartHeaderId;
        this.outletMenuTitleID = outletMenuTitleID;
        this.outletID = outletID;
        this.outlet = outlet;
        this.name = name;
        this.imageUrl = imageUrl;
        this.size = size;
        this.quantity = quantity;
        this.priceTotal = priceTotal;
        this.isActive = isactive;
    }


    public Long getCartHeaderId() {
        return cartHeaderId;
    }

    public void setCartHeaderId(Long cartHeaderId) {
        this.cartHeaderId = cartHeaderId;
    }

    public int getOutletMenuTitleID() {
        return outletMenuTitleID;
    }

    public void setOutletMenuTitleID(int outletMenuTitleID) {
        this.outletMenuTitleID = outletMenuTitleID;
    }

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
