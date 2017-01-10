package com.example.mitjag.dnevnik_rokov;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ApplicationMy app;
    private TextView text_datum;
    private Calendar trenutni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trenutni=Calendar.getInstance();
        trenutni.add(Calendar.DATE,-1);
       // Calendar tren=Calendar.getInstance();
        Date dat=trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum=(TextView)findViewById(R.id.textView);
        text_datum.setText(date1);

        app = (ApplicationMy) getApplication();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
        mRecyclerView.setAdapter(mAdapter);

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,1);

        Intent intent=new Intent(getApplicationContext(),Notification_receiver.class);
        PendingIntent pendingintent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingintent);



    }


    public void buttonOnClickNaprej(View v){
        Dnevnik nov=new Dnevnik();

        //Calendar trenutni=app.getByDate().getDnevnikById(0).getRok_uporabe();
        trenutni.add(Calendar.DATE,1);
        Date dat=trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum=(TextView)findViewById(R.id.textView);
        text_datum.setText(date1);
        mAdapter=new AdapterRoki(app.getPodatkiByDate(trenutni),this);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void buttonOnClickNazaj(View v){
        Dnevnik nov=new Dnevnik();
        //Calendar trenutni=app.getByDate().getDnevnikById(0).getRok_uporabe();
        trenutni.add(Calendar.DATE,-1);
        Date dat=trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum=(TextView)findViewById(R.id.textView);
        text_datum.setText(date1);
        mAdapter=new AdapterRoki(app.getPodatkiByDate(trenutni),this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem mnuTextGroup = menu.findItem(R.id.menu_nazaj);
        mnuTextGroup.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_dodaj:
                Intent i=new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Dnevnik nov=new Dnevnik();
        Calendar trenutni=Calendar.getInstance();
        trenutni.add(Calendar.DAY_OF_MONTH,-1);
        mAdapter=new AdapterRoki(app.getPodatkiByDate(trenutni),this);
        mRecyclerView.setAdapter(mAdapter);
    }

}
