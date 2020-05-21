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
import cathay.hospital.hmfmsmobile.util.ItemContainer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TextView locResult;
    private TextView locFloor;
    private Button btnScan;
    private Button btnConfirm;
    private RecyclerView recList;
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    public static ScannerActivity scannerActivity;
    public static String ItemCondition="0";

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
        LocScanFuncSet();
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new LinearLayoutManager(this));
        recList.setAdapter(new ItemAdapter());
    }

    protected void FindView(){
        drawerLayout = findViewById(R.id.scanner_drawer);
        toolbar = findViewById(R.id.toolbar_scanner);
        navigationView = findViewById(R.id.nav_view_scanner);
        bottomNavigationView = findViewById(R.id.bottom_nav_scanner);
        btnScan = findViewById(R.id.btn_scan);
        btnConfirm = findViewById(R.id.btn_Confirm);
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

    protected void LocScanFuncSet(){
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(ScannerActivity.this)
                        .setCaptureActivity(ScanningActivity.class)
                        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)//掃條碼的類型
                        .setPrompt("請對準條碼")//設置提醒標語
                        .setCameraId(0)//選擇相機鏡頭，前置或是後置鏡頭
                        .setBeepEnabled(false)//是否開啟聲音
                        .setBarcodeImageEnabled(true)//掃描後會產生圖片
                        .initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,R.string.no_val,Toast.LENGTH_LONG).show();
            }else {
                String inputResult = result.getContents().toString();
                switch (inputResult){
                    case "test0001":
                        locResult.setText(R.string.testloc_1);
                        locFloor.setText(R.string.testfloor_1);
                        btnScan.setVisibility(View.GONE);
                        btnConfirm.setVisibility(View.VISIBLE);
                        ItemCondition = "TEST";
                        ItemContainer.setItemLength(ItemCondition);
                        SendItemCount();
                    default:
                        locResult.setText(result.getContents().toString());
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemStructure>{
        @NonNull
        @Override
        public ItemStructure onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemStructure(getLayoutInflater().inflate(R.layout.item_role, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemStructure structure, int position) {
            if(ItemCondition.equals("0")){
                structure.itemID.setText(ItemContainer.item[0].getItemDataByCondition(ItemCondition));
                structure.itemType.setText("");
            }else if(ItemCondition.equals("TEST")){
                Log.d("ritemc", ItemCondition);
                //structure.itemID.setText(ItemContainer.item[position].tget().toString());
                structure.itemID.setText(ItemContainer.item[position].getItemDataByCondition(ItemCondition));
                structure.itemType.setText(R.string.tstDev_name);
            }else {

            }
        }

        @Override
        public int getItemCount() {
            if(ItemCondition.equals("0")){
                return 0;
            }else{
                return ItemContainer.item.length;
            }
        }

        class ItemStructure extends RecyclerView.ViewHolder{
            TextView itemID, itemType;
            public ItemStructure(View itemView){
                super(itemView);
                itemType = findViewById(R.id.item_type);
                itemID = findViewById(R.id.item_propNum);
            }
        }
    }

    public static String SendItemCount(){
        return ItemCondition;
    }

}
