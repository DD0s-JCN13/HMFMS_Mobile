package cathay.hospital.hmfmsmobile.util;

import android.nfc.Tag;
import android.util.Log;

import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class ItemContainer {
    public static Items[] item;

    public static void setItemLength(String status){
        String itemCondition = ScannerActivity.SendItemCount();
        Log.d("itemConditionText", itemCondition);
        if(itemCondition.equals("TEST")){
            item = new Items[3];
            Log.d("itemLengthPrint", String.valueOf(item.length));
        }else if(itemCondition.equals("0")){
            item = new Items[1];
        }
    }

    public ItemContainer(){
        String itemCondition = ScannerActivity.SendItemCount();
        try{
        switch (itemCondition){
            case "TEST":
                for(int i=0;i<3;i++){
                    item[i] = new Items(i);
                }
            case "0":
                item[0] = new Items(0);
        }}catch (NullPointerException ignored){

        }
    }
}
