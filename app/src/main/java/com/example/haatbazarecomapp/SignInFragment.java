package com.example.haatbazarecomapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private  EditText password;

    private Button signInBtn;
    private  Button closeBtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount=view.findViewById(R.id.signIn_donthaveaccount);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);

        email= view.findViewById(R.id.signIn_email);
        password= view.findViewById(R.id.signIn_password);

        progressBar = view.findViewById(R.id.signIn_progressBar);
        signInBtn= view.findViewById(R.id.signIn_btn);
        closeBtn=view.findViewById(R.id.closeBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(getResources().getColor(R.color.colorLetter));
            }else{
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(getResources().getColor(R.color.color50Letter));
            }
        }else{
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(getResources().getColor(R.color.color50Letter));
        }
    }
    private void checkEmailAndPassword(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=8){
                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(getResources().getColor(R.color.color50Letter));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        . addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent mainIntent=new Intent(getActivity(),MainActivity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();

                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(getResources().getColor(R.color.colorLetter));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }else{
                Toast.makeText(getActivity(),"Account Information is not vaild",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"Account Information is not vaild",Toast.LENGTH_SHORT).show();
        }
    }

    private void mainIntent(){
        Intent mainIntent=new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}
