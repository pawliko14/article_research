package Objetcs;

public class StorenotesDetailArticle {

    private String Leverancier;
    private String ORDERNUMMER;
    private String ARTIKELCODE;
    private String BESTELD;
    private String GELEVERD;
    private String BOSTDEH;
    private String CFSTOCK;
    private String AFDELING;
    private String AFDELINGSEQ;
    private String BESTELDATUM;
    private String STATUSCODE;
    private String LEVERINGSDATUMEFFECTIEF;

    public StorenotesDetailArticle(String leverancier, String ORDERNUMMER, String ARTIKELCODE, String BESTELD, String GELEVERD, String BOSTDEH, String CFSTOCK, String AFDELING, String AFDELINGSEQ, String BESTELDATUM, String STATUSCODE, String LEVERINGSDATUMEFFECTIEF) {
        Leverancier = leverancier;
        this.ORDERNUMMER = ORDERNUMMER;
        this.ARTIKELCODE = ARTIKELCODE;
        this.BESTELD = BESTELD;
        this.GELEVERD = GELEVERD;
        this.BOSTDEH = BOSTDEH;
        this.CFSTOCK = CFSTOCK;
        this.AFDELING = AFDELING;
        this.AFDELINGSEQ = AFDELINGSEQ;
        this.BESTELDATUM = BESTELDATUM;
        this.STATUSCODE = STATUSCODE;
        this.LEVERINGSDATUMEFFECTIEF = LEVERINGSDATUMEFFECTIEF;
    }


    public String getLeverancier() {
        return Leverancier;
    }

    public String getORDERNUMMER() {
        return ORDERNUMMER;
    }

    public String getARTIKELCODE() {
        return ARTIKELCODE;
    }

    public String getBESTELD() {
        return BESTELD;
    }

    public String getGELEVERD() {
        return GELEVERD;
    }

    public String getBOSTDEH() {
        return BOSTDEH;
    }

    public String getCFSTOCK() {
        return CFSTOCK;
    }

    public String getAFDELING() {
        return AFDELING;
    }

    public String getAFDELINGSEQ() {
        return AFDELINGSEQ;
    }

    public String getBESTELDATUM() {
        return BESTELDATUM;
    }

    public String getSTATUSCODE() {
        return STATUSCODE;
    }

    public String getLEVERINGSDATUMEFFECTIEF() {
        return LEVERINGSDATUMEFFECTIEF;
    }

    @Override
    public String toString() {
        return "StorenotesDetailArticle{" +
                "Leverancier='" + Leverancier + '\'' +
                ", ORDERNUMMER='" + ORDERNUMMER + '\'' +
                ", ARTIKELCODE='" + ARTIKELCODE + '\'' +
                ", BESTELD='" + BESTELD + '\'' +
                ", GELEVERD='" + GELEVERD + '\'' +
                ", BOSTDEH='" + BOSTDEH + '\'' +
                ", CFSTOCK='" + CFSTOCK + '\'' +
                ", AFDELING='" + AFDELING + '\'' +
                ", AFDELINGSEQ='" + AFDELINGSEQ + '\'' +
                ", BESTELDATUM='" + BESTELDATUM + '\'' +
                ", STATUSCODE='" + STATUSCODE + '\'' +
                ", LEVERINGSDATUMEFFECTIEF='" + LEVERINGSDATUMEFFECTIEF + '\'' +
                '}';
    }
}

