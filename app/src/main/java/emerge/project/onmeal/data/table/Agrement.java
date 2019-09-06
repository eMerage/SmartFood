package emerge.project.onmeal.data.table;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Agrement extends RealmObject {

    @PrimaryKey
    int rowId;

    boolean isAgrementApproved;


    public Agrement() {
    }

    public Agrement(int rowId, boolean isAgrementApproved) {
        this.rowId = rowId;
        this.isAgrementApproved = isAgrementApproved;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public boolean isAgrementApproved() {
        return isAgrementApproved;
    }

    public void setAgrementApproved(boolean agrementApproved) {
        isAgrementApproved = agrementApproved;
    }
}
