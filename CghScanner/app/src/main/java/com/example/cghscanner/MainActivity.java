package com.example.cghscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this)
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
                Toast.makeText(this,"no result",Toast.LENGTH_LONG).show();
            }else {
                tvResult.setText(result.getContents().toString());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private TextView tvResult;
    private Button btnScan;
}
