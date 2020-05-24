package cathay.hospital.hmfmsmobile.util;

public class ItemContainer {
    public static Items[] item;
    public ItemContainer(){
        item = new Items[12];
        for(int i=0; i<12; i++){
            item[i] = new Items(i);
        }
    }
}
