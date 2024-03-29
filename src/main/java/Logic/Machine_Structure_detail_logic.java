package Logic;

import DBConnector.DBConnectorGtt;
import Objetcs.Machine_Structure_Detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Machine_Structure_detail_logic {

    private List<Machine_Structure_Detail> Articles_in_Structure;
    private String Machine_Number;
    private Connection connection_gtt;

    public Machine_Structure_detail_logic( String machine_Number) throws SQLException {
        Articles_in_Structure = new ArrayList<Machine_Structure_Detail>();
        Machine_Number = machine_Number;
        connection_gtt = DBConnectorGtt.dbConnector();

        getArticlesFromMachine();
    }




    public List<Machine_Structure_Detail> GetArticlesFromMachine () throws SQLException {


         return Remove_F_Articles_from_list();
    }


    private List<Machine_Structure_Detail> Remove_F_Articles_from_list()
    {
        List<Machine_Structure_Detail> clone  =  new ArrayList<>(Articles_in_Structure);
        clone.removeIf(x -> x.getType().equals("F"));

        return clone;
    }


    /**
     *
     * CAREFULL!!!
     * function has been changed, group by clausle has been added,
     * previously this statement returned all data for specific machine
     * now it return no duplicated rows based on 'CHILDARTICLE'
     *
     * 09.03.2021
     *
     * @return
     * @throws SQLException
     */
    private List<Machine_Structure_Detail> getArticlesFromMachine() throws SQLException {

//         previous sql, without group by
//        String sql_GetArticles = "select ID,MACHINENUMBER , PARENTARTICLE , CHILDARTICLE ,QUANTITY , `TYPE` ,`LEVEL` \n" +
//                "from machine_structure_details msd  where MACHINENUMBER  = ? ";

        String sql_GetArticles = "select  ID,MACHINENUMBER ,PARENTARTICLE ,  CHILDARTICLE  ,QUANTITY ,`TYPE` , `LEVEL`  from machine_structure_details msd \n" +
                "where MACHINENUMBER  =  ? \n" +
                "GROUP BY CHILDARTICLE \n";

        PreparedStatement pstmnt = connection_gtt.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,Machine_Number);

        ResultSet rs=pstmnt.executeQuery();

        while(rs.next())
        {
            Machine_Structure_Detail obj = new Machine_Structure_Detail(rs.getString("ID"),
                                                                        rs.getString("MACHINENUMBER"),
                                                                        rs.getString("PARENTARTICLE"),
                                                                        rs.getString("CHILDARTICLE"),
                                                                        rs.getString("QUANTITY"),
                                                                        rs.getString("TYPE"),
                                                                        rs.getInt("LEVEL"));

            Articles_in_Structure.add(obj);
        }



        return Articles_in_Structure;
    }


}
