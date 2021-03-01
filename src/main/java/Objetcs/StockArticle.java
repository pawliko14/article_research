package Objetcs;

public class StockArticle {


    String naProdukcji = null;
    String kodArtykulu = null;
    String zapotrzebowanie = null;
    String Ilosc = null;

    public StockArticle(String naProdukcji, String kodArtykulu, String zapotrzebowanie, String ilosc) {
        this.naProdukcji = naProdukcji;
        this.kodArtykulu = kodArtykulu;
        this.zapotrzebowanie = zapotrzebowanie;
        Ilosc = ilosc;
    }


    public String getNaProdukcji() {
        return naProdukcji;
    }

    public String getKodArtykulu() {
        return kodArtykulu;
    }

    public String getZapotrzebowanie() {
        return zapotrzebowanie;
    }

    public String getIlosc() {
        return Ilosc;
    }


    @Override
    public String toString() {
        return "StockArticle{" +
                "naProdukcji='" + naProdukcji + '\'' +
                ", kodArtykulu='" + kodArtykulu + '\'' +
                ", zapotrzebowanie='" + zapotrzebowanie + '\'' +
                ", Ilosc='" + Ilosc + '\'' +
                '}';
    }
}
