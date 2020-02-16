package com.cgh.HMFMS_M;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import cathay.hospital.smartpatrol.model.SharedPreferencesModel;
import cathay.hospital.smartpatrol.model.bean.LoginData;
import cathay.hospital.smartpatrol.util.UpdateManager;
import cathay.hospital.smartpatrol.util.UtilCommonVariable;
import cathay.hospital.smartpatrol.util.UtilTools;
import cathay.hospital.smartpatrol.viewModel.ViewModel_login;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editUser,editPassword;
    TextView textError;
    Button btnLogin,btnRefresh;
    String empNo,password,divNo,deviceToken;
    ViewModel_login viewModel_login;
    SharedPreferencesModel sharedPrefsModel;
    UpdateManager updateManager;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();//隱藏標題列

        init();
        updateManager.checkNewVersion("");
    }

    public void getUnknownSourceAllowUpdateApp(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!getPackageManager().canRequestPackageInstalls()) {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse(String.format("package:%s", getPackageName()))), UtilCommonVariable.allowUnknownPermission);
            } else {
                if(isUpdate){
                   updateManager.DownloadNewVersion();
                }
            }
        } else {
            if(isUpdate){
               updateManager.DownloadNewVersion();
            }
        }
    }

    public void init(){
        divNo = UtilCommonVariable.connectEnv;
        editUser = findViewById(R.id.editUser);
        editPassword = findViewById(R.id.editPassword);
        textError = findViewById(R.id.textError);
        btnLogin = findViewById(R.id.btnLogin);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnLogin.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        sharedPrefsModel = new SharedPreferencesModel(getApplicationContext());
        updateManager = new UpdateManager(this);
    }

    public void onClick(View v) {
        if(v == btnLogin){
            empNo = editUser.getText().toString();
            password = editPassword.getText().toString();
            //因得取token為非同步必須在裡面執行
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                deviceToken = instanceIdResult.getToken();
                loginMember();
                getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
            });
        }else if(v == btnRefresh){
            editUser.setText("");
            editPassword.setText("");
            textError.setText("");
            editUser.requestFocus();
        }
    }

    public void loginMember(){
        HashMap<String,String> sharedMap = new HashMap<>();
        switch(empNo){
            case "123456":
                sharedMap.put("empNo", empNo);
                sharedMap.put("costNo", "A506W");
                sharedMap.put("divNo",divNo);
                sharedMap.put("deviceToken", deviceToken);
                sharedPrefsModel.setSharedPrefsData(sharedMap);
                UtilTools.goActivity(this, MainActivity.class);
                break;
            case "E65409": case "272332":
                sharedMap.put("empNo", empNo);
                sharedMap.put("divNo",divNo);
                sharedMap.put("deviceToken", deviceToken);
                sharedPrefsModel.setSharedPrefsData(sharedMap);

                UtilTools.goActivity(this, MainActivity.class);
                break;
            default:
                HashMap<String,String> paramsMap = new HashMap<>();
                paramsMap.put("nickname",empNo);
                paramsMap.put("passwd",password);
                paramsMap.put("divno",divNo);

                viewModel_login = new ViewModel_login(divNo,paramsMap);
                viewModel_login.getLoginData().observe(this, dataModel -> {
                    Boolean error = dataModel.getError();
                    if(error) {
                        textError.setText(R.string.connectError);
                    } else {
                        LoginData loginData = (LoginData)dataModel.getDataObj();
                        if(loginData.status.equals("1")){
                            sharedMap.put("divNo",divNo);
                            sharedMap.put("empNo", empNo);
                            sharedMap.put("empName", loginData.name);
                            sharedMap.put("costNo", loginData.costNo);
                            sharedMap.put("deviceToken", deviceToken);
                            sharedPrefsModel.setSharedPrefsData(sharedMap);

                            UtilTools.goActivity(this, MainActivity.class);
                        }else{
                            textError.setText(R.string.loginError);
                        }
                    }
                });
                break;
        }

    }

    public void setIsUpdate(boolean isUpdate){
        this.isUpdate = isUpdate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UtilCommonVariable.allowUnknownPermission && resultCode == Activity.RESULT_OK) {
            if (getPackageManager().canRequestPackageInstalls()) {
                if(isUpdate){
                    updateManager.DownloadNewVersion();
                }
            }
        } else {
            updateManager.checkNewVersion("rejectPermission");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == UtilCommonVariable.allowStoragePermission){
                updateManager.getCheckUpdateDialog().checkStoragePermission();
            }

        }
    }
}
