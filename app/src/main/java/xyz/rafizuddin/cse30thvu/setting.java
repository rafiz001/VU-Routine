package xyz.rafizuddin.cse30thvu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHandler db = new DatabaseHandler(this);
   // ProgressBar pgro=findViewById(R.id.pgr);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Spinner spin=findViewById(R.id.secspin);
        spin.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select your Section");
        categories.add("Section A");
        categories.add("Section B");
        categories.add("Section C");
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_root,categories);
        aa.setDropDownViewResource(R.layout.spinner_dropdown_layout);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
       if(position!=0) {



               db.addsec(position-1);
               this.startActivity(new Intent(this,MainActivity.class));}



    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {


    }




}