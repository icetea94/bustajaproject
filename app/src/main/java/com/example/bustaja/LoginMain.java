package com.example.bustaja;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginMain extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SignInButton Google_Login;
    private static final int RC_SIGN_IN = 100;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    TextView new_addmem_btn, textviewFindPassword,loginBtn;
    TextView tv_id_input, tv_pw_input;
    EditText et_id_input, et_pw_input;
    Button Google_Logout;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Google_Logout = findViewById(R.id.Google_Logout);
        new_addmem_btn = findViewById(R.id.new_addmem_btn);
        textviewFindPassword = findViewById(R.id.textViewFindpassword);
        loginBtn=findViewById(R.id.loginBtn);
        tv_id_input = findViewById(R.id.tv_id_input);
        tv_pw_input = findViewById(R.id.tv_pw_input);
        et_id_input = findViewById(R.id.et_id_input);
        et_pw_input = findViewById(R.id.et_pw_input);
        progressDialog = new ProgressDialog(this);
        navigationView = findViewById(R.id.nav);

        setTitle("로그인");

        if(firebaseAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //추가해 줄 ProfileActivity
        }



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
                            progressDialog.dismiss();
                        }
                    }
                });
    }


    public void updateUI(@Nullable FirebaseUser user) {
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
                            progressDialog.dismiss();
                            finish();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String googlemail = user.getEmail();
                            SharedPreferences googlepref ;
                            googlepref = getSharedPreferences("GoogleMailList",0);

                            SharedPreferences.Editor editor = googlepref.edit();
                            editor.putString("email",googlemail);
                            editor.commit();
                            String aftergooglemail =  googlepref.getString("email",googlemail);
                            if(!aftergooglemail.contains(googlemail)) {

                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference rootRef = firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함

                                GoogleMemberVO googlemember = new GoogleMemberVO(googlemail);
                                //'persons'노드를 새로 생성
                                DatabaseReference personRef = rootRef.child("members");
                                personRef.push().setValue(googlemember);

                                //'persons'라는 노드에 리스너 붙이기
                                personRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //persons노드는 여러개의 자식노드가 있으므로
                                        StringBuffer buffer = new StringBuffer();
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            GoogleMemberVO googlemember = snapshot.getValue(GoogleMemberVO.class);
                                            String googlemail = googlemember.getGooglemail();

                                            buffer.append(googlemail + "\n");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Toast.makeText(LoginMain.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(LoginMain.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                                finish();
                            }
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
    public void clickFindPassword(View view) {
        if(view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, FindActivity.class));
        }
    }
}


