import DBConnector.DBConnectorFATDB;
import Excel.ExcelFIle;
import LeverancierOrdernummer.LeverancierOrdernummer;
import Objetcs.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class main {

    private static List<Machine_Structure_Detail> Articles_in_Structure;


    public static  void main(String[] args) throws Exception {



            /*
         1. step get data from gtt database for example : 21050204
            create an Object with articles from Strucutre(Gtt_database)
            assign for the machine (21050204)  its parent or main Project (7/XXXXXXXXX)
          */
        //    MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("21050204");


      // OLD LOGIC, faulty
        OLD_LOGIC_WRONG_ONE();





    }



    private static String checkForLeverDatumFromBestellig(String project) throws SQLException {
        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select LEVERDATUM, ORDERNUMMER from bestelling b2s \n" +
                "where ORDERNUMMER  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,project);

        ResultSet rs=pstmnt.executeQuery();
        String Besteldatum = null;

        if(rs.next() == true)
        {
            Besteldatum = rs.getString("BESTELDATUM");
        }


        pstmnt.close();
        rs.close();
        connection_fatdb.close();

        return Besteldatum;
    }

    private static void OLD_LOGIC_WRONG_ONE() throws IOException, SQLException {

        // MainLogic mainFunction = new MainLogic();


         /*
         1. step get data from gtt database for example : 21050204
            create an Object with articles from Strucutre(Gtt_database)
            assign for the machine (21050204)  its parent or main Project (7/XXXXXXXXX)
          */
        //    MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("21050204");
        MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("20052101");  // sprawdzanie podprojektu
        MachineObj.GetGeneralProject();
        String BestelDatum =  checkForBesteldatumFromBestelling(MachineObj.getProject());  //


        System.out.println("Stuctura: ");

        MachineObj.getMachineArtilcesLogic().GetArticlesFromMachine().forEach(System.out::println);
        int size = MachineObj.getMachineArtilcesLogic().GetArticlesFromMachine().size();






        List<StorenoteBestellingdetails_Stock> storenoteBestellingdetails_stocks= new ArrayList<>();
        for(int i = 0 ; i < size; i++) {

            List<StorenoteBestellingdetails_Stock> el = StorenotesBestellingStock(
                    MachineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(i), BestelDatum);
            storenoteBestellingdetails_stocks.addAll(el);
        }


        storenoteBestellingdetails_stocks.forEach(System.out::println);

        // eliminate NUlls

        storenoteBestellingdetails_stocks.removeIf(Objects::isNull);

        System.out.println("");
        System.out.println("elminate NUlls ");
        storenoteBestellingdetails_stocks.forEach(System.out::println);

        // eliminate where besteld = gelerved

        storenoteBestellingdetails_stocks
                .removeIf(x-> x.getBESTELD_storenotes().equals(x.getGELEVERD_storenotes())
                        && (!x.getBESTELD_storenotes().equals("0") && !x.getGELEVERD_storenotes().equals("0")));

        // filter nulls in bestelling - probably Lagers
//        for( int i = 0 ; i < storenoteBestellingdetails_stocks.size(); i++)
//        {
//            if(storenoteBestellingdetails_stocks.get(i).getORDERNUMMER_bestelling() == null){
//               String ORDERNUMMER_bestelling = "Lager";
//               storenoteBestellingdetails_stocks.get(i).setORDERNUMMER_bestelling(ORDERNUMMER_bestelling);
//            }
//        }


        // filter to find FEHLER, if stock value < neededValue then it should return Fehler(there should be more condition)
//        storenoteBestellingdetails_stocks.stream()
//                .filter(x->Double.parseDouble(x.getIlosc_stock())  < Double.parseDouble(x.getZapotrzebowanie_stock()))
//                .peek(x->x.setORDERNUMMER_bestelling("FEHLER"))
//                .collect(Collectors.toList());

        // filter to find Lagers, not all conditions
//        storenoteBestellingdetails_stocks.stream()
//                .filter(x->Double.parseDouble(x.getIlosc_stock())  > Double.parseDouble(x.getZapotrzebowanie_stock()))
//                .peek(x->x.setORDERNUMMER_bestelling("LAGER"))
//                .peek(x->x.setLeverancier_bestelling("LAGER"))
//                .collect(Collectors.toList());




        // changed lagers
        // storenoteBestellingdetails_stocks.forEach((System.out::println));

        // CReate an Excel file
        ExcelFIle file = new ExcelFIle();
        file.CreateFile2(storenoteBestellingdetails_stocks);

        System.out.println("");
        System.out.println("elminate besteld = gelerved");
        storenoteBestellingdetails_stocks.forEach(System.out::println);


    }

    private static List<StorenoteBestellingdetails_Stock> StorenotesBestellingStock(Machine_Structure_Detail machine_structure_detail, String bestelDatum) throws SQLException {

        int rowsCount  = checkIfSqlHasMoreThan1RowAsResult(machine_structure_detail.getCHILDARTICLE(), bestelDatum);

        List<StorenoteBestellingdetails_Stock> listOfElements = new ArrayList<>();

        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select \n" +
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
                ", b2.AFDELINGSEQ  as afdelingseq_bestelling\n" +
                ", st.Ilosc  as ilosc_stock\n" +
                ", st.naProdukcji  as naProdukcji_stock\n" +
                ", st.Zapotrzebowanie  as Zapotrzebowanie_stock\n" +
                "from storenotesdetail s \n" +
                "left join bestellingdetail b2 \n" +
                "\ton s.ARTIKELCODE  = b2.ARTIKELCODE \n" +
                "\tand s.BESTELDATUM  = b2.BESTELDATUM \n" +
                "left join stock st\n" +
                "on s.ARTIKELCODE  = st.kodArtykulu \n" +
                "Where s.ARTIKELCODE  = ? \n" +
                "and s.BESTELDATUM  = ? \n" +
                "order by b2.BESTELDATUM  \n" +
                "desc ";







        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,machine_structure_detail.getCHILDARTICLE());
        pstmnt.setString(2,bestelDatum);


        ResultSet rs=pstmnt.executeQuery();
        
        getRowcCOunt(rs);

        StorenoteBestellingdetails_Stock art = null;
        while(rs.next())
        {
            art = new StorenoteBestellingdetails_Stock(
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
                    rs.getString("BESTELDATUM_bestelling"),
                    rs.getString("afdelingseq_bestelling"),
                    rs.getString("ilosc_stock"),
                    rs.getString("naProdukcji_stock"),
                    rs.getString("Zapotrzebowanie_stock")
                    );

            listOfElements.add(art);
        }



        pstmnt.close();
        rs.close();
        connection_fatdb.close();


    return listOfElements;
    }

    private static int checkIfSqlHasMoreThan1RowAsResult(String childarticle, String bestelDatum) throws SQLException {

        int count_resultSet = 0;
        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String checker = "select \n" +
                "count(*) as count\n" +
                "from storenotesdetail s \n" +
                "left join bestellingdetail b2 \n" +
                "\ton s.ARTIKELCODE  = b2.ARTIKELCODE \n" +
                "\tand s.BESTELDATUM  = b2.BESTELDATUM \n" +
                "left join stock st\n" +
                "on s.ARTIKELCODE  = st.kodArtykulu \n" +
                "Where s.ARTIKELCODE  = ? \n" +
                "and s.BESTELDATUM  = ? ";

        PreparedStatement pstmn_checker = connection_fatdb.prepareStatement(checker);
        pstmn_checker.setString(1,childarticle);
        pstmn_checker.setString(2,bestelDatum);


        ResultSet rs=pstmn_checker.executeQuery();
        if(rs.first())
        {
            count_resultSet= rs.getInt("count");
        }

        pstmn_checker.close();
        rs.close();
        connection_fatdb.close();


        return count_resultSet;
    }

    private static void getRowcCOunt(ResultSet resultSet) {



    }

    private static void WrongALgorithm_leaveit(MachineStructureWithParentProjects machineObj, int size) throws SQLException {


        //   LeverancierOrderNUmmer(MachineObj,size);
        List<LeverancierOrdernummer> leverancierOrdernummers = LeverancierOrderNUmberWIthoutNUlls(machineObj, size);

        // foreach of the obkject find Bestled gElerved Bostdeh and CFStock
        for(LeverancierOrdernummer l : leverancierOrdernummers)
            FindArticleStock(l);

        leverancierOrdernummers.forEach(System.out::println);

        // find articles that BESTELD <> GELERVED

//        System.out.println("removed from lise articles where Besteld = Gelerved");
//        leverancierOrdernummers.stream()
//                    .filter(x-> !x.getBesteld().equals(x.getGelerved()))
//                    .collect(Collectors.toList()).forEach(System.out::println);





        /*
         2. Articles Research  check each article which isnt 'F' for its stock, and orderds
         */

        //   StorenotesBestellingDetails storenoteBestellingDetail_obj =   ArticleAnalyze(MachineObj, 0);

        /*
         2.1 Article Researchh with base from ArticleAnalyze
         */


        //    System.out.println("PASSING VALUE :" + storenoteBestellingDetail_obj.toString());

        //  ArticleAnalyze_WithPreviousRecord(MachineObj, 3, storenoteBestellingDetail_obj);

        System.out.println("\n\n");



    }

    private static void FindArticleStock(LeverancierOrdernummer leverancierOrder) throws SQLException {

        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select BESTELD,GELEVERD ,BOSTDEH ,CFSTOCK  from storenotesdetail s \n" +
                "where ARTIKELCODE  = ?\n" +
                "and Leverancier = ?\n" +
                "and ORDERNUMMER  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,leverancierOrder.getArticle());
        pstmnt.setString(2,leverancierOrder.getLeverancier());
        pstmnt.setString(3,leverancierOrder.getOrdernummer());

        ResultSet rs=pstmnt.executeQuery();

        String BESTELD= null;
        String GELEVERD = null;
        String BOSTDEH= null;
        String CFSTOCK = null;
        while(rs.next())
        {
            BESTELD  = rs.getString("BESTELD");
            GELEVERD = rs.getString("GELEVERD");
            BOSTDEH  = rs.getString("BOSTDEH");
            CFSTOCK = rs.getString("CFSTOCK");
        }

        leverancierOrder.setBesteld(BESTELD);
        leverancierOrder.setGelerved(GELEVERD);
        leverancierOrder.setBostdeh(BOSTDEH);
        leverancierOrder.setCFstock(CFSTOCK);

        pstmnt.close();
        rs.close();
        connection_fatdb.close();


    }

    /**
     *
     * same method as LeverancierOrderNUmmer
     * here results is returning without nulls
     *
     * @param machineObj
     * @param size
     */
    private static List<LeverancierOrdernummer> LeverancierOrderNUmberWIthoutNUlls(MachineStructureWithParentProjects machineObj, int size) throws SQLException {

        List<LeverancierOrdernummer> l = new ArrayList<>();

        // 1. check for BESTELDATUM from bestelling
        String BestelDatum =  checkForBesteldatumFromBestelling(machineObj.getProject());  // 21050204


        for(int i = 0 ;  i < size; i++) {
            LeverancierOrdernummer  leverancierOrdernummer=  CheckForLeverancierOrdernamer_returner(machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(i).getCHILDARTICLE(), BestelDatum, machineObj.getProject());
            l.add(leverancierOrdernummer);
        }


        System.out.println("removed nulls");
        l.removeIf(x->x == null);
     //   l.forEach(System.out::println);


        return l;
    }

    /**
     *  same function as CheckForLeverancierOrdernamer this case its returning values
     *  instead of just printing
     *
     * @param article
     * @param bestelDatum
     */
    private static LeverancierOrdernummer CheckForLeverancierOrdernamer_returner(String article, String bestelDatum,String MachineProject_Number) throws SQLException {

        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select Leverancier ,ORDERNUMMER,AFDELING,AFDELINGSEQ  from storenotesdetail s2 \n" +
                "where ARTIKELCODE  = ?\n" +
                "and BESTELDATUM  = ?\n" +
                "and AFDELINGSEQ  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,article);
        pstmnt.setString(2,bestelDatum);
        pstmnt.setString(3,MachineProject_Number);

        ResultSet rs=pstmnt.executeQuery();

        String leverancier= null;
        String Ordernummer = null;
        String AFDELING= null;
        String AFDELINGSEQ = null;

        while(rs.next())
        {
            leverancier  = rs.getString("Leverancier");
            Ordernummer = rs.getString("ORDERNUMMER");
            AFDELING  = rs.getString("AFDELING");
            AFDELINGSEQ = rs.getString("AFDELINGSEQ");
        }



        pstmnt.close();
        rs.close();
        connection_fatdb.close();
        
        
        LeverancierOrdernummer leverancierOrdernummer = null;
        if( leverancier != null) {
           leverancierOrdernummer =   new LeverancierOrdernummer(article,leverancier,Ordernummer);

           // add additional fields ( should be nulls)
            leverancierOrdernummer.setAfdeling(AFDELING);
            leverancierOrdernummer.setAfdelingseq(AFDELINGSEQ);
        }


        
        return leverancierOrdernummer;
    }


    /**
     *          check for leverancier/ordernummer based on besteldaum
     *          retrives all values leverancier/ordernummer values for each of article from structure
     *          CAREFULL,
     *          it will fetch also nulls
     * @param machineObj
     * @param size
     * @throws SQLException
     */
    public static void LeverancierOrderNUmmer(MachineStructureWithParentProjects machineObj,int size) throws SQLException {


        // 1. check for BESTELDATUM from bestelling
        String BestelDatum =  checkForBesteldatumFromBestelling("21050204");
        // 2. check for all leverancier/ordernamer based on Besteldatum for this machine(Submachine)

        for(int i = 0 ;  i < size; i++) {
            System.out.println(i);
            CheckForLeverancierOrdernamer(machineObj.getMachineArtilcesLogic().GetArticlesFromMachine().get(i).getCHILDARTICLE(), BestelDatum);
        }

    }

    private static void CheckForLeverancierOrdernamer(String s, String bestelDatum) throws SQLException {


        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select * from storenotesdetail s2 \n" +
                "where ARTIKELCODE = ?\n" +
                "and BESTELDATUM  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,s);
        pstmnt.setString(2,bestelDatum);

        ResultSet rs=pstmnt.executeQuery();
        String Besteldatum = null;

        String leverancier= null;
        String Ordernummer = null;
        while(rs.next())
        {
            leverancier  = rs.getString("Leverancier");
            Ordernummer = rs.getString("ORDERNUMMER");
        }


        System.out.println("article : " + s + " , leverancier -> " + leverancier + " , Ordernummer ->" + Ordernummer );


        pstmnt.close();
        rs.close();
        connection_fatdb.close();




    }

    private static String checkForBesteldatumFromBestelling(String s) throws SQLException {


        Connection connection_fatdb =DBConnectorFATDB.dbConnector();

        String sql_GetArticles = "select BESTELDATUM, ORDERNUMMER from bestelling b2s \n" +
                "where ORDERNUMMER  = ? ";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,s);

        ResultSet rs=pstmnt.executeQuery();
        String Besteldatum = null;

        if(rs.next() == true)
        {
             Besteldatum = rs.getString("BESTELDATUM");
        }

        pstmnt.close();
        rs.close();
        connection_fatdb.close();

        return Besteldatum;
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

