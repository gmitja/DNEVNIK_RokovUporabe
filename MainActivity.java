package com.example.mitjag.dnevnik_rokov;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity  extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ApplicationMy app;
    private TextView text_datum;
    private Calendar trenutni;
    private int stanje = 0;

    public static String METHOD_NAME = "";
    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String SOAP_ACTION = "http://tempuri.org/IService1/";
    public static String URL = "http://192.168.0.38/WebserviceDnevnik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trenutni = Calendar.getInstance();
        trenutni.add(Calendar.DATE, -1);
        Calendar tren = Calendar.getInstance();
        Date dat = trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum = (TextView) findViewById(R.id.textView);
        text_datum.setText(date1);

        app = (ApplicationMy) getApplication();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
       mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
       // mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
       // mRecyclerView.setAdapter(mAdapter);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 1);

        Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
        PendingIntent pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingintent);


        final class Task extends AsyncTask<Void, Void, Void> {
            String[] parse;
            String[] parse_elementi;
            protected void onPreExecute() {

            }

            protected Void doInBackground(Void... unused) {

                try {
                    METHOD_NAME = "VrniDnevnik";
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    //request.addProperty("id", Integer.parseInt(id));
                    envelope.setOutputSoapObject(request);

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                    androidHttpTransport.call(SOAP_ACTION + "VrniDnevnik", envelope);


                    SoapObject response = (SoapObject) envelope.bodyIn;
                    String data = response.getProperty(0).toString();
                    parse = data.split(";");


                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(Void unused) {
                Dnevnik novo = new Dnevnik();
                try {

                    for (int i = 0; i < parse.length; i++) {
                        parse_elementi = parse[i].split(">");

                        novo.dodaj(new Artikli(parse_elementi[0].toString(), parse_elementi[1].toString(), parse_elementi[2]));

                        Calendar cas_vnosa = Calendar.getInstance();
                        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = formatter.parse(parse_elementi[3]);
                        cas_vnosa.setTime(date);

                        Calendar rok_trajanja = Calendar.getInstance();
                        DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
                        Date date1 = formatter1.parse(parse_elementi[4]);
                        rok_trajanja.setTime(date1);

                        novo.dodaj(new Datum_uporabe(novo.getArtikl(parse_elementi[0].toString()), cas_vnosa, rok_trajanja));

                    }
                } catch (Exception ex) {
                }
                app.setAll(novo);
                osvezi();

            }
        }
        new Task().execute();

    }

    public void buttonOnClickNaprej(View v) {
        Dnevnik nov = new Dnevnik();

        //Calendar trenutni=app.getByDate().getDnevnikById(0).getRok_uporabe();
        trenutni.add(Calendar.DATE, 1);
        Date dat = trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum = (TextView) findViewById(R.id.textView);
        text_datum.setText(date1);
        mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
        mRecyclerView.setAdapter(mAdapter);
        }

    public void buttonOnClickNazaj(View v) {
        Dnevnik nov = new Dnevnik();
        //Calendar trenutni=app.getByDate().getDnevnikById(0).getRok_uporabe();
        trenutni.add(Calendar.DATE, -1);
        Date dat = trenutni.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, dd-MM-yyyy");
        String date1 = format1.format(dat);
        text_datum = (TextView) findViewById(R.id.textView);
        text_datum.setText(date1);
        mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
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
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Dnevnik nov = new Dnevnik();
        Calendar trenutni = Calendar.getInstance();
        trenutni.add(Calendar.DAY_OF_MONTH, -1);
        //mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
       // mRecyclerView.setAdapter(mAdapter);
    }

    public void osvezi() {

        Dnevnik nov = new Dnevnik();
        Calendar trenutni = Calendar.getInstance();
        trenutni.add(Calendar.DAY_OF_MONTH, -1);
        mAdapter = new AdapterRoki(app.getPodatkiByDate(trenutni), this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
