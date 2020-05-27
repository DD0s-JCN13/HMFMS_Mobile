package cathay.hospital.hmfmsmobile.util;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import cathay.hospital.hmfmsmobile.activity.R;
import cathay.hospital.hmfmsmobile.activity.ScannerActivity;

public class QRSwitcher {

    public static String condition = ScannerActivity.ItemCondition;

    public static void OnLocationScan(String result, int baseList, ItemContainer lister, ArrayList itemCollector, TextView location, TextView floor){
       /**  此方法僅供ScannerActivity 使用，屬性說明如下：
         *  result          ->從QR code掃描的結果內容
         *  baseList        ->作為生成在該地點的設備應有數量(也就是上次清點時的確定總數)
         *  lister          ->存放設備資訊(地點、財產編號)的基本格式
         *  itemCollection  ->存放該地點所有應有設備的"財產編號"
         *  location, floor ->設定QR code對應的地點資訊(包含樓層與地點名稱)
         */
        switch(result){
            case "test0001":
                baseList = 3;
                ItemContainer.recItemCount = 3;
                location.setText(R.string.testloc_1);
                floor.setText(R.string.testfloor_1);
                lister = new ItemContainer();
                itemCollector = new ArrayList();
                for(int i=0; i<baseList; i++){
                    itemCollector.add(lister.item[i].getTest());
                    Log.d("InfoArrayItem", String.valueOf(itemCollector.get(i)));
                }
                break;
            case "test0002":
                baseList = 1;
                ItemContainer.recItemCount = 1;
                location.setText(R.string.testloc_2);
                floor.setText(R.string.testfloor_1);
                lister = new ItemContainer();
                itemCollector = new ArrayList();
                itemCollector.add(lister.item[0].getAnotherTest());
                Log.d("InfoArrayItem", String.valueOf(itemCollector.get(0)));
                break;

        }
    }

    public static void OnItemScan(String result, int baseList, ArrayList itemCollector){
        for(int i=0; i<baseList; i++){
            if(itemCollector.get(i).toString().equals(result)){

                break;
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
