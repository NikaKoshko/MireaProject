package ru.mirea.lukaninava.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class ConvertFragment extends Fragment {

    private EditText editTextFilename, editTextContent;
    private Button buttonSave, buttonConvert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convert, container, false);

        editTextFilename = view.findViewById(R.id.editTextFilename);
        editTextContent = view.findViewById(R.id.editTextContent);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonConvert = view.findViewById(R.id.buttonConvert);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile(editTextFilename.getText().toString(), editTextContent.getText().toString());
            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertToJson(editTextFilename.getText().toString(), editTextContent.getText().toString());
            }
        });

        return view;
    }

    private void saveFile(String filename, String content) {
        try (FileOutputStream fos = getActivity().openFileOutput(filename, getActivity().MODE_PRIVATE)) {
            fos.write(content.getBytes());
            Toast.makeText(getActivity(), "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to save file", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertToJson(String filename, String content) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("filename", filename);
            jsonObject.put("content", content);

            String jsonString = jsonObject.toString();
            saveFile(filename + ".json", jsonString);
            Toast.makeText(getActivity(), "File converted to JSON", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to convert to JSON", Toast.LENGTH_SHORT).show();
        }
    }
}