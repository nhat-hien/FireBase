package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button updBtn;
    TextView userName, userPhone;
    EditText userNameUpd, userPhoneUpd;

    FirebaseDatabase fDatabase;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updBtn = findViewById(R.id.updBtn);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userNameUpd = findViewById(R.id.userNameUpd);
        userPhoneUpd = findViewById(R.id.userPhoneUpd);

        fDatabase = FirebaseDatabase.getInstance();
        dRef = fDatabase.getReference().child("account");
        dRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userName.setText(snapshot.getValue(String.class));
                }else
                    userName.setText("Not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dRef.child("phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    userPhone.setText(snapshot.getValue(int.class).toString());
                }else userPhone.setText("Not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userNameUpd.getText().toString();
                String phoneget = userPhoneUpd.getText().toString();
                int phone = Integer.parseInt(phoneget);

                dRef.child("name").setValue(name);
                dRef.child("phone").setValue(phone);
            }
        });
    }
}