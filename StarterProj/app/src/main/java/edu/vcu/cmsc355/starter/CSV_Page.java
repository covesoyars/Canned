package edu.vcu.cmsc355.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;

public class CSV_Page extends AppCompatActivity {

    private EditText fileName;
    private Button addFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv__page);

        fileName = (EditText)findViewById(R.id.editText17);
        addFile = (Button)findViewById(R.id.button12);

        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = fileName.getText().toString();
                try{
                    InputStream stream = getAssets().open(file);
                    //Not finished
                }
                catch(Exception e){

                }
            }
        });
    }

    public void addCSV(View view){

    }
}
