package cathay.hospital.hmfmsmobile.util;

import android.util.Log;

public class ItemContainer {
    public static Items[] item;
    public static int recItemCount = 0;
    public ItemContainer(){
        Log.d("InfoGetItemCount", String.valueOf(recItemCount));
        item = new Items[recItemCount];
        for(int i=0; i<recItemCount; i++){
            item[i] = new Items(i);
        }
    }
}
