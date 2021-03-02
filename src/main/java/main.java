import DBConnector.DBConnectorFATDB;
import DBConnector.DBConnectorGtt;
import Logic.Machine_Structure_detail_logic;
import Logic.MainLogic;
import Objetcs.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class main {

    private static List<Machine_Structure_Detail> Articles_in_Structure;


    public static  void main(String[] args) throws Exception {



       // MainLogic mainFunction = new MainLogic();


         /*
         1. step get data from gtt database for example : 21050204
            create an Object with articles from Strucutre(Gtt_database)
            assign for the machine (21050204)  its parent or main Project (7/XXXXXXXXX)
          */
        MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("21050204");
        MachineObj.GetGeneralProject();
     //    MachineObj.getMachineArtilcesLogic().GetArticlesFromMachine().forEach(System.out::println);
     //   System.out.println(MachineObj.toString());

        /*
         2. Articles Research  check each article which isnt 'F' for its stock, and orderds
         */

        StorenotesBestellingDetails storenoteBestellingDetail_obj =   ArticleAnalyze(MachineObj, 0);

        /*
         2.1 Article Researchh with base from ArticleAnalyze
         */


    //    System.out.println("PASSING VALUE :" + storenoteBestellingDetail_obj.toString());

       ArticleAnalyze_WithPreviousRecord(MachineObj, 3, storenoteBestellingDetail_obj);

       System.out.println("\n\n");




    }

    private static void ArticleAnalyze_WithPreviousRecord(MachineStructureWithParentProjects machineObj,int index_binded_toMachineObj, StorenotesBestellingDetails storenoteBestellingDetail_obj_previousOne) throws Exception {

        //1. check if current one parentArticle == previousOne CHildArticle

        String currentParentEqualsPreviousChild = machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(index_binded_toMachineObj).getPARENTARTICLE();
        int level = machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(index_binded_toMachineObj).getLevel();


        // check current one
        System.out.println(machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(index_binded_toMachineObj).toString());

      //2. search for parent Article
       boolean IsThereParentArticle =  machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().stream()
                .filter(x -> x.getCHILDARTICLE().equals(currentParentEqualsPreviousChild))
                .filter(x -> x.getLevel() == level -1)
                .findFirst()
                .isPresent();

            // function below must be implementd, otherwise there is no possibility to track previous article that match following conditions
            //2.1. get structure details from this article
               System.out.println("Option that must be implemented" +     machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().stream()
                    .filter(x -> x.getCHILDARTICLE().equals(currentParentEqualsPreviousChild))
                    .filter(x -> x.getLevel() == level -1)
                    .findFirst() );


        //3. if 2. return parent article there should be analuze in StorenotesD_BestellingDetails with Parent Article as Afdeling/Afdelingseq

        if(IsThereParentArticle) {

            ArticleAnalyze_2(machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(index_binded_toMachineObj), storenoteBestellingDetail_obj_previousOne);
        }

        else {
            System.out.println("there is no parent article for article : " + machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(index_binded_toMachineObj).getCHILDARTICLE());
        }







    }

    private static void ArticleAnalyze_2(Machine_Structure_Detail machine_structure_detail, StorenotesBestellingDetails storenoteBestellingDetail_obj_previousOne) throws Exception {

        //CODE REUSAGE!!!
        // step (1). check for the Stock, not used here ( temporary)
            //    checkForTheStock( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(temporary_index).getCHILDARTICLE());



         System.out.println("second one : ");

        StorenotesBestellingDetails storenotesBestellingDetails = checkStorenotesBestellingDetailsArticle(machine_structure_detail.getCHILDARTICLE(),
                storenoteBestellingDetail_obj_previousOne.getORDERNUMMER_bestelling());

    }


    private static StorenotesBestellingDetails ArticleAnalyze(MachineStructureWithParentProjects machineObj, int temporary_index) throws SQLException {

        System.out.println("*****");
        System.out.println("ARTICLE ANALYZE");
        System.out.println("*****");
        System.out.println("");

        System.out.println("MACHINE  : " + machineObj.toString());

        System.out.println(machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(temporary_index).toString());


        machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(temporary_index).getCHILDARTICLE();

        // step (1). check for the Stock,
        checkForTheStock( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(temporary_index).getCHILDARTICLE());

        // step(2). based on stock condition( checking in future), now its time to check for storenotesdetail
//        checkForStorenotesDetail( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(0).getCHILDARTICLE(),
//                                  machineObj.getProject()
//        );

        // STEP (2 v2) - bas on stock condtiion, check for bestellingdetail and storenotesdetail simutanuesly;

         return checkStorenotesBestellingDetailsArticle( machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(temporary_index).getCHILDARTICLE(),
                machineObj.getProject()
        );


    }

    private static StorenotesBestellingDetails checkStorenotesBestellingDetailsArticle(String childarticle, String ProjectNumber) throws SQLException {

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


        System.out.println("StorenotesDetail_BestellingDetail article " + storenoteDetailsBestellingDetails);


        pstmnt.close();
        rs.close();
        connection_fatdb.close();


        return storenoteDetailsBestellingDetails;
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

