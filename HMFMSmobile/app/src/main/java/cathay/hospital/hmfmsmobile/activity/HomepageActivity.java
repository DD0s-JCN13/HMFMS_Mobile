package cathay.hospital.hmfmsmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.drm.DrmStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import cathay.hospital.hmfmsmobile.R;

public class HomepageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        toolbar = findViewById(R.id.toolbar_home);
        drawerLayout = findViewById(R.id.home_drawer);
        navigationView = findViewById(R.id.nav_view_home);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);

                int id = item.getItemId();
                if (id == R.id.nav_home){
                    Toast.makeText(HomepageActivity.this, "Already on this page!!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }else if (id == R.id.nav_checklist){
                    Toast.makeText(HomepageActivity.this, "Check List direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }else if (id == R.id.nav_errorlocation){
                    Toast.makeText(HomepageActivity.this, "Error Location direction",
                            Toast.LENGTH_LONG).show();
                    return true;
                }else if (id == R.id.nav_locationmap){
                    Toast.makeText(HomepageActivity.this, "Location Map direction",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        navigationView.getMenu().getItem(0).setChecked(true);
    }

}
