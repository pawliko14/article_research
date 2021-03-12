package Objetcs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StorenoteBestellingdetails_Stock {

    private String Leverancier_storenotes;
    private String ORDERNUMMER_storenotes;
    private String ARTIKELCODE_storenotes;
    private String ARTIKELOMSCHRIJVING_storenotes;
    private String BESTELD_storenotes;
    private String GELEVERD_storenotes;
    private String CFSTOCK_storenotes;
    private String AFDELING_storenotes;
    private String AFDELINGSEQ_storenotes;
    private String MONTAGE_storenotes;
    private String BESTELDATUM_storentoes;
    private String leverancier_bestelling;
    private String ORDERNUMMER_bestelling;
    private String BESTELDATUM_bestelling;
    private String afdelingseq_bestelling;
    private String ilosc_stock;
    private String naProdukcji_stock;
    private String Zapotrzebowanie_stock;


    @Override
    public String toString() {
        return "StorenoteBestellingdetails_Stock{" +
                "Leverancier_storenotes='" + Leverancier_storenotes + '\'' +
                ", ORDERNUMMER_storenotes='" + ORDERNUMMER_storenotes + '\'' +
                ", ARTIKELCODE_storenotes='" + ARTIKELCODE_storenotes + '\'' +
                ", ARTIKELOMSCHRIJVING_storenotes='" + ARTIKELOMSCHRIJVING_storenotes + '\'' +
                ", BESTELD_storenotes='" + BESTELD_storenotes + '\'' +
                ", GELEVERD_storenotes='" + GELEVERD_storenotes + '\'' +
                ", CFSTOCK_storenotes='" + CFSTOCK_storenotes + '\'' +
                ", AFDELING_storenotes='" + AFDELING_storenotes + '\'' +
                ", AFDELINGSEQ_storenotes='" + AFDELINGSEQ_storenotes + '\'' +
                ", MONTAGE_storenotes='" + MONTAGE_storenotes + '\'' +
                ", BESTELDATUM_storentoes='" + BESTELDATUM_storentoes + '\'' +
                ", leverancier_bestelling='" + leverancier_bestelling + '\'' +
                ", ORDERNUMMER_bestelling='" + ORDERNUMMER_bestelling + '\'' +
                ", BESTELDATUM_bestelling='" + BESTELDATUM_bestelling + '\'' +
                ", afdelingseq_bestelling='" + afdelingseq_bestelling + '\'' +
                ", ilosc_stock='" + ilosc_stock + '\'' +
                ", naProdukcji_stock='" + naProdukcji_stock + '\'' +
                ", Zapotrzebowanie_stock='" + Zapotrzebowanie_stock + '\'' +
                '}';
    }



}
