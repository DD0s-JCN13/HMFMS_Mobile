package cathay.hospital.hmfmsmobile.model.bean;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("status")
    public String status;

    @SerializedName("userName")
    public String userName;

    @SerializedName("divNo")
    public String divNo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDivNo() { return divNo;}

    public void setDivNo(String divNo) { this.divNo = divNo; }

}
