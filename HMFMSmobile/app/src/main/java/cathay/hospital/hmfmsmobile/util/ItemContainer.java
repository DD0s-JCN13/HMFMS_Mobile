package cathay.hospital.hmfmsmobile.util;

import android.nfc.Tag;
import android.util.Log;

import java.util.List;

import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class ItemContainer {
    public static Items[] item;

    /*public static void setItemLength(String status){
        String itemCondition = ScannerActivity.SendItemCount();
        Log.d("itemConditionText", itemCondition);
        if(itemCondition.equals("TEST")){
            item = new Items[3];
            Log.d("itemLengthPrint", String.valueOf(item.length));
        }else if(itemCondition.equals("0")){
            item = new Items[1];
        }
    }*/

    public ItemContainer() {
        int req = 0;
        String itemCondition = ScannerActivity.SendItemCount();
        if(itemCondition.equals("TEST"))    {req=3;}
        ItemArrayGenerate(req);
    }

    public void ItemArrayGenerate(int request){
        item = new Items[request];
        for(int i=0;i<request; i++){
            item[i] = new Items(i);
            Log.d("index inside"+ i, String.valueOf(item[i]));
        }
    }
}
