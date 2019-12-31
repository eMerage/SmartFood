package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

public class VersionUpdate {



    @SerializedName("isUpdateAvailable")
    Boolean isUpdateAvailable;

    @SerializedName("isUpdateCompulsory")
    Boolean isUpdateCompulsory ;


    @SerializedName("updateMessage")
    String updateMessage;


    public Boolean getUpdateAvailable() {
        return isUpdateAvailable;
    }

    public void setUpdateAvailable(Boolean updateAvailable) {
        isUpdateAvailable = updateAvailable;
    }

    public Boolean getUpdateCompulsory() {
        return isUpdateCompulsory;
    }

    public void setUpdateCompulsory(Boolean updateCompulsory) {
        isUpdateCompulsory = updateCompulsory;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }
}
