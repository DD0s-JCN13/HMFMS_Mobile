package cathay.hospital.hmfmsmobile.util;

import android.util.Log;

import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class Items {

    int number;
    String itemCondition;

    public Items(int number){
        this.number = number;
    }

    public  String tget(){
        return "DEVICE-TEST-0000";
    }
    public String getItemDataByCondition(String itemcondition){
        this.itemCondition= itemcondition;
        //itemCondition = ScannerActivity.SendItemCount();
        Log.d("itemsgetcondi", itemCondition);
        if(itemCondition.equals("TEST")){
            return "DEVICE-TEST-0000"+number;
        }else if(itemCondition.equals("0")){
            return "EMPTY";
        }else{
            return "EMPTY";
        }
    }
}
