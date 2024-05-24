package ru.mirea.lukaninava.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ProfileFragment extends Fragment {
    EditText editFullname, editAge, editDolz;
    Button button;
    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        editFullname = root.findViewById(R.id.fullname);
        editAge = root.findViewById(R.id.age);
        editDolz = root.findViewById(R.id.dolz);

        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        Context context = getActivity();
        sharedPref = context.getSharedPreferences("profile_settings", Context.MODE_PRIVATE);

        String name = sharedPref.getString("FULLNAME ", "unknown");
        String age = sharedPref.getString("AGE ", "unknown");
        String dolz = sharedPref.getString("DOLZ ", "unknown");

        if(!name.equals("unknown"))
        {
            editFullname.setText(name);
        }
        if(!age.equals("unknown"))
        {
            editAge.setText(age);
        }
        if(!dolz.equals("unknown"))
        {
            editDolz.setText(dolz);
        }

        return root;
    }

    public void saveProfile()
    {
        SharedPreferences.Editor editor	= sharedPref.edit();

        String name = editFullname.getText().toString();
        editor.putString("FULLNAME", name);

        String email = editAge.getText().toString();
        editor.putString("AGE", email);

        String sex = editDolz.getText().toString();
        editor.putString("DOLZ", sex);

        editor.apply();
    }
}