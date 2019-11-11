package com.example.bustaja;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class AddmemMain extends AppCompatActivity {
    TextView tv_addmem_id,tv_id_rule,tv_addmem_pw,tv_pw_rule,tv_addmem_pw_confirm,tv_pw_confirm_rule,tv_addmem_nickname,tv_nickname_rule;
    TextView reque;
    EditText et_addmem_id_input,et_addmem_pw_input,et_addmem_pw_confirm,et_addmem_nickname_input;
    Button addmemBtn;
    FirebaseAuth firebaseAuth;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,10}$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{2,8}$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmem_main);
        setTitle("회원가입");
        firebaseAuth = FirebaseAuth.getInstance();
        tv_addmem_id=findViewById(R.id.tv_addmem_id);
        tv_id_rule=findViewById(R.id.tv_id_rule);
        tv_addmem_pw=findViewById(R.id.tv_addmem_pw);
        tv_pw_rule=findViewById(R.id.tv_pw_rule);
        tv_addmem_pw_confirm=findViewById(R.id.tv_addmem_pw_confirm);
        tv_pw_confirm_rule=findViewById(R.id.tv_pw_confirm_rule);
        tv_addmem_nickname=findViewById(R.id.tv_addmem_nickname);
        tv_nickname_rule=findViewById(R.id.tv_nickname_rule);
        et_addmem_id_input=findViewById(R.id.et_addmem_id_input);
        et_addmem_pw_input=findViewById(R.id.et_addmem_pw_input);
        et_addmem_pw_confirm=findViewById(R.id.et_addmem_pw_confirm);
        et_addmem_nickname_input=findViewById(R.id.et_addmem_nickname_input);
        addmemBtn=findViewById(R.id.addmemBtn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_addmem_pw_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password=et_addmem_pw_input.getText().toString();
                String confirm=et_addmem_pw_confirm.getText().toString();


                if(password.equals(confirm)) {
                    et_addmem_pw_input.setBackgroundColor(0xff0099FF);
                    et_addmem_pw_confirm.setBackgroundColor(0xff0099FF);
                }else{
                    et_addmem_pw_input.setBackgroundColor(Color.RED);
                    et_addmem_pw_confirm.setBackgroundColor(Color.RED);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });




    }

    // 회원가입
    public void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                              } else {
                            // 회원가입 실패
                            Toast.makeText(AddmemMain.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        String email = et_addmem_id_input.getText().toString();
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }
    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        String password = et_addmem_pw_input.getText().toString();
        if (password.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }
    // 닉네임 유효성 검사
    private boolean isValidNickname() {
        String nickname = et_addmem_nickname_input.getText().toString();
        if (nickname.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }


    public void clickAddmem(View view) {


        if(et_addmem_id_input.getText().toString().length() == 0) {
            Toast.makeText(AddmemMain.this,"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
            et_addmem_id_input.requestFocus();
            return;
        }
        if(et_addmem_pw_input.getText().toString().length() == 0) {
            Toast.makeText(AddmemMain.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            et_addmem_pw_input.requestFocus();
            return;
        }
        if(et_addmem_pw_confirm.getText().toString().length() == 0) {
            Toast.makeText(AddmemMain.this, "비밀번호 확인란를 입력하세요", Toast.LENGTH_SHORT).show();
            et_addmem_pw_confirm.requestFocus();
            return;
        }
        if(et_addmem_nickname_input.getText().toString().length() == 0) {
            Toast.makeText(AddmemMain.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            et_addmem_nickname_input.requestFocus();
            return;
        }
        if(!et_addmem_pw_input.getText().toString().equals(et_addmem_pw_confirm.getText().toString())){
            tv_pw_confirm_rule.setText("* 비밀번호가 일치하지 않습니다");
        }

        if(!isValidEmail()) {
            Toast.makeText(AddmemMain.this, "아이디 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
            et_addmem_id_input.requestFocus();
            return;
        }
        if(!isValidPasswd()) {
            Toast.makeText(AddmemMain.this, "비밀번호 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
            et_addmem_pw_input.requestFocus();
            return;
        }
        if(!isValidNickname()) {
            Toast.makeText(AddmemMain.this, "닉네임 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
            et_addmem_nickname_input.requestFocus();
            return;
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("재확인");
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.message_board_dialog, null);
            reque = v.findViewById(R.id.reque);
            builder.setView(v);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String email = et_addmem_id_input.getText().toString();
                    String password = et_addmem_pw_input.getText().toString();

                    Toast.makeText(AddmemMain.this, "회원가입 성공!!", Toast.LENGTH_LONG).show();
                    Intent result = new Intent();
                    result.putExtra("email", et_addmem_id_input.getText().toString());

                    // 자신을 호출한 Activity로 데이터를 보낸다.
                    setResult(RESULT_OK, result);

                    createUser(email, password);

                    finish();
                }
            }).create();

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).create();
            AlertDialog dialog = builder.create();
            //다이얼로그의 바깥쪽을 터치하였을 때 다이얼로그가 꺼지지 않도록..
            dialog.setCanceledOnTouchOutside(false);
            //뒤로가기 버튼을 클릭해도 꺼지지 않도록 하려면..
            dialog.setCancelable(false);
            //다이얼로그를 화면에 보이기!!
            dialog.show();
        }
    }
}
