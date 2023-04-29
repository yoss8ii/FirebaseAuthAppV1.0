package com.example.mybase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private CircleImageView userImage;
    private TextView userName, userEmail;
    private Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        mAuth = FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.logoutbtn);
        currentUser = mAuth.getCurrentUser();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        getProfileInfo();
    }

    private void getProfileInfo() {
        userName.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());

        if (currentUser.getPhotoUrl() != null){
            Glide.with(this).load(currentUser.getPhotoUrl()).into(userImage);
        }
        else {
            Glide.with(this).load(R.drawable.avatarr).into(userImage);
        }
    }

    private void signout() {
        FirebaseAuth.getInstance().signOut();
        showMessage("Vous étes déconnecté");
        Intent intent
                = new Intent(getApplicationContext(),
                Login.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
}