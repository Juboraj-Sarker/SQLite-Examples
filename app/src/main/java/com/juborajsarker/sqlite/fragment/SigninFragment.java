package com.juborajsarker.sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juborajsarker.sqlite.MainActivity;
import com.juborajsarker.sqlite.R;
import com.juborajsarker.sqlite.database.DatabaseHelper;
import com.juborajsarker.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;


public class SigninFragment extends Fragment {
    View view;
    EditText emailET, passwordET;
    Button signinBTN;
    DatabaseHelper db;
    List<User> users = new ArrayList<User>();

    public SigninFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signin, container, false);

        db = new DatabaseHelper(getContext());
        init();

        return view;
    }

    private void init() {

        emailET = (EditText) view.findViewById(R.id.email_ET);
        passwordET = (EditText) view.findViewById(R.id.password_ET);
        signinBTN = (Button) view.findViewById(R.id.signin_BTN);

        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

               try {
                   users = db.getUserByEmailAndPassword(email, password);
                   if (users.size() > 0){
                       Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(getContext(), MainActivity.class);
                       getActivity().startActivity(intent);

                   }else {

                       Toast.makeText(getContext(), "Wrong email or password. Please try with valid credential", Toast.LENGTH_SHORT).show();
                   }
               }catch (Exception e){
                   Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
}