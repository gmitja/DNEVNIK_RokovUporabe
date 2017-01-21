package com.example.mitjag.dnevnik_rokov;

import java.util.Calendar;

/**
 * Created by Mitja G on 24.5.2016.
 */
public class Datum_uporabe {
    private Artikli artikel;
    private Calendar datum_vnosa;
    private Calendar rok_uporabe;

    public Datum_uporabe(Artikli artikel, Calendar datum_vnosa, Calendar rok_uporabe) {
        this.artikel = artikel;
        this.datum_vnosa = datum_vnosa;
        this.rok_uporabe = rok_uporabe;
    }

    public Artikli getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikli artikel) {
        this.artikel = artikel;
    }

    public Calendar getDatum_vnosa() {
        return datum_vnosa;
    }

    public void setDatum_vnosa(Calendar datum_vnosa) {
        this.datum_vnosa = datum_vnosa;
    }

    public Calendar getRok_uporabe() {
        return rok_uporabe;
    }

    public void setRok_uporabe(Calendar rok_uporabe) {
        this.rok_uporabe = rok_uporabe;
    }
}
