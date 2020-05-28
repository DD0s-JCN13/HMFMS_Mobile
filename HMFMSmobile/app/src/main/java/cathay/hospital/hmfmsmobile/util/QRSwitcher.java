package cathay.hospital.hmfmsmobile.util;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import cathay.hospital.hmfmsmobile.activity.R;
import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class QRSwitcher {

    public static String condition = ScannerActivity.ItemCondition;
    public static int baseList = 0;
    public static ItemContainer lister;

    public static void OnLocationScan(String result, TextView location, TextView floor){
       /**  此方法僅供ScannerActivity 使用，屬性說明如下：
         *  result          ->從QR code掃描的結果內容
         *  baseList        ->作為生成在該地點的設備應有數量(也就是上次清點時的確定總數)
         *  location, floor ->設定QR code對應的地點資訊(包含樓層與地點名稱)
         */
        switch(result){
            case "test0001":
                baseList = 3;
                ScannerActivity.itemCounter = baseList;
                ItemContainer.recItemCount = baseList;
                location.setText(R.string.testloc_1);
                floor.setText(R.string.testfloor_1);
                lister = new ItemContainer();
                ScannerActivity.CollectItems = new ArrayList();
                for(int i=0; i<baseList; i++){
                    ScannerActivity.CollectItems.add(lister.item[i].getTest());
                    Log.d("InfoArrayItem", String.valueOf(ScannerActivity.CollectItems.get(i)));
                }
                break;
            case "test0002":
                baseList = 1;
                ScannerActivity.itemCounter = baseList;
                ItemContainer.recItemCount = baseList;
                location.setText(R.string.testloc_2);
                floor.setText(R.string.testfloor_1);
                lister = new ItemContainer();
                ScannerActivity.CollectItems = new ArrayList();
                ScannerActivity.CollectItems.add(lister.item[0].getAnotherTest());
                Log.d("InfoArrayItem", String.valueOf(ScannerActivity.CollectItems.get(0)));
                break;
            default:
                break;
        }
    }

    public static void OnItemScan(String result){
        ArrayList itemCollector = (ArrayList) ScannerActivity.CollectItems.clone();
        for(int i=0; i<baseList; i++){
            String inputItemID = String.valueOf(itemCollector.get(i));
            if(itemCollector.get(i).toString().equals(result)){

                break;
            }else if(!inputItemID.equals(result) && i==baseList-1){
                ScannerActivity.CollectItems.add(ItemContainer.item[0].getQRContent());
                ScannerActivity.itemCounter = ScannerActivity.itemCounter +1;
                Log.d("InfoAddOutListItem", String.valueOf(ScannerActivity.CollectItems.get(baseList)));
                ItemContainer.recItemCount = ItemContainer.recItemCount +1;
            }
        }
    }

    public static void setLocCond(String result){
        //用於設定Location Scanner 掃描的結果
        switch (result){
            case "test0001":
                ScannerActivity.ItemCondition = "TEST1";
                break;
            case "test0002":
                ScannerActivity.ItemCondition = "TEST2";
                break;
            default:
                ScannerActivity.ItemCondition = "0";
                break;
        }
    }

}
