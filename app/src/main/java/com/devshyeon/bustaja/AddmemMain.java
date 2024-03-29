package com.devshyeon.bustaja;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class AddmemMain extends AppCompatActivity {
    TextView tv_addmem_id,tv_id_rule,tv_addmem_pw,tv_pw_rule,tv_addmem_pw_confirm,tv_pw_confirm_rule;
    TextView reque;
    EditText et_addmem_id_input,et_addmem_pw_input,et_addmem_pw_confirm;
    Button addmemBtn;
    FirebaseAuth firebaseAuth;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,10}$");

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

        et_addmem_id_input=findViewById(R.id.et_addmem_id_input);
        et_addmem_pw_input=findViewById(R.id.et_addmem_pw_input);
        et_addmem_pw_confirm=findViewById(R.id.et_addmem_pw_confirm);

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
                        if (!task.isSuccessful()) {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                               Toast.makeText(AddmemMain.this, "이미 메일을 가진 사용자가 존재합니다", Toast.LENGTH_SHORT).show();
                                  et_addmem_id_input.setBackgroundColor(Color.RED);
                                  et_addmem_id_input.requestFocus();
                                  return;
                            }
                        }
                            else{
                                Toast.makeText(AddmemMain.this, "회원가입 성공~", Toast.LENGTH_SHORT).show();
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




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickAddmem(View view) {


//
//        if(et_addmem_id_input.getText().toString().length() == 0) {
//            Toast.makeText(AddmemMain.this,"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
//            et_addmem_id_input.requestFocus();
//            return;
//        }
//        if(et_addmem_pw_input.getText().toString().length() == 0) {
//            Toast.makeText(AddmemMain.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
//            et_addmem_pw_input.requestFocus();
//            return;
//        }
//        if(et_addmem_pw_confirm.getText().toString().length() == 0) {
//            Toast.makeText(AddmemMain.this, "비밀번호 확인란를 입력하세요", Toast.LENGTH_SHORT).show();
//            et_addmem_pw_confirm.requestFocus();
//            return;
//        }
//        if(!et_addmem_pw_input.getText().toString().equals(et_addmem_pw_confirm.getText().toString())){
//            tv_pw_confirm_rule.setText("* 비밀번호가 일치하지 않습니다");
//            Toast.makeText(AddmemMain.this, "동일하게 입력해 주세요", Toast.LENGTH_SHORT).show();
//            et_addmem_pw_confirm.requestFocus();
//            return;
//        }
//
//        if(!isValidEmail()) {
//            Toast.makeText(AddmemMain.this, "아이디 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
//            et_addmem_id_input.requestFocus();
//            return;
//        }
//        if(!isValidPasswd()) {
//            Toast.makeText(AddmemMain.this, "비밀번호 형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show();
//            et_addmem_pw_input.requestFocus();
//            return;
//        }
//        else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("재확인");
//            LayoutInflater inflater = getLayoutInflater();
//            View v = inflater.inflate(R.layout.message_board_dialog, null);
//            reque = v.findViewById(R.id.reque);
//            builder.setView(v);
//            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                    String email = et_addmem_id_input.getText().toString();
//                    String password = et_addmem_pw_input.getText().toString();
//
//
//
//                    createUser(email, password);
//
//                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//                    DatabaseReference rootRef=firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
//
//                    MemberVO member= new MemberVO(email,password);
//                    member.setEmail(email);
//                    String memberemail=member.getEmail();
//                    //'persons'노드를 새로 생성
//                    if(email.contains(member.email)){
//                        Toast.makeText(AddmemMain.this, "이미 메일을 가진 사용자가 존재합니다", Toast.LENGTH_SHORT).show();
//                        et_addmem_id_input.setBackgroundColor(Color.RED);
//                        et_addmem_id_input.requestFocus();
//                        return;
//                    }else{
//                        et_addmem_id_input.setBackgroundColor(0xff0099FF);
//                        Intent result = new Intent();
//                        // 자신을 호출한 Activity로 데이터를 보낸다.
//                        setResult(RESULT_OK, result);
//
//                    }
//                    DatabaseReference personRef= rootRef.child("members");
//                    personRef.push().setValue(member);
//
//                    Toast.makeText(AddmemMain.this, "회원가입 성공!!", Toast.LENGTH_LONG).show();
//                    //'persons'라는 노드에 리스너 붙이기
//                    personRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            //persons노드는 여러 자식노드가 있으므로
//                            StringBuffer buffer= new StringBuffer();
//                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//
//                                MemberVO person= snapshot.getValue(MemberVO.class);
//                                String email= person.getEmail();
//                                String password= person.getPassword();
//
//                                buffer.append(email+" , "+ password+"\n");
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                    finish();
//                }
//            }).create();
//
//            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                }
//            }).create();
//            AlertDialog dialog = builder.create();
//            //다이얼로그의 바깥쪽을 터치하였을 때 다이얼로그가 꺼지지 않도록..
//            dialog.setCanceledOnTouchOutside(false);
//            //뒤로가기 버튼을 클릭해도 꺼지지 않도록 하려면..
//            dialog.setCancelable(false);
//            //다이얼로그를 화면에 보이기!!
//            dialog.show();
//        }

    }
}
