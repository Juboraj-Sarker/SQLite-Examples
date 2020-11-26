package com.juborajsarker.sqlite.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juborajsarker.sqlite.R;
import com.juborajsarker.sqlite.database.DatabaseHelper;
import com.juborajsarker.sqlite.model.User;


public class RegisterFragment extends Fragment {
    View view;

    EditText nameET, emailET, phoneET, passwordET;
    Button registerBTN;
    DatabaseHelper db;


    public RegisterFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        db = new DatabaseHelper(getContext());
        init();

        return view;
    }

    private void init() {
        nameET = (EditText) view.findViewById(R.id.name_ET);
        emailET = (EditText) view.findViewById(R.id.email_ET);
        phoneET = (EditText) view.findViewById(R.id.phone_ET);
        passwordET = (EditText) view.findViewById(R.id.password_ET);
        registerBTN = (Button) view.findViewById(R.id.register_BTN);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = 0;
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String phone = phoneET.getText().toString();
                User user = new User(id, name, email, password, phone);

                long message = db.insertUser(user);

                if (message > 0){
                    Toast.makeText(getContext(), "Registration Success.", Toast.LENGTH_SHORT).show();
                    nameET.setText("");
                    emailET.setText("");
                    phoneET.setText("");
                    passwordET.setText("");

                }else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again later" , Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}