package com.example.bustaja;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MessageboardAdapter extends RecyclerView.Adapter implements Serializable {

    ArrayList<MessageboardItem> boardItem;
    Context context;


    public FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference boardRef3,boardRef4;
    DatabaseReference rootRef3;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView board_nickname, board_title, board_contents, board_time;


        public MyViewHolder(View view) {

            super(view);

            board_nickname = view.findViewById(R.id.board_nickname);
            board_title = view.findViewById(R.id.board_title);
            board_contents = view.findViewById(R.id.board_contents);
            board_time = view.findViewById(R.id.board_time);

            view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuItem Edit = menu.add(Menu.NONE, 1000, 1, "편집");
                    Edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int position = getAdapterPosition();
                            firebaseAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String cd = boardItem.get(position).getBoardNick();

                            switch (item.getItemId()) {
                                case 1000:
                                    if(user!=null) {
                                        String gettemail = user.getEmail();

                                        if (!gettemail.equals(cd) || user == null) {
                                            AlertDialog dialog = new AlertDialog.Builder(context).setMessage("본인만 수정할 수 있습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            }).create();

                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.show();

                                        } else if(gettemail.equals(cd) && user != null) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            View view = LayoutInflater.from(context)
                                                    .inflate(R.layout.messageboard_editbox, null, false);
                                            builder.setView(view);
                                            final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                                            final EditText editTextID = (EditText) view.findViewById(R.id.edittext_dialog_id);
                                            final EditText edittext_dialog_contents = (EditText) view.findViewById(R.id.edittext_dialog_contents);
                                            final TextView date_tv = view.findViewById(R.id.replace_date_tv);
                                            final TextView nick_tv = view.findViewById(R.id.replace_nickid_tv);
                                            editTextID.setText(boardItem.get(getAdapterPosition()).getBoardTitle());
                                            edittext_dialog_contents.setText(boardItem.get(getAdapterPosition()).getBoardContents());
                                            nick_tv.setText(boardItem.get(getAdapterPosition()).getBoardNick());
                                            date_tv.setText(boardItem.get(getAdapterPosition()).getBoardDate());


                                            final AlertDialog dialog = builder.create();
                                            ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    String strID = editTextID.getText().toString();
                                                    String strContents = edittext_dialog_contents.getText().toString();
                                                    String dates= date_tv.getText().toString();
                                                    String nick=nick_tv.getText().toString();

                                                    MessageboardItem dict2 = new MessageboardItem(strID, strContents,dates,nick);
                                                    boardItem.set(getAdapterPosition(), dict2);

                                                    notifyItemChanged(getAdapterPosition());

                                                    dialog.dismiss();

                                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                                    rootRef3 = firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
                                                    boardRef3= rootRef3.child("Boards");
                                                    boardRef4=boardRef3.child(MessageboardMain.boardKeys.get(getAdapterPosition()));

                                                    boardRef4.setValue(dict2);







                                                }
                                            });

                                            dialog.show();



                                        }
                                    }
                                    break;

                            }
                            return false;
                        }
                    });
                    MenuItem Delete = menu.add(Menu.NONE, 1001, 2, "삭제");
                    Delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            int position = getAdapterPosition();
                            firebaseAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String cd = boardItem.get(position).getBoardNick();

                            switch (item.getItemId()) {
                                case 1001:

                                    if(user!=null) {
                                        String gettemail = user.getEmail();

                                        if (!gettemail.equals(cd) || user == null) {
                                            AlertDialog dialog = new AlertDialog.Builder(context).setMessage("본인만 삭제할 수 있습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            }).create();

                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.show();
                                        } else if(gettemail.equals(cd) && user != null) {
                                            boardItem.remove(getAdapterPosition());
                                            notifyItemRemoved(getAdapterPosition());
                                            notifyItemRangeChanged(getAdapterPosition(), boardItem.size());

                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            rootRef3 = firebaseDatabase.getReference();//괄호 안이 비어있으면 최상위 노드를 뜻함
                                            boardRef3 = rootRef3.child("Boards");
                                            boardRef3.removeValue();



                                        }
                                    }



                                    break;
                            }
                            return false;
                        }
                    });

                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //클릭된 아이템뷰가 리사이클러 뷰에서 몇번째 index(position)인지 알아내기
                    int position = getAdapterPosition();
                    int hitcounts=0;

                    hitcounts++;

                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

    public MessageboardAdapter(ArrayList<MessageboardItem> boardItem, Context context) {
        this.boardItem = boardItem;
        this.context = context;

    }
    @NonNull
    @Override
    public MessageboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_board_form, parent, false);
        MessageboardAdapter.MyViewHolder holder = new MessageboardAdapter.MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageboardAdapter.MyViewHolder holder5 = (MessageboardAdapter.MyViewHolder) holder;


        holder5.board_title.setText(boardItem.get(position).getBoardTitle());
        holder5.board_nickname.setText(boardItem.get(position).getBoardNick());
        holder5.board_time.setText(boardItem.get(position).getBoardDate());
        holder5.board_contents.setText(boardItem.get(position).getBoardContents());

    }

    @Override
    public int getItemCount() {
        return boardItem.size();
    }
}