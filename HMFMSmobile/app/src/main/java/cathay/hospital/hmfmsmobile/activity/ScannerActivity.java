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

public class ScannerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView locResult, locFloor;
    private Button btnScanLoc, btnScanItem;
    private RecyclerView recList;
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    public static ScannerActivity scannerActivity;
    private int itemCounter = 0;
    String ItemCondition="0";
    String btnClicked = "";
    String inputResult = "";
    String[] CollectItems;
    ItemContainer itemC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerActivity = this;
        FindView();
        ActionBarSet();
        setChecked();
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

    protected void setChecked(){
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
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

    protected void VisibleSet(){
        btnScanItem.setVisibility(View.VISIBLE);
        recList.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,R.string.no_val,Toast.LENGTH_LONG).show();
            }else {
                Log.d("InfoScan", String.valueOf(result));
                Log.d("InfoScan", result.getContents());
                inputResult = result.getContents();
                if(btnClicked.equals("ScanLoc")){
                    VisibleSet();
                    switch (inputResult){
                        case "test0001":
                            ItemContainer.recItemCount = 3;
                            itemC = new ItemContainer();
                            CollectItems = new String[3];
                            for(int i=0; i<ItemContainer.recItemCount; i++){
                                CollectItems[i] = itemC.item[i].getTest();
                                Log.d("InfoCollectItem", CollectItems[i]);
                            }
                            locResult.setText(R.string.testloc_1);
                            locFloor.setText(R.string.testfloor_1);
                            ItemCondition = "TEST1";
                            onCreateRecycleView();
                            break;
                        case "test0002":
                            ItemContainer.recItemCount = 1;
                            itemC = new ItemContainer();
                            CollectItems = new String[1];
                            CollectItems[0] = itemC.item[0].getAnotherTest();
                            Log.d("InfoCollectItem", CollectItems[0]);
                            locResult.setText(R.string.testloc_2);
                            locFloor.setText(R.string.testfloor_1);
                            ItemCondition = "TEST2";
                            onCreateRecycleView();
                            break;
                        default:
                            locResult.setText(result.getContents());
                            onCreateRecycleView();
                            break;
                    }
                }else if(btnClicked.equals("ScanItem")){
                    for(int i=0;i<CollectItems.length;i++){
                        if(inputResult.equals(CollectItems[i])){
                            itemCounter = i;
                            break;
                        }
                        if(i == CollectItems.length-1 && !inputResult.equals(CollectItems[i])){

                        }
                    }
                }
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
                    Log.d("InfoListLength", String.valueOf(itemC.item.length));
                    Log.d("InfoGet1stOnList", itemC.item[0].getTest());
                    structure.itemID.setText(itemC.item[position].getTest());
                    structure.itemType.setText(R.string.tstDev_name);
                    break;
                case "TEST2":
                    structure.itemID.setText(itemC.item[position].getAnotherTest());
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

    }

    protected void setVOnList(){

    }

}
