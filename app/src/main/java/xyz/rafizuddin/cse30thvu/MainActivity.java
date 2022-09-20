package xyz.rafizuddin.cse30thvu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);

WebView webpage;




    boolean needUpdate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        AlertDialog.Builder alertD=new AlertDialog.Builder(MainActivity.this);

        alertD.setMessage("Loading...");
        alertD.setTitle("VU Routine");
        AlertDialog aD=alertD.create();
        aD.show();






        Button bt=findViewById(R.id.button);
        Button bt2=findViewById(R.id.out);

        webpage=findViewById(R.id.webpage);
webpage.getSettings().setJavaScriptEnabled(true);
webpage.clearCache(true);
webpage.setWebViewClient(new WebViewClient()
                         {


                             @Override
                             public void onPageFinished(WebView view, String url) {
                                 aD.dismiss();
                                 super.onPageFinished(view, url);
                             }

                             @Override
                             public void onPageStarted(WebView view, String url, Bitmap favicon) {

                                 super.onPageStarted(view, url, favicon);
                             }

                             @Override
                             public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                 Intent intent = new Intent(Intent.ACTION_VIEW,request.getUrl());
                                 startActivity(intent);
                                 return true;
                             }
                         }
);
        if (!db.issec("sett")){
            this.startActivity(new Intent(this,setting.class));
          finish();
        }else{
            if (!db.issec("routine")){
                updater(false);
            }else {

                webpage.loadDataWithBaseURL("blarg://ignored", db.output(db.showsec()), "text/html; charset=utf-8", "UTF-8", "");

            } }




        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               db.delete("sett");

                startActivity(new Intent(MainActivity.this,setting.class));
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        updater(true);


            }
        });

        Button teacher=findViewById(R.id.teacher);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertD=new AlertDialog.Builder(MainActivity.this);

                alertD.setMessage("Dr. Khademul Islam Molla/KIM\n" +
                        "Sabina Yasmin/SY\n" +
                        "Arifa Ferdousi/AF\n" +
                        "Dr. Ahammad Hossain/AH\n" +
                        "Mst. Rashida Akhtar/RAK\n" +
                        "Md. Mizanur Rahman/MR\n" +
                        "Jannatul Ferdous/JF\n" +
                        "Monika Kabir/MK\n" +
                        "Farah Wahida/FW\n" +
                        "Sumaia Rahman/SR\n" +
                        "Delwar Hossain/DH\n" +
                        "Tokey Ahmmed/TKA\n" +
                        "Mohammad Kasedullah/MKD\n" +
                        "Md. Toufikul Islam/MTI\n" +
                        "Md. Nour Noby/NN\n" +
                        "Rafi Ibn Sultan/RIS\n" +
                        "Ayesha Akter Lima/AAL\n" +
                        "Golam Shahriar/GS\n" +
                        "Salma Akter Lima/SAL\n" +
                        "Md. Mosiur Rahman Sweet/MRS\n" +
                        "Ipshita Tasnim Raha/ITR\n" +
                        "Sumaiya Tasnim/ST\n" +
                        "Shamim Reza/SHR\n" +
                        "Samira Tareque/SMT\n" +
                        "Al Muktadir Munam/AMM\n" +
                        "Akib Iqbal/AKI\n" +
                        "Mohammad Faisal Al-Naser/MFA\n" +
                        "Ahmed Al Azmain/AAAMd. Nour Noby/NN\n" +
                        "Rafi Ibn Sultan/RIS\n" +
                        "Ayesha Akter Lima/AAL\n" +
                        "Golam Shahriar/GS\n" +
                        "Salma Akter Lima/SAL\n" +
                        "Md. Mosiur Rahman Sweet/MRS\n" +
                        "Ipshita Tasnim Raha/ITR\n" +
                        "Sumaiya Tasnim/ST\n" +
                        "Shamim Reza/SHR\n" +
                        "Samira Tareque/SMT\n" +
                        "Al Muktadir Munam/AMM\n" +
                        "Akib Iqbal/AKI\n" +
                        "Mohammad Faisal Al-Naser/MFA\n" +
                        "Ahmed Al Azmain/AAA\n" +
                        "A.K.M Ashiqur Rahman/AAR\n" +
                        "Kazi Arunim Samya/KA\n" +
                        "Md. Abdus Sobhan/MAS\n" +
                        "Md. S. M. Ashraf Hossain/MAH").setCancelable(true);
            alertD.setIcon(getDrawable(R.drawable.cse_vu));
alertD.setTitle("VU CSE 30th");

                alertD.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogI, int i) {
                        dialogI.cancel();
                    }
                });
                AlertDialog aD=alertD.create();
                aD.show();
            }
        });

        Button room=findViewById(R.id.room);
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertD=new AlertDialog.Builder(MainActivity.this);

                alertD.setMessage("OLD = Engineering Old Building\n" +
                        "ANX = Engineering Annex Building\n" +
                        "SEL= Software Enginnering Lab (Room: 305, Engineering Annex Building)\n " +
                        "NL= Network Lab (Room: 304, Engineering Annex Building)\n" +
                        "DEL= Digital Electronics Lab (Room: 403, Engineering Annex Building)\n" +
                        "Abacus Lab= (Room: 404, Engineering Annex Building)").setCancelable(true);
                alertD.setIcon(getDrawable(R.drawable.cse_vu));
                alertD.setTitle("VU CSE 30th");

                alertD.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogI, int i) {
                        dialogI.cancel();
                    }
                });
                AlertDialog aD=alertD.create();
                aD.show();
            }
        });

        if (db.issec("routine")) {
            if (db.version() > Float.parseFloat(BuildConfig.VERSION_NAME)) {
                String url = "";
                url = Timer.getUrl("https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt");
                if (url != "") {
                    String urlA[] = url.split(",");
                    //  Toast.makeText(this, Float.parseFloat(urlA[0])+"\n"+Float.parseFloat(BuildConfig.VERSION_NAME), Toast.LENGTH_LONG).show();


                    AlertDialog.Builder alertU = new AlertDialog.Builder(MainActivity.this);

                    alertU.setMessage("New app update is available. You have to download and install it right now!").setCancelable(true);
                    alertU.setIcon(getDrawable(R.drawable.cse_vu));
                    alertU.setTitle("VU CSE 30th");

                    alertU.setPositiveButton("Download & Install!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogI, int i) {


                            //   String url=Timer.getUrl("https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt");

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlA[1]));
                            startActivity(intent);
                        }
                    });

                    AlertDialog aU = alertU.create();
                    aU.show();


                }


            }
        }
        ///////////////////////////////////////////////////////////////////////up</
    }

boolean updater(boolean willDel){

    String json="";
    //https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt
            json=Timer.getUrl("https://raw.githubusercontent.com/rafiz001/VU-Routine/main/cse30.json");
if (json==""){
    Toast.makeText(this, "No Internet! Check your connection and try again.", Toast.LENGTH_SHORT).show();
  return false;
}


    try {



        JSONArray jsa=new JSONArray(json);
        JSONObject jobj=new JSONObject();
        if (willDel){
        db.delete("routine");}
        for (int j1=0;j1<jsa.length();j1++){

            jobj=jsa.getJSONObject(j1);

            String sqll="'"+jobj.getString("ccode")+"','"+jobj.getString("day")
                    +"','"+jobj.getString("effective")+"','"+jobj.getString("room")
                    +"','"+jobj.getString("sec")+"','"+jobj.getString("teacher")
                    +"','"+jobj.getString("timeStart")+"','"+jobj.getString("timeStop")
                    +"','"+jobj.getString("v")+"','"+jobj.getString("semester")+"'";
/*
            String url="";
            url=Timer.getUrl("https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt");
            if (url!="") {
                String urlA[] = url.split(",");
                //  Toast.makeText(this, Float.parseFloat(urlA[0])+"\n"+Float.parseFloat(BuildConfig.VERSION_NAME), Toast.LENGTH_LONG).show();
                if(Float.parseFloat(urlA[0])>Float.parseFloat(BuildConfig.VERSION_NAME)){
            needUpdate=true;
                    AlertDialog.Builder alertU=new AlertDialog.Builder(MainActivity.this);

                    alertU.setMessage("New app update is available. You have to download and install it right now!").setCancelable(true);
                    alertU.setIcon(getDrawable(R.drawable.cse_vu));
                    alertU.setTitle("VU CSE 30th");

                    alertU.setPositiveButton("Download & Install!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogI, int i) {


                            //   String url=Timer.getUrl("https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt");

                            Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(urlA[1]));
                            startActivity(intent);
                        }
                    });

                    AlertDialog aU=alertU.create();
                    aU.show();

                }
            }
            //app update finished

 */
            db.addRow(sqll);

        }
        Toast.makeText(MainActivity.this, "Data Updated!", Toast.LENGTH_SHORT).show();
    if(!needUpdate)MainActivity.this.recreate();


    }
    catch (Exception e){
        e.printStackTrace();
    }
    return true;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.about){
            AlertDialog.Builder alertD=new AlertDialog.Builder(MainActivity.this);

            alertD.setMessage("This free and open source app is for the routine of CSE 30th batch of Varendra University." +
                    " If Allah wills, I'll update new available routine ASAP." +
                    " I tried to make this app error free, hence if you find any bug or if you have any suggestion(s) then you can send me e-mail." +
                    "\n-\nMd. Rafiz Uddin" +
                    "\n222311079@vu.edu.bd").setCancelable(true);
            alertD.setIcon(getDrawable(R.drawable.cse_vu));
            alertD.setTitle("VU CSE 30th");

            alertD.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogI, int i) {
                    dialogI.cancel();
                }
            });
            alertD.setNegativeButton("Github!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/rafiz001/VU-Routine"));
                    startActivity(intent);
                }
            });
            alertD.setNeutralButton("Send e-mail!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:222311079@vu.edu.bd?Subject=About%20Routine%20App"));
                    startActivity(intent);
                }
            });
            AlertDialog aD=alertD.create();
            aD.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}