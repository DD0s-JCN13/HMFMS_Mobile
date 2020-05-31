package cathay.hospital.hmfmsmobile.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cathay.hospital.hmfmsmobile.activity.R;
import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class CollectItems {
    //用以產生存放物件產編、型號、掃描結果的類別，僅用於ScannerActivity
    private List<String> itemPNumLister, itemTpLister, itemSttLister;
    public static String deviceName = "";


    public void pushList(String locScan){
        itemPNumLister = new ArrayList<>();
        itemTpLister = new ArrayList<>();
        itemSttLister = new ArrayList<>();
        deviceName = ScannerActivity.sendItemType();
        switch(locScan){
            case "test0001":
                ScannerActivity.locResult.setText(R.string.testloc_1);
                ScannerActivity.locFloor.setText(R.string.testfloor_1);
                for(int i=0; i<3; i++){
                    Log.d("TST_get_Type_pushList", ScannerActivity.sendItemType());
                    itemTpLister.add(String.valueOf(ScannerActivity.sendItemType()));
                    itemPNumLister.add("DEVICE-TEST-0000" + i);
                    itemSttLister.add("wait");
                }
                break;
            case "test0002":
                ScannerActivity.locResult.setText(R.string.testloc_2);
                ScannerActivity.locFloor.setText(R.string.testfloor_1);
                itemTpLister.add(deviceName);
                itemPNumLister.add("F007-TEST-00001");
                itemSttLister.add("wait");
                break;
            default:
                break;
        }
        ScannerActivity.itemID = new ArrayList<>(itemPNumLister);
        ScannerActivity.itemType = new ArrayList<>(itemTpLister);
        ScannerActivity.itemState = new ArrayList<>(itemSttLister);
    }

    public void updateList(String itemNum){
        for(int i=0; i<itemPNumLister.size(); i++){
            if(itemNum.equals(itemPNumLister.get(i))){
                itemSttLister.set(i, "pass");
                break;
            }else if(!itemNum.equals(itemPNumLister.get(i)) && i==itemPNumLister.size()-1){
                itemPNumLister.add(itemNum);
                itemSttLister.add("alert");
                itemTpLister.add(deviceName);
            }
        }
    }
}
