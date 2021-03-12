import DBConnector.DBConnectorFATDB;
import Objetcs.StorenoteBestellingdetails_Stock;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class mainTest {



    @Test
    public void SampleTestCase()
    {
        int x = 10;
        int result = x*x;
            assertEquals(100, result);

    }


    @Test
    public void SHouldNotReturnNullValue() throws SQLException {

        String article = "NG10";
        String bestelDatum = "2021-02-08";

        Connection connection_fatdb = DBConnectorFATDB.dbConnector();

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
                ", st.Ilosc as Ilosc \n" +
                ", st.naProdukcji as naProdukcji \n" +
                ", st.Zapotrzebowanie as Zapotrzenowanie\n" +
                "from storenotesdetail s \n" +
                "left join bestellingdetail b2 \n" +
                "on s.ARTIKELCODE  = b2.ARTIKELCODE \n" +
                "left join stock st\n" +
                "on s.ARTIKELCODE  = st.kodArtykulu \n" +
                "Where s.ARTIKELCODE  = ? \n" +
                "and s.BESTELDATUM  =  ? \n" +
                "order by b2.BESTELDATUM  desc \n" +
                "limit 1";

        PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,article);
        pstmnt.setString(2,bestelDatum);


        ResultSet rs=pstmnt.executeQuery();

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
                    rs.getString("Ilosc"),
                    rs.getString("naProdukcji"),
                    rs.getString("Zapotrzenowanie")
            );
        }



        pstmnt.close();
        rs.close();
        connection_fatdb.close();


        assertNotEquals(null, art);


    }



@Test
public void SHouldReturn12Values() throws SQLException {

    String article = "NG10";
    String bestelDatum = "2021-02-08";

    List<StorenoteBestellingdetails_Stock> temporary_list = new ArrayList<>();


    Connection connection_fatdb = DBConnectorFATDB.dbConnector();

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
            ", st.Ilosc as Ilosc \n" +
            ", st.naProdukcji as naProdukcji \n" +
            ", st.Zapotrzebowanie as Zapotrzenowanie\n" +
            "from storenotesdetail s \n" +
            "left join bestellingdetail b2 \n" +
            "on s.ARTIKELCODE  = b2.ARTIKELCODE \n" +
            "left join stock st\n" +
            "on s.ARTIKELCODE  = st.kodArtykulu \n" +
            "Where s.ARTIKELCODE  = ? \n" +
            "and s.BESTELDATUM  =  ? \n" +
            "order by b2.BESTELDATUM  desc \n" +
            "";

    PreparedStatement pstmnt = connection_fatdb.prepareStatement(sql_GetArticles);
    pstmnt.setString(1,article);
    pstmnt.setString(2,bestelDatum);


    ResultSet rs=pstmnt.executeQuery();

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
                rs.getString("Ilosc"),
                rs.getString("naProdukcji"),
                rs.getString("Zapotrzenowanie")
        );

        temporary_list.add(art);
    }



    pstmnt.close();
    rs.close();
    connection_fatdb.close();


        assertEquals(12, temporary_list.size());


}


}