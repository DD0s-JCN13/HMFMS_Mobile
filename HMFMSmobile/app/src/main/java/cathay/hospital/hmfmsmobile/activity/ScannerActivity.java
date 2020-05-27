package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cathay.hospital.hmfmsmobile.util.ItemContainer;
import cathay.hospital.hmfmsmobile.util.QRSwitcher;
import cathay.hospital.hmfmsmobile.util.UtilTools;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ScannerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private Button btnScanLoc, btnScanItem;
    private TextView locResult, locFloor;
    private RecyclerView recList;
    private int itemCounter = 0;
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    public static ScannerActivity scannerActivity;
    public static String ItemCondition="0";
    String btnClicked = ""; //作為判斷使用者點擊的按鍵是掃描地點還是掃描財產編號
    ArrayList CollectItems; //暫存現在所在位置的裝置財產編號，新的地點資料匯入就會覆蓋
    ItemContainer itemC;    //存放從資料庫撈出指定地點的所有設備產編與設備型號

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
            if(result.getContents()==null){
                Toast.makeText(this,R.string.no_val,Toast.LENGTH_LONG).show();
            }else if(btnClicked.equals("ScanLoc")){
                //透過Utiltools/QRSwitcher的方法來決定掃描QR code後的資料處理方式
                QRSwitcher.OnLocationScan(result.getContents(),
                        itemCounter,itemC, CollectItems,locResult,locFloor);
                QRSwitcher.setLocCond(result.getContents());
                Log.d("InfoLocationItemCOUND", ItemCondition);
                onCreateRecycleView();
                recList.setVisibility(View.VISIBLE);
                btnScanItem.setVisibility(View.VISIBLE);
            }else if(btnClicked.equals("ScanItem")){
                //OnItemScan(result.getContents(),);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onCreateRecycleView(){
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new LinearLayoutManager(this));
        recList.setAdapter(new ItemAdapter());
    }
    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemStructure>{
        @NonNull
        @Override
        public ItemStructure onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemStructure(getLayoutInflater().inflate(R.layout.item_role, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemStructure structure, int position) {
            switch (ItemCondition){
                case "0":
                    structure.itemID.setText(itemC.item[0].NoItems());
                    structure.itemType.setText("");
                    break;
                case "TEST1":
                    Log.d("InfoOnPosition", String.valueOf(position));
                    Log.d("InfoGetPositionOnList", itemC.item[position].getTest());
                    structure.itemID.setText(itemC.item[position].getTest());
                    structure.itemType.setText(R.string.tstDev_name);
                    break;
                case "TEST2":
                    structure.itemID.setText(itemC.item[0].getAnotherTest());
                    structure.itemType.setText(R.string.tstDev_name_2);
                    break;
                default:
                    structure.itemID.setText(itemC.item[position].getList());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            switch (ItemCondition){
                case "TEST1":
                    return 3;
                case "TEST2":
                    return 1;
                case "0":
                default:
                    //default function will not be the same as case 0
                    return 0;
            }
        }

        class ItemStructure extends RecyclerView.ViewHolder{
            TextView itemID, itemType;
            ImageView itemEmpty, itemChecked, itemWarn;
            public ItemStructure(View itemView){
                super(itemView);
                itemType = itemView.findViewById(R.id.item_type);
                itemID = itemView.findViewById(R.id.item_propNum);
                itemEmpty = itemView.findViewById(R.id.scan_item_empty);
                itemChecked = itemView.findViewById(R.id.scan_item_checked);
                itemWarn = itemView.findViewById(R.id.scan_item_warn);
            }
        }

        public void SetItemOnChecked(){

        }

    }

}
