package Objetcs;

import DBConnector.DBConnectorFATDB;
import DBConnector.DBConnectorGtt;
import Logic.Machine_Structure_detail_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MachineStructureWithParentProjects {

    private Machine_Structure_detail_logic MachineArticles;
    private boolean isItASubproject;
    private String Project;
    private Connection connection_FAT;
    private String ParentProject;
    private String MainProject;

    public MachineStructureWithParentProjects( String project) throws SQLException {


        Project = project;
        MachineArticles = new Machine_Structure_detail_logic(project);
        connection_FAT = DBConnectorFATDB.dbConnector();
        ParentProject= null;
        MainProject= null;


        GetGeneralProject();
    }


    private void GetGeneralProject() throws SQLException {

        if(DetermineIfProjectHasAnyParent())
        {
            // repetition of function, remove it later

            String sql_GetArticles = "select OUDERPROJECT from bestelling b  where ORDERNUMMER  = ?  limit 1";
            PreparedStatement pstmnt = connection_FAT.prepareStatement(sql_GetArticles);
            pstmnt.setString(1,ParentProject.substring(2,ParentProject.length()));

            ResultSet rs=pstmnt.executeQuery();
            String TempParent = null;

            if(rs.next()) {
                TempParent =  rs.getString("OUDERPROJECT");
            }

            MainProject = TempParent.startsWith("7/") ? TempParent : "NONE!";



            rs.close();
            pstmnt.close();

        }
        else {
            MainProject = ParentProject;
        }
    }



    private boolean DetermineIfProjectHasAnyParent() throws SQLException {

        String sql_GetArticles = "select OUDERPROJECT from bestelling b  where ORDERNUMMER  = ?  limit 1";

        PreparedStatement pstmnt = connection_FAT.prepareStatement(sql_GetArticles);
        pstmnt.setString(1,Project);
        ResultSet rs=pstmnt.executeQuery();


        if(rs.next()) {
            ParentProject =  rs.getString("OUDERPROJECT");
        }

        rs.close();
        pstmnt.close();


        // for instance 2/21050204 has parent 2/210502 which has parent 7/202102004 ( last one)
       return  isItASubproject = ParentProject.startsWith("7/") ? false : true;
    }






    public Machine_Structure_detail_logic getMachineArtilcesLogic() { return MachineArticles; }
    public String getParentProject() { return ParentProject; }
    public String getMainProject() { return MainProject; }


    @Override
    public String toString() {
        return "MachineStructureWithParentProjects{" +
                "isItASubproject=" + isItASubproject +
                ", Project='" + Project + '\'' +
                ", ParentProject='" + ParentProject + '\'' +
                ", MainProject='" + MainProject + '\'' +
                '}';
    }
}









