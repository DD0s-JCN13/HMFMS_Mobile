package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import cathay.hospital.hmfmsmobile.activity.R;
import cathay.hospital.hmfmsmobile.util.UtilTools;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
    private TextView tvResult;
    private Button btnScan;
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        FindView();
        ActionBarSet();
        setChecked();
        NavigationDrawerSet();
        BottomNavigationSet();
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

    protected void FindView(){
        drawerLayout = findViewById(R.id.scanner_drawer);
        toolbar = findViewById(R.id.toolbar_scanner);
        navigationView = findViewById(R.id.nav_view_scanner);
        bottomNavigationView = findViewById(R.id.bottom_nav_scanner);
        btnScan = findViewById(R.id.btn_scan);
        tvResult = findViewById(R.id.tv_result);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,R.string.no_val,Toast.LENGTH_LONG).show();
            }else {
                tvResult.setText(result.getContents().toString());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
