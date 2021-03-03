package LeverancierOrdernummer;

public class LeverancierOrdernummer {

    private String article;
    private String Leverancier;
    private String Ordernummer;
    private String Besteld;
    private String Gelerved;
    private String Bostdeh;
    private String CFstock;
    private String Afdeling;
    private String Afdelingseq;



    public LeverancierOrdernummer(String article, String leverancier, String ordernummer) {
        this.article = article;
        Leverancier = leverancier;
        Ordernummer = ordernummer;
    }

    public LeverancierOrdernummer(String article, String leverancier, String ordernummer, String besteld, String gelerved, String bostdeh, String CFstock) {
        this.article = article;
        Leverancier = leverancier;
        Ordernummer = ordernummer;
        Besteld = besteld;
        Gelerved = gelerved;
        Bostdeh = bostdeh;
        this.CFstock = CFstock;
    }



    public String getAfdeling() {
        return Afdeling;
    }

    public void setAfdeling(String afdeling) {
        Afdeling = afdeling;
    }

    public String getAfdelingseq() {
        return Afdelingseq;
    }

    public void setAfdelingseq(String afdelingseq) {
        Afdelingseq = afdelingseq;
    }


    @Override
    public String toString() {
        return "LeverancierOrdernummer{" +
                "article='" + article + '\'' +
                ", Leverancier='" + Leverancier + '\'' +
                ", Ordernummer='" + Ordernummer + '\'' +
                ", Besteld='" + Besteld + '\'' +
                ", Gelerved='" + Gelerved + '\'' +
                ", Bostdeh='" + Bostdeh + '\'' +
                ", CFstock='" + CFstock + '\'' +
                ", Afdeling='" + Afdeling + '\'' +
                ", Afdelingseq='" + Afdelingseq + '\'' +
                '}';
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getLeverancier() {
        return Leverancier;
    }

    public void setLeverancier(String leverancier) {
        Leverancier = leverancier;
    }

    public String getOrdernummer() {
        return Ordernummer;
    }

    public void setOrdernummer(String ordernummer) {
        Ordernummer = ordernummer;
    }

    public String getBesteld() {
        return Besteld;
    }

    public void setBesteld(String besteld) {
        Besteld = besteld;
    }

    public String getGelerved() {
        return Gelerved;
    }

    public void setGelerved(String gelerved) {
        Gelerved = gelerved;
    }

    public String getBostdeh() {
        return Bostdeh;
    }

    public void setBostdeh(String bostdeh) {
        Bostdeh = bostdeh;
    }

    public String getCFstock() {
        return CFstock;
    }

    public void setCFstock(String CFstock) {
        this.CFstock = CFstock;
    }
}
