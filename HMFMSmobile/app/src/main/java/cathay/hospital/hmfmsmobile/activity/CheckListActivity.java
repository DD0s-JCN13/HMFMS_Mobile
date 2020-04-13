package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import cathay.hospital.hmfmsmobile.util.UtilTools;

import com.google.android.material.navigation.NavigationView;

import cathay.hospital.hmfmsmobile.R;

public class CheckListActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        findView();
        ActionBarSet();
        Navigationdrawerset();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    protected void findView() {
        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.checklist_drawer);
        navigationView = findViewById(R.id.nav_view_home);
    }

    protected void ActionBarSet(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected void Navigationdrawerset(){
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer((GravityCompat.START));

            int id = item.getItemId();

            switch (id){
                case R.id.nav_home:
                    UtilTools.goActivity(this,HomepageActivity.class);
                    return true;
                case R.id.nav_checklist:
                    Toast.makeText(CheckListActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_errorlocation:
                    Toast.makeText(CheckListActivity.this, "Error Location direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_locationmap:
                    Toast.makeText(CheckListActivity.this, "Location Map direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });
    }
}
