package cathay.hospital.hmfmsmobile.util;

import android.util.Log;

import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class Items {

    int number;
    String itemCondition;

    public Items(int number){
        this.number = number;
    }

    public String getList(){
        itemCondition = ScannerActivity.SendItemCount();
        Log.d("ItemGetCondition", itemCondition);
        return "EMPTY";
    }

    public String getList(String condition){
        return "DEVICE-TEST-0000"+number;
    }
}
