package com.dmepty.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int MAX_MESSAGE_LENGTH = 100;

    //Запись в базу
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("messages");

    EditText editTextMessage;
    Button buttonSendMessage;
    RecyclerView recyclerViewMessages;

    ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSendMessage = findViewById(R.id.message_send);
        editTextMessage = findViewById(R.id.message_input);

        recyclerViewMessages = findViewById(R.id.message_recycler);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        final DataAdapter dataAdapter = new DataAdapter(this, messages);

        recyclerViewMessages.setAdapter(dataAdapter);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = editTextMessage.getText().toString();

                if (msg.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Введите сообщение", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (msg.length() > MAX_MESSAGE_LENGTH){
                    Toast.makeText(getApplicationContext(), "Слишком длинное сообщение", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(msg);
                editTextMessage.setText("");
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg = dataSnapshot.getValue(String.class);

                messages.add(msg);
                dataAdapter.notifyDataSetChanged();

                recyclerViewMessages.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
