package cathay.hospital.example.model.bean;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("logCostNo")
    public String costNo;

    @SerializedName("logStatus")
    public String status;

    @SerializedName("logUserCname")
    public String name;

    public String getCostNo() {
        return costNo;
    }

    public void setCostNo(String costNo) {
        this.costNo = costNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
