package com.example.mitjag.dnevnik_rokov;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mitja G on 24.5.2016.
 */
public class Dnevnik {
    private ArrayList<Artikli> artikli;
    private ArrayList<Datum_uporabe> datum;
    private Dnevnik all;

    public Dnevnik() {
        artikli=new ArrayList<Artikli>();
        datum=new ArrayList<Datum_uporabe>();
    }

    public int sizeDnevnik(){
        return datum.size();
    }

    public Datum_uporabe getDnevnikById(int i){
        if(datum.size()>0)
            return datum.get(i);
        else
            return null;
    }

    public void dodaj(Artikli a){artikli.add(a);}
    public void dodaj(Datum_uporabe d){datum.add(d);}

    public Artikli getArtikl(String koda){
        Artikli a=null;
        for(int i=0;i<artikli.size();i++){
            if(koda.equals(artikli.get(i).getKoda_izdelka())){
                a=artikli.get(i);
            }
        }
       return a;
    }
    public static Dnevnik getDnevnik() {
        Dnevnik all=new Dnevnik();
        all.dodaj(new Artikli("3830000625043","Pepsi","0,5L"));
        all.dodaj(new Artikli("20471217","Coca Cola","0,5L"));
        all.dodaj(new Artikli("54365575","Chio Chips Paprika","500g"));
        all.dodaj(new Artikli("5413548040639","Frutabela Marelica","25g"));
        all.dodaj(new Artikli("2056485968214","KitKat","42g"));
        all.dodaj(new Artikli("2058648524515","Rogljiček 7days kakav","80g"));
        all.dodaj(new Artikli("205462512","Coca Cola","1,5L"));
        all.dodaj(new Artikli("5842356125345","Toblerone","200g"));
        Calendar c= Calendar.getInstance();
        Calendar d= Calendar.getInstance();
        Calendar e= Calendar.getInstance();
        Calendar g= Calendar.getInstance();
        Calendar h= Calendar.getInstance();
        Calendar f= Calendar.getInstance();
        c.set(2016,7,8);

        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),c));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),c));
        all.dodaj(new Datum_uporabe(all.artikli.get(7),Calendar.getInstance(),c));
        d.set(2016,5,25);
        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),d));
        all.dodaj(new Datum_uporabe(all.artikli.get(2),Calendar.getInstance(),d));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),d));
        e.set(2016,5,24);
        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),e));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),e));
        all.dodaj(new Datum_uporabe(all.artikli.get(2),Calendar.getInstance(),e));
        g.set(2016,5,31);
        all.dodaj(new Datum_uporabe(all.artikli.get(2),Calendar.getInstance(),g));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),g));
        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),g));
        h.set(2016,5,27);
        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),h));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),h));
        all.dodaj(new Datum_uporabe(all.artikli.get(2),Calendar.getInstance(),h));
        all.dodaj(new Datum_uporabe(all.artikli.get(3),Calendar.getInstance(),h));


        f.set(2016,5,22);
        all.dodaj(new Datum_uporabe(all.artikli.get(0),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(1),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(2),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(3),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(4),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(5),Calendar.getInstance(),f));
        all.dodaj(new Datum_uporabe(all.artikli.get(6),Calendar.getInstance(),f));

        return all;
    }
    public Dnevnik getDnevnikByDate(Calendar date){
        Dnevnik byDate=new Dnevnik();
        for(int i=0;i<sizeDnevnik();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            if(sdf.format(datum.get(i).getRok_uporabe().getTime()).equals(sdf.format(date.getTime()))) {
                byDate.dodaj(datum.get(i));
            }
        }
        return byDate;
    }
}