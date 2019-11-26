package com.example.bustaja;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.VISIBLE;


public class MessageReplaceFragment extends Fragment {
    TextView reque;
    EditText replace_title_tv,replace_contents_tv;
    TextView replace_date_tv,replace_nickid_tv;
    Button btn_replaceOk;

    MessageboardDetail boardDetail;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        MessageboardDetail boardDetail= (MessageboardDetail)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
//        //이제 더이상 엑티비티 참초가안됨
//        boardDetail = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //보여줄 뷰객체를 생성(부풀리다)
        View view = inflater.inflate(R.layout.fragment_message_replace, container,false);


        replace_title_tv=view.findViewById(R.id.replace_title_tv);
        replace_contents_tv=view.findViewById(R.id.replace_contents_tv);
        replace_date_tv=view.findViewById(R.id.replace_date_tv);
        replace_nickid_tv=view.findViewById(R.id.replace_nickid_tv);

        btn_replaceOk=view.findViewById(R.id.btn_replaceOk);

        MessageboardDetail boardDetail= (MessageboardDetail)getActivity();

        String boardTitle = boardDetail.detail_title_tv.getText().toString();
        final String boardContents = boardDetail.detail_contents_tv.getText().toString();
        String boardNick=boardDetail.detail_nickid_tv.getText().toString();
        String boardDate=boardDetail.detail_date_tv.getText().toString();

        replace_title_tv.setText(boardTitle);
        replace_contents_tv.setText(boardContents);
        replace_date_tv.setText(boardDate);
        replace_nickid_tv.setText(boardNick);

        btn_replaceOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메인 액티비티 텍스트뷰 글씨변경
                //프래그먼트 보여주는 액티비티를 얻어와서 참조하기
                //MainActivity.tv.setText("바꾸기"); //대신 메인에서 변수앞에 스테틱붙여야함
                //이 프레그먼트를 보여주는 Activity를
                //얻어와서 참조하기
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //건축가에게 원하는 작업요청
                builder.setTitle("재확인");
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.message_board_dialog, null);

                reque = v.findViewById(R.id.reque);
                builder.setView(v);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        MessageboardDetail boardDetail= (MessageboardDetail)getActivity();

                        String replaceTitle= replace_title_tv.getText().toString();
                        String replaceContents= replace_contents_tv.getText().toString();
                        String replaceDate = replace_date_tv.getText().toString();
                        String replaceNick = replace_nickid_tv.getText().toString();


                        boardDetail.btn_back.setVisibility(VISIBLE);
                        boardDetail.btn_replace.setVisibility(VISIBLE);

// 뒤로갈때
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().remove(MessageReplaceFragment.this).commit();
                        fragmentManager.popBackStack();

                        boardDetail.detail_title_tv.setText(replaceTitle);
                        boardDetail.detail_contents_tv.setText(replaceContents);




//                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//                DatabaseReference rootRef2=firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
//                DatabaseReference boardRef= rootRef2.child("Boards");
//                        boardRef.updateChildren()





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
        });

        //생성된 뷰를 리턴해주면 Activity에 보여짐
        return view;

    }






}
