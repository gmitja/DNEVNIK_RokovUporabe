package com.example.mitjag.dnevnik_rokov;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.mitjag.dnevnik_rokov.MainActivity.METHOD_NAME;
import static com.example.mitjag.dnevnik_rokov.MainActivity.NAMESPACE;
import static com.example.mitjag.dnevnik_rokov.MainActivity.SOAP_ACTION;
import static com.example.mitjag.dnevnik_rokov.MainActivity.URL;

public class AddActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn,buttonDate;
    private TextView formatTxt, contentTxt,display,opisTxt;
    private TextView Naziv_Txt;
    private String ean_koda,naziv,opis,rok_trajanja,ean_text;
    private Calendar rok_uporabe;
    Calendar cal=Calendar.getInstance();
    ApplicationMy app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        app = (ApplicationMy) getApplication();
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        display=(TextView)findViewById(R.id.textViewDatum);
        opisTxt=(TextView)findViewById(R.id.textViewOpis);
        buttonDate=(Button)findViewById(R.id.button_date);
        scanBtn.setOnClickListener(this);
        buttonDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(AddActivity.this,R.style.DialogTheme,listener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        onClick(findViewById(R.id.scan_button));

    }
    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth){
            display.setText(""+dayOfMonth+". "+(monthOfYear+1)+". "+year);
            rok_uporabe=Calendar.getInstance();
            rok_uporabe.set(year,monthOfYear,dayOfMonth);
            rok_trajanja=display.getText().toString();
        }
    };

    public void onClick(View v){
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator=new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        IntentResult scanningResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(scanningResult!=null){
            String scanContent=scanningResult.getContents();
            String scanFormat=scanningResult.getFormatName();
            //formatTxt.setText("Format: "+scanFormat);
            if(scanContent!=null) {
                formatTxt.setText(scanContent);
                String rez = scanContent;
                ean_koda=scanContent;
                ean_text=scanContent;
                rez = app.getNazivByKoda(scanContent);
                contentTxt.setText(rez);
                naziv=rez;
                opis=app.getOpisByKoda(scanContent);
                opisTxt.setText(opis);

                new DatePickerDialog(AddActivity.this,R.style.DialogTheme,listener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        }
        else{
            Toast toast=Toast.makeText(getApplicationContext(),
                    "No scan data received!",Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem mnuTextGroup = menu.findItem(R.id.menu_dodaj);
        mnuTextGroup.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_nazaj:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveData(View v){
        if(ean_koda!=null && naziv!=null && opis!= null && rok_uporabe!=null&& ean_koda!="" &&naziv!="" && opis!=""){
            Dnevnik a=app.getAll();
            a.dodaj(new Datum_uporabe(new Artikli(ean_koda,naziv,opis),Calendar.getInstance(),rok_uporabe));
            app.setAll(a);
            //app.save();

            final class Task1 extends AsyncTask<Void, Void, Void> {
                String[] parse;
                String[] parse_elementi;
                protected void onPreExecute() {

                }

                protected Void doInBackground(Void... unused) {

                    try {
                        METHOD_NAME = "VstaviRokUporabe";
                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        request.addProperty("ean",ean_text);
                        request.addProperty("datum_uporabe",rok_trajanja);
                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                        androidHttpTransport.call(SOAP_ACTION + "VstaviRokUporabe", envelope);

                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                    return null;
                }

                protected void onPostExecute(Void unused) {
                    try {


                    } catch (Exception ex) {
                    }

                }
            }
            new Task1().execute();

            showSnackBarUspesno(v);
            display.setText("");
            contentTxt.setText("");
            formatTxt.setText("");
            opisTxt.setText("");
            ean_koda=null;
            naziv=null;
        }
        else{
            showSnackBar(v);
        }
    }

    public void showSnackBarUspesno(View v){
        Toast t= Toast.makeText(AddActivity.this, "Shranjeno!", Toast.LENGTH_LONG);
        t.show();
    }

    public void showSnackBar(View v){
        Snackbar snack = Snackbar.make(v, "Vnesite vse podatke!", Snackbar.LENGTH_LONG);
        snack.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, "OK clicked!", Toast.LENGTH_LONG).show();
            }
        });
        View sbView = snack.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snack.show();
        snack.setActionTextColor(Color.RED);
        View vista=snack.getView();
        vista.setMinimumHeight(240);
        vista.setBackgroundColor(Color.BLACK);
        snack.show();
    }
}

