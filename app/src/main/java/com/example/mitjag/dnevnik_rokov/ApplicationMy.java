package com.example.mitjag.dnevnik_rokov;

import android.app.Application;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Mitja G on 24.5.2016.
 */
public class ApplicationMy extends Application {
    private Dnevnik all;
    private Dnevnik trenutni_podatki;
    private static final String DATA_MAP = "dnevnikmapa";
    private static final String FILE_NAME = "dnevnik.json";

    @Override
    public void onCreate() {
        super.onCreate();

        if (!load()) {
            all = Dnevnik.getDnevnik(); //testni prvi podatki
            save();
        }
        else {
            Calendar today = Calendar.getInstance();
            //today.set(2016,4,25);
            trenutni_podatki = all.getDnevnikByDate(today);

        }

    }
    public Dnevnik getAll(){ return all;}

    public Calendar getTrenutniDatum(){
        if(trenutni_podatki.getDnevnik().sizeDnevnik()>0)
            return trenutni_podatki.getDnevnik().getDnevnikById(0).getRok_uporabe();
        else
            return null;
    }

    public String getNazivByKoda(String koda){
        String naziv;
        naziv=all.getArtikl(koda).getNaziv();
        return naziv;
    }

    public String getOpisByKoda(String koda){
        String opis;
        opis=all.getArtikl(koda).getOpis();
        return opis;
    }

    public Dnevnik getPodatkiByDate(Calendar date){
        //all=Dnevnik.getDnevnik();
        Calendar trenutni=Calendar.getInstance();
        Dnevnik byDate=new Dnevnik();
        for(int i=0;i<all.sizeDnevnik();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            if(sdf.format(all.getDnevnikById(i).getRok_uporabe().getTime()).equals(sdf.format(date.getTime()))) {
                byDate.dodaj(all.getDnevnikById(i));
            }
        }
        return byDate;
    }
    public Dnevnik getByDate(){return trenutni_podatki;}

    public void setAll(Dnevnik all) {
        this.all = all;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean save() {

        return save(all,FILE_NAME);
    }

    public boolean load(){
        Dnevnik tmp = load(FILE_NAME);
        if (tmp!=null) all = tmp;
        else return false;
        return true;
    }

    private boolean save(Dnevnik a, String filename) {
        if (isExternalStorageWritable()) {
            File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                    + filename);
            try {
                long start = System.currentTimeMillis();
                System.out.println("Save "+file.getAbsolutePath()+" "+file.getName());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                PrintWriter pw = new PrintWriter(file);
                String sss=gson.toJson(a);
                System.out.println("Save time gson:"+(double)(System.currentTimeMillis()-start)/1000);
                pw.println(sss);
                pw.close();
                System.out.println("Save time s:"+(double)(System.currentTimeMillis()-start)/1000);
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error save! (FileNotFoundException)");
            } catch (IOException e) {
                System.out.println("Error save! (IOException)");
            }
        } else{
            System.out.println(this.getClass().getCanonicalName()+" NOT Writable");
        }
        return false;
    }


    private Dnevnik load(String name) {
        if (isExternalStorageReadable()) {
            try {
                File file = new File(this.getExternalFilesDir(DATA_MAP),"" + name);
                System.out.println("Load "+file.getAbsolutePath()+" "+file.getName());
                FileInputStream fstream = new FileInputStream(file);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader( new InputStreamReader(in));
                StringBuffer sb = new StringBuffer();
                String strLine;
                while ((strLine = br.readLine()) != null) {sb.append(strLine).append('\n');}
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Dnevnik a = gson.fromJson(sb.toString(), Dnevnik.class);
                if (a == null) { System.out.println("Error: fromJson Format error");
                } else { System.out.println(a.toString()); };
                return a;
            } catch (IOException e) {
                System.out.println("Error load "+e.toString());
            }}
        System.out.println("ExternalStorageAvailable is not avaliable");
        return null;}


}
