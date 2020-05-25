package cathay.hospital.hmfmsmobile.util;

import android.util.Log;


public class Items {

    int number;

    public Items(int number){
        this.number = number;
    }

    public String NoItems(){
        return "EMPTY";
    }

    public String getTest(){
        Log.d("InfoTestSuccess", String.valueOf(number));
        return "DEVICE-TEST-0000"+number;
    }

    public String getAnotherTest(){
        return "F007-TEST-0000"+String.valueOf(number+1);
    }

    public String getList(){
        return "SQL command here";
    }
}
