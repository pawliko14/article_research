package Objetcs;

public class StorenotesBestellingDetails {

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

    public StorenotesBestellingDetails(String leverancier_storenotes, String ORDERNUMMER_storenotes, String ARTIKELCODE_storenotes, String ARTIKELOMSCHRIJVING_storenotes, String BESTELD_storenotes, String GELEVERD_storenotes, String CFSTOCK_storenotes, String AFDELING_storenotes, String AFDELINGSEQ_storenotes, String MONTAGE_storenotes, String BESTELDATUM_storentoes, String leverancier_bestelling, String ORDERNUMMER_bestelling, String BESTELDATUM_bestelling) {
        Leverancier_storenotes = leverancier_storenotes;
        this.ORDERNUMMER_storenotes = ORDERNUMMER_storenotes;
        this.ARTIKELCODE_storenotes = ARTIKELCODE_storenotes;
        this.ARTIKELOMSCHRIJVING_storenotes = ARTIKELOMSCHRIJVING_storenotes;
        this.BESTELD_storenotes = BESTELD_storenotes;
        this.GELEVERD_storenotes = GELEVERD_storenotes;
        this.CFSTOCK_storenotes = CFSTOCK_storenotes;
        this.AFDELING_storenotes = AFDELING_storenotes;
        this.AFDELINGSEQ_storenotes = AFDELINGSEQ_storenotes;
        this.MONTAGE_storenotes = MONTAGE_storenotes;
        this.BESTELDATUM_storentoes = BESTELDATUM_storentoes;
        this.leverancier_bestelling = leverancier_bestelling;
        this.ORDERNUMMER_bestelling = ORDERNUMMER_bestelling;
        this.BESTELDATUM_bestelling = BESTELDATUM_bestelling;
    }

    public String getLeverancier_storenotes() {
        return Leverancier_storenotes;
    }

    public String getORDERNUMMER_storenotes() {
        return ORDERNUMMER_storenotes;
    }

    public String getARTIKELCODE_storenotes() {
        return ARTIKELCODE_storenotes;
    }

    public String getARTIKELOMSCHRIJVING_storenotes() {
        return ARTIKELOMSCHRIJVING_storenotes;
    }

    public String getBESTELD_storenotes() {
        return BESTELD_storenotes;
    }

    public String getGELEVERD_storenotes() {
        return GELEVERD_storenotes;
    }

    public String getCFSTOCK_storenotes() {
        return CFSTOCK_storenotes;
    }

    public String getAFDELING_storenotes() {
        return AFDELING_storenotes;
    }

    public String getAFDELINGSEQ_storenotes() {
        return AFDELINGSEQ_storenotes;
    }

    public String getMONTAGE_storenotes() {
        return MONTAGE_storenotes;
    }

    public String getBESTELDATUM_storentoes() {
        return BESTELDATUM_storentoes;
    }

    public String getLeverancier_bestelling() {
        return leverancier_bestelling;
    }

    public String getORDERNUMMER_bestelling() {
        return ORDERNUMMER_bestelling;
    }

    public String getBESTELDATUM_bestelling() {
        return BESTELDATUM_bestelling;
    }


    @Override
    public String toString() {
        return "StorenotesBestellingDetails{" +
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
                '}';
    }
}
