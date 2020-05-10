package com.SevenDigITs.Solutions.CompanyChat.Registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.SevenDigITs.Solutions.CompanyChat.MainActivity;
import com.SevenDigITs.Solutions.CompanyChat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mail,pass;
    private Button sign_in,create_account,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mail = (EditText) findViewById(R.id.field_email);
        pass = (EditText) findViewById(R.id.field_password);
        sign_in = (Button) findViewById(R.id.email_sign_in_button);
        create_account = (Button) findViewById(R.id.email_create_account_button);

        // Get firbase instance
        mAuth = FirebaseAuth.getInstance();

        // On Click preform
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), CreateAcount.class);
                startActivity(intent);
            }
        });
        // signin click
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

    }

    //sign in method
    public void signin () {
        String email = mail.getText().toString();
        String password = pass.getText().toString();

        if (email.length() > 0 && password.length() > 0)
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignIn.this, "Wrong Email or Password",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Email and Password", Toast.LENGTH_SHORT).show();
        }

    }
    // Save account and go to main activity
    public void updateUI () {
        Intent n = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(n);
        finish();
    }

    //check if user is already sign in
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }
    }
}
