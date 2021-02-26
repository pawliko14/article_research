import DBConnector.DBConnectorFATDB;
import DBConnector.DBConnectorGtt;
import Logic.Machine_Structure_detail_logic;
import Objetcs.MachineStructureWithParentProjects;
import Objetcs.Machine_Structure_Detail;

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
         1. step get data grom gtt database about 21050204
          */

        MachineStructureWithParentProjects MachineObj = new MachineStructureWithParentProjects("21050204" +
                "");

        System.out.println(MachineObj.toString());

    }






    }

