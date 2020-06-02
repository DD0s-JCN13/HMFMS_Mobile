package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cathay.hospital.hmfmsmobile.util.CollectItems;
import cathay.hospital.hmfmsmobile.util.ItemAdaptor;
import cathay.hospital.hmfmsmobile.util.UtilTools;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class ScannerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Button btnScanLoc, btnScanItem;
    private RecyclerView recList;
    private CollectItems collectItems = new CollectItems();
    private ItemAdaptor itemAdaptor;
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    private String device_dynamic = "";
    public static List<String> itemID, itemType, itemState = new ArrayList<>();
    public static ScannerActivity scannerActivity;
    public static String device,scanResult="0";
    public static TextView locResult, locFloor;
    String btnClicked = ""; //作為判斷使用者點擊的按鍵是掃描地點還是掃描財產編號

    public ScannerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerActivity = this;
        FindView();
        ActionBarSet();
        NavigationDrawerSet();
        BottomNavigationSet();
        ScanFuncSet();
    }

    protected void FindView(){
        drawerLayout = findViewById(R.id.scanner_drawer);
        toolbar = findViewById(R.id.toolbar_scanner);
        navigationView = findViewById(R.id.nav_view_scanner);
        bottomNavigationView = findViewById(R.id.bottom_nav_scanner);
        btnScanLoc = findViewById(R.id.btn_scan_Loc);
        btnScanItem = findViewById(R.id.btn_scan_item);
        locResult = findViewById(R.id.loc_name);
        locFloor = findViewById(R.id.loc_floor);
        recList = findViewById(R.id.recView_item);
    }

    protected void ActionBarSet(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected void NavigationDrawerSet(){
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);

            int id = item.getItemId();
            switch (id){
                case R.id.nav_home:
                    UtilTools.goActivity(this,HomepageActivity.class);
                    if(sysCondition){fadeSwitchAnimation();}
                    return true;
                case R.id.nav_checklist:
                    UtilTools.goActivity(this,CheckListActivity.class);
                    if(sysCondition){fadeSwitchAnimation();}
                    return true;
                case R.id.nav_err_loc:
                    Toast.makeText(ScannerActivity.this, "Error Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_del_loc:
                    Toast.makeText(ScannerActivity.this, "Delete Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }

    protected void BottomNavigationSet(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            switch (id){
                case R.id.action_homeapge:
                    UtilTools.goActivity(this, HomepageActivity.class);
                    if(sysCondition) {fadeSwitchAnimation();}
                    return true;
                case R.id.action_scanner:
                    Toast.makeText(ScannerActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.list_item:
                    UtilTools.goActivity(this, CheckListActivity.class);
                    if(sysCondition) {fadeSwitchAnimation();}
                    return true;
            }
            return false;
        });
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    protected void fadeSwitchAnimation(){
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void ScanFuncSet(){
        //雖然兩者設置大致相同，但在點擊之後設下參數作為更新RecyclerView內容的依據
        btnScanLoc.setOnClickListener(view -> {
            btnClicked = "ScanLoc";
            new IntentIntegrator(ScannerActivity.this)
                    .setCaptureActivity(ScanningActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)//掃條碼的類型
                    .setPrompt("請對準條碼")//設置提醒標語
                    .setCameraId(0)//選擇相機鏡頭，前置或是後置鏡頭
                    .setBeepEnabled(false)//是否開啟聲音
                    .setBarcodeImageEnabled(true)//掃描後會產生圖片
                    .initiateScan();
        });
        btnScanItem.setOnClickListener(view -> {
            btnClicked = "ScanItem";
            new IntentIntegrator(ScannerActivity.this)
                    .setCaptureActivity(ScanningActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)//掃條碼的類型
                    .setPrompt("請對準條碼")//設置提醒標語
                    .setCameraId(0)//選擇相機鏡頭，前置或是後置鏡頭
                    .setBeepEnabled(false)//是否開啟聲音
                    .setBarcodeImageEnabled(true)//掃描後會產生圖片
                    .initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            scanResult = result.getContents();
            if(scanResult==null){
                Toast.makeText(this,R.string.no_val,Toast.LENGTH_LONG).show();
            }else {
                if(btnClicked.equals("ScanLoc")){
                    getItemType(scanResult);
                    Log.d("TST_get_type", device_dynamic);
                    collectItems.pushList(scanResult);
                    Log.d("TST_get_size", String.valueOf(itemID.size()));
                    Log.d("TST_get1st_item", itemID.get(0) +" "
                            + itemType.get(0) +" " +itemState.get(0));
                    btnScanItem.setVisibility(View.VISIBLE);
                    recList.setVisibility(View.VISIBLE);
                }else if(btnClicked.equals("ScanItem")){
                    getItemType(scanResult);
                    Log.d("TST_get_Type", device_dynamic);
                    collectItems.updateList(scanResult);
                    Log.d("TST_get_last_item", itemID.get(itemID.size()-1)+ " "
                    +itemType.get(itemType.size()-1) + " " + itemState.get(itemState.size()-1));
                }
                recList.setLayoutManager(new LinearLayoutManager(this));
                recList.setAdapter(itemAdaptor =
                        new ItemAdaptor(this, itemID, itemType, itemState));
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getItemType(String qrInput){
        Log.d("TST_logic_click", String.valueOf(btnClicked.equals("ScanLoc")));
        Log.d("TST_logic_click2", String.valueOf(btnClicked.equals("ScanItem")));
        if(btnClicked.equals("ScanLoc")){
            Log.d("TST_logic_location", String.valueOf(qrInput.equals("test0001")));
            Log.d("TST_logic_location2", String.valueOf(qrInput.equals("test0002")));
            if(qrInput.equals("test0001")){
                device_dynamic = getResources().getString(R.string.tstDev_name);
            }else if(qrInput.equals("test0002")){
                device_dynamic = getResources().getString(R.string.tstDev_name_2);
            }
        }else if(btnClicked.equals("ScanItem")){
            String decider = qrInput.substring(0,4);
            Log.d("TST_get_decider", decider);
            Log.d("TST_logic_items", String.valueOf(decider.equals("DEVI")));
            Log.d("TST_logic_items2", String.valueOf(decider.equals("F007")));
            if(decider.equals("DEVI")){
                device_dynamic = getResources().getString(R.string.tstDev_name);
            }else if(decider.equals("F007")){
                device_dynamic = getResources().getString(R.string.tstDev_name_2);
            }
        }
        Log.d("TST_getItemType", device_dynamic);
        device = device_dynamic;
    }

    public static String sendItemType(){
        return device;
    }


}
