package com.example.bustaja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AccessMain extends AppCompatActivity {

    Button AccessBtn ,AccessBtn2,AccessBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_main);
        AccessBtn = findViewById(R.id.AccessBtn);
        AccessBtn2 =findViewById(R.id.AccessBtn2);
        AccessBtn3 =findViewById(R.id.AccessBtn3);
        setTitle("접근 권한");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "권한에 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
                    AccessBtn.setEnabled(true);
                    AccessBtn.setText("거부됨");
                    AccessBtn.setBackgroundColor(0xffFF0000);
                } else {
                    AccessBtn.setText("허용됨");
                    AccessBtn.setBackgroundColor(0xff0099FF);
                    AccessBtn.setEnabled(true);
                    Toast.makeText(this, "권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();

                }
                break;


                case 20:
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "권한에 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
                        AccessBtn2.setEnabled(true);
                        AccessBtn2.setText("거부됨");
                        AccessBtn2.setBackgroundColor(0xffFF0000);
                    } else {
                        AccessBtn2.setText("허용됨");
                        AccessBtn2.setBackgroundColor(0xff0099FF);
                        AccessBtn2.setEnabled(true);
                        Toast.makeText(this, "권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();

                    }
                    break;

            case 30:

                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "권한에 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
                    AccessBtn3.setEnabled(true);
                    AccessBtn3.setText("거부됨");
                    AccessBtn3.setBackgroundColor(0xffFF0000);
                } else {
                    AccessBtn3.setText("허용됨");
                    AccessBtn3.setBackgroundColor(0xff0099FF);
                    AccessBtn3.setEnabled(true);
                    Toast.makeText(this, "권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }



    public void clickAccess2(View view) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkedPermission= checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);//READ는 WRITE를 주면 같이 권한이 주어짐

            if(checkedPermission==PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 30);

            }

            else {
                Toast.makeText(this, "이미 모든 권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                AccessBtn2.setText("허용됨");
                AccessBtn2.setBackgroundColor(0xff0099FF);
                AccessBtn2.setEnabled(true);
            }
        }
    }

    public void clickAccess(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkedPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐

            if (checkedPermission == PackageManager.PERMISSION_DENIED) {//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

            } else {
                Toast.makeText(this, "이미 권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                AccessBtn.setText("허용됨");
                AccessBtn.setBackgroundColor(0xff0099FF);
                AccessBtn.setEnabled(true);
            }
        }
    }

    public void clickAccess3(View view) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkedPermission= checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);//READ는 WRITE를 주면 같이 권한이 주어짐

            if(checkedPermission==PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 20);

            }

            else {
                Toast.makeText(this, "이미 권한에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                AccessBtn3.setText("허용됨");
                AccessBtn3.setBackgroundColor(0xff0099FF);
                AccessBtn3.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
