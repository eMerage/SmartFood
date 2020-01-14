package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateToken implements Serializable {

    @SerializedName("status")
    boolean status;

    @SerializedName("currentVersion")
    String currentVersion;

    @SerializedName("appUrl")
    String appUrl;


    @SerializedName("error")
    ErrorObject error = new ErrorObject();


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ErrorObject getError() {
        return error;
    }

    public void setError(ErrorObject error) {
        this.error = error;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
