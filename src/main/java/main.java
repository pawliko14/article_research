import DBConnector.DBConnectorFATDB;
import DBConnector.DBConnectorGtt;
import Logic.Machine_Structure_detail_logic;
import Objetcs.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class main {


    private static Connection connection_fatdb = null;
    private static Connection connection_gtt = null;
    private static List<Machine_Structure_Detail> Articles_in_Structure;


    public static  void main(String[] args) throws SQLException {

         connection_fatdb = DBConnectorFATDB.dbConnector();
         connection_gtt = DBConnectorGtt.dbConnector();


         /*
         1. step get data from gtt database for example : 21050204
            create an Object with articles from Strucutre(Gtt_database)
            assign for the machine (21050204)  its parent or main Project (7/XXXXXXXXX)
          */
        MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("21050204");
        MachineObj.getMachineArtilcesLogic().GetArticlesFromMachine().forEach(System.out::println);
        System.out.println(MachineObj.toString());

        /*
         2. Articles Research  check each article which isnt 'F' for its stock, and orderds
         */

        ArticleAnalyze_2(MachineObj);


    }


    private static void ArticleAnalyze_2(MachineStructureWithParentProjects machineObj) throws SQLException {


        System.out.println("MACHINE  : " + machineObj.toString());


        machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(0).getCHILDARTICLE();

        // step (1). check for the Stock,
        checkForTheStock( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(0).getCHILDARTICLE());

        // step(2). based on stock condition( checking in future), now its time to check for storenotesdetail
//        checkForStorenotesDetail( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(0).getCHILDARTICLE(),
//                                  machineObj.getProject()
//        );

        // STEP (2 v2) - bas on stock condtiion, check for bestellingdetail and storenotesdetail simutanuesly;

        checkStorenotesBestellingDetailsArticle( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(0).getCHILDARTICLE(),
                machineObj.getProject()
        );


    }

    private static void checkStorenotesBestellingDetailsArticle(String childarticle, String ProjectNumber) throws SQLException {

        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles =  "select \n" +
                "  s.Leverancier as Leverancier_storenotes\n" +
                ", s.ORDERNUMMER  as ORDERNUMMER_storenotes\n" +
                ", s.ARTIKELCODE  as ARTIKELCODE_storenotes\n" +
                ", s.ARTIKELOMSCHRIJVING as ARTIKELOMSCHRIJVING_storenotes\n" +
                ", s.BESTELD as BESTELD_storenotes\n" +
                ", s.GELEVERD  as GELEVERD_storenotes\n" +
                ", s.CFSTOCK as CFSTOCK_storenotes\n" +
                ", s.AFDELING as AFDELING_storenotes\n" +
                ", s.AFDELINGSEQ as AFDELINGSEQ_storenotes\n" +
                ", s.MONTAGE as MONTAGE_storenotes\n" +
                ", s.BESTELDATUM as BESTELDATUM_storentoes\n" +
                ", b2.leverancier as leverancier_bestelling\n" +
                ", b2.ORDERNUMMER  as ORDERNUMMER_bestelling\n" +
                ", b2.BESTELDATUM  as BESTELDATUM_bestelling\n" +
                "from storenotesdetail s \n" +
                "left join bestellingdetail b2 \n" +
                "on s.ARTIKELCODE  = b2.ARTIKELCODE \n" +
                "Where s.ARTIKELCODE  = ? \n" +
                "and s.afdelingseq  =  ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,childarticle);
        pstmnt.setString(2, ProjectNumber);


        ResultSet rs=pstmnt.executeQuery();
        StorenotesBestellingDetails storenoteDetailsBestellingDetails = null;

        if(rs.next() == true)
        {
            storenoteDetailsBestellingDetails = new StorenotesBestellingDetails(
                    rs.getString("Leverancier_storenotes"),
                    rs.getString("ORDERNUMMER_storenotes"),
                    rs.getString("ARTIKELCODE_storenotes"),
                    rs.getString("ARTIKELOMSCHRIJVING_storenotes"),
                    rs.getString("BESTELD_storenotes"),
                    rs.getString("GELEVERD_storenotes"),
                    rs.getString("CFSTOCK_storenotes"),
                    rs.getString("AFDELING_storenotes"),
                    rs.getString("AFDELINGSEQ_storenotes"),
                    rs.getString("MONTAGE_storenotes"),
                    rs.getString("BESTELDATUM_storentoes"),
                    rs.getString("leverancier_bestelling"),
                    rs.getString("ORDERNUMMER_bestelling"),
                    rs.getString("BESTELDATUM_bestelling")
                    );
        }


        System.out.println("StorenotesBetllingDetails article " + storenoteDetailsBestellingDetails);


        pstmnt.close();
        rs.close();
        connection_fatdb.close();


    }



    /**
    * not used - check  checkStorenotesBestellingDetailsArticle() first
     */
    private static void checkForStorenotesDetail(String childarticle, String ProjectNumber) throws SQLException {

        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles =  "select Leverancier ,ORDERNUMMER ,ARTIKELCODE ,BESTELD , GELEVERD ,BOSTDEH , CFSTOCK , AFDELING , AFDELINGSEQ, BESTELDATUM , STATUSCODE , LEVERINGSDATUMEFFECTIEF  \n" +
                "from storenotesdetail s  where ARTIKELCODE  = ? and AFDELINGSEQ  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,childarticle);
        pstmnt.setString(2, ProjectNumber);


        ResultSet rs=pstmnt.executeQuery();
        StorenotesDetailArticle storeNoteArticle = null;

        if(rs.next() == true)
        {
            storeNoteArticle = new StorenotesDetailArticle(
                    rs.getString("Leverancier"),
                    rs.getString("ORDERNUMMER"),
                    rs.getString("ARTIKELCODE"),
                    rs.getString("BESTELD"),
                    rs.getString("GELEVERD"),
                    rs.getString("BOSTDEH"),
                    rs.getString("CFSTOCK"),
                    rs.getString("AFDELING"),
                    rs.getString("AFDELINGSEQ"),
                    rs.getString("BESTELDATUM"),
                    rs.getString("STATUSCODE"),
                  ""//  rs.getString("LEVERINGSDATUMEFFECTIEF")
                    );
        }


        System.out.println("Storenotes article " + storeNoteArticle);


        pstmnt.close();
        rs.close();
        connection_fatdb.close();

    }


    /**
     *  -  check stock status for article, right now there is no condition for next  step - for future purpose
     *
     * @param childarticle article from structures, all child articles have parent one, here is no use for it
     * @throws SQLException
     */
    private static void checkForTheStock(String childarticle) throws SQLException {

         Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select naProdukcji ,kodArtykulu ,zapotrzebowanie ,Ilosc from stock s  where kodArtykulu  = ? limit 1 ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,childarticle);

        ResultSet rs=pstmnt.executeQuery();
        StockArticle stockArticle = null;

        if(rs.next() == true)
        {
            stockArticle = new StockArticle(rs.getString("naProdukcji"),
                    rs.getString("kodArtykulu"),
                    rs.getString("zapotrzebowanie"),
                    rs.getString("Ilosc")
            );
        }


        System.out.println("Stock article " + stockArticle);


        pstmnt.close();
        rs.close();
        connection_fatdb.close();

    }


    private static void ArticleAnalyze(Machine_Structure_Detail s) {

    //    System.out.println(s.toString());

        //1. check article type
        switch (s.getType() ){
            case "P" :

                /*
                 1. IF its 'P' type, there always should be production note which starts with '500/   retrive information from bestellingdetails and storenotesdetail\
                 2. IF this is Article 'P' type, it is 99% possible that inside this article hides another ( piramid scheme)

                 How to check how deep this article is??
                 */

                break;

            case "Y" :

                break;

            case "A" :

                break;

            default:

                System.err.println("there is no P || Y ||A article!");

                break;
        }



    }


}

