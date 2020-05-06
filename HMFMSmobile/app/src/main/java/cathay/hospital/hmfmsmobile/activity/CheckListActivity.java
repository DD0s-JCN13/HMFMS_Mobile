package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import cathay.hospital.hmfmsmobile.util.UtilTools;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import cathay.hospital.hmfmsmobile.activity.R;

public class CheckListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    //Check system version because activity switch animation
    //needs the lowest requirement of API 21.
    private boolean sysCondition = Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        findView();
        setChecked();
        ActionBarSet();
        NavigationDrawerSet();
        BottomNavigationSet();
    }

    protected void findView() {
        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.checklist_drawer);
        navigationView = findViewById(R.id.nav_view_home);
        bottomNavigationView  = findViewById(R.id.bottom_nav_home);
    }

    protected void setChecked(){
        navigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
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
                    if(sysCondition) { fadeSwitchAnimation(); }
                    return true;
                case R.id.nav_checklist:
                    Toast.makeText(CheckListActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_err_loc:
                    Toast.makeText(CheckListActivity.this, "Error Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_del_loc:
                    Toast.makeText(CheckListActivity.this, "Delete Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }

    protected void BottomNavigationSet() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            switch (id){
                case R.id.action_homeapge:
                    UtilTools.goActivity(this, HomepageActivity.class);
                    if(sysCondition) {fadeSwitchAnimation();}
                    return true;
                case R.id.action_scanner:
                    UtilTools.goActivity(this, ScannerActivity.class);
                    if(sysCondition) {fadeSwitchAnimation();}
                    return true;
                case R.id.list_item:
                    Toast.makeText(CheckListActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }

    protected void fadeSwitchAnimation(){
        //Variable at the front is enter animation,
        //at the back is exit animation.
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
