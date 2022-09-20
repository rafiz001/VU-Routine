package xyz.rafizuddin.cse30thvu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VU";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROUTINE_TABLE = "CREATE TABLE routine(id INTEGER PRIMARY KEY,ccode TEXT,day TEXT,"
                +"effective TEXT,room TEXT,sec TEXT,teacher TEXT,timeStart TEXT,timeStop TEXT,v TEXT,semester TEXT)";
        db.execSQL(CREATE_ROUTINE_TABLE);
        String CREATE_SET_TABLE = "CREATE TABLE sett(id INTEGER PRIMARY KEY,sec INTEGER)";
        db.execSQL(CREATE_SET_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS routine");

        // Create tables again
        onCreate(db);
    }

    void addRow(String ccode) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into routine (ccode,day ,"
                +"effective ,room ,sec ,teacher ,timeStart ,timeStop,v,semester )" +
                "values ( "+ccode+")");
        db.close(); // Closing database connection

    }
    String test(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from routine where id='"+Integer.parseInt(id)+"'", null);
        String ccode="";
        if (cursor.moveToFirst()) {
            do {


                ccode=cursor.getString(1);



            } while (cursor.moveToNext());
        }
        db.close();
        return ccode;
    }

    float version(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select v from routine where id='1'", null);
        float ccode=0;
        if (cursor.moveToFirst()) {
            do {


                ccode=Float.parseFloat(cursor.getString(0));



            } while (cursor.moveToNext());
        }
        db.close();
        return ccode;
    }
    boolean issec(String table){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select COUNT(*) from "+table, null);
        String result="0";
        if (cursor.moveToFirst()) {
            do {


                result=  cursor.getString(0);



            } while (cursor.moveToNext());
        }
        db.close();
        if (Integer.parseInt(result)==0) {return false;} else {return true;}

    }
    int showsec(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select sec from sett", null);
        int result=7;
        if (cursor.moveToFirst()) {
            do {


                result=cursor.getInt(0);



            } while (cursor.moveToNext());
        }
        db.close();
        return result;
    }
    void addsec(int sec){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("insert into sett (sec) values ( "+sec+")");
        db.close();
    }
    String ampm(String input){
        String outputA[]=input.split(":");
        String hour,min,stat;
        if(Integer.parseInt(outputA[0])>12){hour=(Integer.parseInt(outputA[0])-12)+"";stat="PM";}
        else if (Integer.parseInt(outputA[0])==12){hour=outputA[0];stat="PM";}
        else {hour=outputA[0];stat="AM";}
        min=outputA[1];
        return hour+":"+min+" "+stat;
    }
    int today(){
        /*SimpleDateFormat sdf=new SimpleDateFormat("eee");
        String date=sdf.format(new Date());*/
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day-1;
    }





   String widget(int day,int sec){
        String output="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from routine where (day='" + day + "' and sec='"+sec+"') ", null);
        if (cursor.moveToFirst()) {
            do {
                output+=cursor.getString(1)+"("+cursor.getString(4)+")"+ampm(cursor.getString(7))+"\n";
            } while (cursor.moveToNext());
        }
        db.close();
        return output;
    }
    String output( int sec){
        String secA[]={"A","B","C"};
        String sem="";
        String dayA[]={"Sunday","Monday","Tuesday","Wednesday","Thursday"};
        SQLiteDatabase db = this.getWritableDatabase();
        String ccode="<html><head><style>small{color:white;float:right;}body{background: black;}table{border-collapse: collapse;width:100%;}td,th{border: 1px solid #dddddd;text-align: center;padding:8px;}tr:nth-child(even){background-color:#0f0f0f;color:#ffffff;}tr:nth-child(odd){background-color:#a0a0a0;}.date{border: 2px dotted red;color: white;text-align:center;padding:7px;}.today{border: 2px solid red;}a{color: green;}</style></head><body>";
        String idate="";ccode+="<div class='date'>Effective from: <span id='ef'></span><br>Section: "+secA[sec]+"<span id='update'></span></div><br><table><tr><th>Day</th><th>Class/Lab</th><th>Class/Lab</th></tr>";
        for (int i=0;i<=4;i++) {
            Cursor cursor = db.rawQuery("select * from routine where (day='" + i + "' and sec='" + sec + "') ", null);
            String classS="nottoday";
            if(i==today()){
                classS="today";
            }
            ccode += "<tr class='"+classS+"'><td>" + dayA[i] + "</td>";

            if (cursor.moveToFirst()) {
                do {

                    sem=cursor.getString(10);
                    ccode += "<td>Course Code: " + cursor.getString(1)

                       //     + "<br>Starting from: " + cursor.getString(3)
                            + "<br>Room: "
                            + cursor.getString(4)

                            + "<br>Teacher: "
                            + cursor.getString(6)
                            + "<br>Start: "
                            + ampm(cursor.getString(7))
                            + "<br>Stop: "
                            + ampm(cursor.getString(8)) + "</td>";
                    idate=cursor.getString(3);

                } while (cursor.moveToNext());
            }
            ccode+="</tr>";
        }
        String month[]=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
        char[] oA;
        oA=idate.toCharArray();
      /*  String out="";
    for (int i=0;i<=oA.length-1;i++){
        out+=oA[i];
    }
*/
        String out=""+oA[6]+oA[7]+" "+month[8]+", "+oA[0]+oA[1]+oA[2]+oA[3];

        ccode+="</table><script>document.getElementById('ef').innerHTML='"+out+""+"<br>Semester: "+sem+"';</script>" +
                "<script>" +
                "var xhttp=new XMLHttpRequest();\n" +
                "xhttp.onreadystatechange=function(){\n" +
                "if(this.readyState==4&&this.status==200){\n" +
                "//document.getElementById('update').innerHTML=this.responseText;\n" +
                "var a=this.responseText;\n" +
                "//var a=\"1.2,http://wiki.com\";\n" +
                "var b=a.split(\",\");\n" +
                "if(parseFloat(b[0])>parseFloat("+BuildConfig.VERSION_NAME+")){\n" +
                "document.getElementById('update').innerHTML=\"<br>App Update is available. You have to download and install new app from <a href='\"+b[1]+\"'>here</a>.\";}" +
                "" +
                "" +
                "}};\n" +
                "xhttp.open(\"GET\",\"https://raw.githubusercontent.com/rafiz001/VU-Routine/main/upadeLink.txt\",true);\n" +
                "xhttp.send();\n" +
                "</script>" +
                "<small>"+"Version: " +BuildConfig.VERSION_NAME+"</small></body></html>";
        db.close();

        return ccode;

    }
     void delete(String table){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+table+" where (2=2)");
        db.close();

    }

}
