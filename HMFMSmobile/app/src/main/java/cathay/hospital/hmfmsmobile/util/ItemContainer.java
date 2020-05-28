package cathay.hospital.hmfmsmobile.util;

import android.util.Log;

public class ItemContainer {
    public static Items[] item;
    public static int recItemCount = 0;
    public ItemContainer(){
        Log.d("InfoGetItemCount", String.valueOf(recItemCount));
        item = new Items[recItemCount*5];
        //設定上限避免陣列出現無法容納總量的問題，實際方法再思考如何解決
        for(int i=0; i<recItemCount; i++){
            item[i] = new Items(i);
        }
    }
}
