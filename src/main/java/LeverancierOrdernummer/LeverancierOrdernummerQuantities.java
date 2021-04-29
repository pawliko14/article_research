package LeverancierOrdernummer;

public class LeverancierOrdernummerQuantities extends LeverancierOrdernummer{

    private String Besteld;
    private String Gelerved;
    private String Bostdeh;
    private String CFstock;


    public LeverancierOrdernummerQuantities(String article, String leverancier, String ordernummer) {
        super(article, leverancier, ordernummer);
    }
}
