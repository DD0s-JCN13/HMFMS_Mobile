package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import cathay.hospital.hmfmsmobile.util.UtilTools;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import cathay.hospital.hmfmsmobile.R;

public class HomepageActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_homepage);
        FindView();
        setChecked();
        ActionBarSet();
        NavigationDrawerSet();
        BottomNavigationSet();
    }

    protected void FindView(){
        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.home_drawer);
        navigationView = findViewById(R.id.nav_view_home);
        bottomNavigationView = findViewById(R.id.bottom_nav_home);
    }

    protected void setChecked(){
        navigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
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
                    Toast.makeText(HomepageActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_checklist:
                    UtilTools.goActivity(this, CheckListActivity.class);
                    if(sysCondition){   fadeSwitchAnimation();  }
                    return true;
                case R.id.nav_err_loc:
                    Toast.makeText(HomepageActivity.this, "Error Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_del_loc:
                    Toast.makeText(HomepageActivity.this, "Delete Location direction",
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
                    Toast.makeText(HomepageActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_scanner:
                    Toast.makeText(HomepageActivity.this, "QR Code Scanner direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.list_item:
                    UtilTools.goActivity(this, CheckListActivity.class);
                    if(sysCondition){   fadeSwitchAnimation();  }
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
