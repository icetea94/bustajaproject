package com.example.bustaja;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginMain extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton Google_Login;
    private static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    TextView new_addmem_btn, loginBtn;
    TextView tv_id_input, tv_pw_input;
    EditText et_id_input, et_pw_input;
    Button Google_Logout;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Google_Logout = findViewById(R.id.Google_Logout);
        new_addmem_btn = findViewById(R.id.new_addmem_btn);
        loginBtn = findViewById(R.id.loginBtn);
        tv_id_input = findViewById(R.id.tv_id_input);
        tv_pw_input = findViewById(R.id.tv_pw_input);
        et_id_input = findViewById(R.id.et_id_input);
        et_pw_input = findViewById(R.id.et_pw_input);
        progressDialog = new ProgressDialog(this);

        setTitle("로그인");

//        if(firebaseAuth.getCurrentUser() != null){
//            //이미 로그인 되었다면 이 액티비티를 종료함
//            Toast.makeText(this, "이미 로그인 중입니다", Toast.LENGTH_SHORT).show();
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }







        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
        Google_Login = findViewById(R.id.Google_Login);
        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        // 로그아웃 버튼 클릭 이벤트 > dialog 예/아니오
        Google_Logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                Log.v("알림", "구글 LOGOUT");
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
                alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 네 클릭
                                // 로그아웃 함수 call
                                progressDialog.setMessage("로그아웃 중입니다. \n잠시 기다려 주세요...");
                                progressDialog.show();
                                signOut();
                                Toast.makeText(LoginMain.this, "구글 로그아웃 성공", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 아니오 클릭. dialog 닫기.
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // 대화창 제목 설정
                alert.setTitle("로그아웃");
                alert.show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
            }
            Log.d("RESULT", requestCode + "");
            Log.d("RESULT", resultCode + "");
            Log.d("RESULT", data + "");

            if(requestCode == 1000 && resultCode == RESULT_OK) {
                Toast.makeText(LoginMain.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
                et_id_input.setText(data.getStringExtra("email"));
            }

        }
    }
    public void userLogin(){
        String email = et_id_input.getText().toString().trim();
        String password = et_pw_input.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "이메일아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LoginMain.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginMain.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void updateUI(@Nullable FirebaseUser user) {
        // No-op
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
                        progressDialog.show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginMain.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginMain.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            finish();
                        }

                    }
                });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("알림", "연결 실패");
    }


    public void signOut() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                FirebaseAuth.getInstance().signOut();
                mAuth.signOut();
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.v("알림", "구글 로그아웃 성공");
                                setResult(1);
                            } else {
                                setResult(0);
                            }
                            Intent intent = new Intent(getApplicationContext(), LoginMain.class);
                            startActivity(intent);
                            finish();
                        }

                    });
                }

            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.v("알림", "Google API Client Connection Suspended");
                setResult(-1);
                finish();
            }
        });
    }


    public void newaddmemBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), AddmemMain.class);
        startActivityForResult(intent, 1000);


    }

    public void clickLogin(View view) {
        if(view == loginBtn) {
            userLogin();
        }

    }
}


