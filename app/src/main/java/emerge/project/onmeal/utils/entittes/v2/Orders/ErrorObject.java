package emerge.project.onmeal.utils.entittes.v2.Orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorObject implements Serializable {



    @SerializedName("errCode")
    String errCode;


    @SerializedName("errDesc")
    String errDescription;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrDescription() {
        return errDescription;
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }
}
