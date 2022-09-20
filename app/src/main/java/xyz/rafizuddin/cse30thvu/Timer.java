package xyz.rafizuddin.cse30thvu;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */

public class Timer extends AppWidgetProvider {
    //DatabaseHandler db = new DatabaseHandler(this);
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent refresh=new Intent(context,Timer.class);
        refresh.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ida=new int[]{appWidgetId};
        refresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ida);


        PendingIntent refreshIntent=PendingIntent.getBroadcast(context,appWidgetId,refresh,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timer);
        views.setOnClickPendingIntent(R.id.wid,refreshIntent);



SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss MM.dd");
String date=sdf.format(new Date());
        //CharSequence s= DateFormat.getDateInstance().format("MM dd, yyyy")+"\nrafiz";
    //    Toast.makeText(context, "welcome!", Toast.LENGTH_SHORT).show();
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
       // RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timer);


      //  Toast.makeText(context, s, Toast.LENGTH_SHORT).show();


       //
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
         DatabaseHandler mydb=new DatabaseHandler(context);
        String dayA[]={"Sunday","Monday","Tuesday","Wednesday","Thursday"};
        int nDay=0;
        switch (day){
            case 4:
            case 5:
            case 6: nDay=0; break;
            default: nDay=day+1;break;
        }
        if (day!=5||day!=6){
        views.setTextViewText(R.id.today1, dayA[day]+":\n"+mydb.widget(day,mydb.showsec())+"");}
        views.setTextViewText(R.id.tomorrow1, dayA[nDay]+":\n"+mydb.widget(nDay,mydb.showsec())+"");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);



        }
    }
    public static String getUrl(String givenurl){

        StringBuilder output=new StringBuilder();
        try {
            URL url=new URL(givenurl);
            URLConnection urlConnection=url.openConnection();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                output.append(line+"\n");
            }
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return output.toString();
    }


}