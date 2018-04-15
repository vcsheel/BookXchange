package com.example.vivek.bookxchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etusername;
    private EditText etpassword;
    private Button RegisterButton;
    private ImageView backButton;
    private ProgressDialog progressDialog;
    LinearLayout bgLayout;

    private FirebaseAuth firebaseauth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseauth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        RegisterButton = (Button) findViewById(R.id.RegisterButton);
        backButton = (ImageView) findViewById(R.id.backButton);
        bgLayout = (LinearLayout) findViewById(R.id.linearbgLayout);

        bgLayout.setOnClickListener(this);
        RegisterButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == RegisterButton){
            RegisterUser();
        }
        if (v == backButton){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        if(v == bgLayout){
            closeKeyBoard();
        }
    }

    private void closeKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    private void RegisterUser() {
        final String email = etusername.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            saveUserDetails(email);
                            finish();
                            Intent intent = new Intent(getApplicationContext(),BrowseBookActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void saveUserDetails(String email){
        Users user = new Users();
        user.setEmail(email);

        FirebaseUser currUser = firebaseauth.getCurrentUser();
        Log.i("curruser",currUser.getUid());
        mDatabaseRef.child("Users").child(currUser.getUid()).setValue(user);
        Log.i("UserInfo","Pushed to firebase");
    }

}

