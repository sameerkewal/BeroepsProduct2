package sr.unasat.jdbc.crud.repositories;

import sr.unasat.jdbc.crud.entities.MedicalHistory;
import sr.unasat.jdbc.crud.entities.Persoon;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryRepository {

    private Connection connection;

    public MedicalHistoryRepository(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("De driver is geregistreerd!");
            String URL = "jdbc:mysql://localhost:3306/adres_boek";
            String USER = "root";
            String PASS = "sadk2005";
            connection = DriverManager.getConnection(URL, USER, PASS);



        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertIntoRecord(MedicalHistory history) {
        PersoonRepository pr = new PersoonRepository();
        Persoon test = pr.findOneRecord(history.getPersoon().getNaam());

        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "insert into Medical_History (Illness, dateOfDiagnosis, Allergies, persoonID) values(?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, history.getIllness());
            stmt.setDate(2, Date.valueOf(history.date()));

            stmt.setString(3, history.getAllergies());
            stmt.setInt(4, test.getId());
            result = stmt.executeUpdate();
            System.out.println("Resultset: " + result);

        } catch (SQLException e) {

        } finally {
            return result;
        }
    }


    public int deleteOneRecord(MedicalHistory history) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "DELETE FROM Medical_History where Medical_History.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, history.getId());
            result = stmt.executeUpdate();
            System.out.println("Deleted: " + history.getId());
        } catch (SQLException e) {

        } finally {
            return result;
        }
    }
        public int deleteOneRecord(int id){
            PreparedStatement stmt = null;
            int result = 0;
            try{
                String sql = "DELETE FROM Medical_History where Medical_History.id = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                result = stmt.executeUpdate();
                System.out.println("Deleted: " + id);
            } catch (SQLException e) {

            } finally {
                return result;
            }

    }

    public MedicalHistory findOneRecord(String illness, LocalDate date){
        MedicalHistory history = null;
        String test = null;
        PreparedStatement stmt = null;

        try{
            String sql = "SELECT mh.id, mh.Illness, mh.dateOfDiagnosis, mh.Allergies, mh.persoonID, p.id, p.naam\n" +
                    "FROM \n" +
                    "medical_history mh JOIN\n" +
                    "persoon p ON mh.persoonID = p.id\n" +
                    "WHERE Illness = ? and dateOfDiagnosis = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, illness);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            System.out.println("Resultset: " + rs);
            if(rs.next()) {
                int id = rs.getInt("id");
                String Illness = rs.getString("Illness");
                LocalDate dateOfDiagnosis = rs.getDate("dateOfDiagnosis").toLocalDate();
                String allergies = rs.getString("Allergies");
                int persoonID = rs.getInt("persoonID");

                int pid = rs.getInt("id");
                String persoonNaam = rs.getString("Naam");
                Persoon persoon = new Persoon(persoonID, persoonNaam);


                 history = new MedicalHistory(id, Illness, dateOfDiagnosis, allergies, persoon);
            }
            rs.close();

        } catch (SQLException e) {

        } finally {
            return history;
        }
    }
    public List<MedicalHistory> findAllRecords(){
        List<MedicalHistory> fullHistory = new ArrayList<>();
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql = "SELECT mh.id, mh.Illness, mh.dateOfDiagnosis, mh.Allergies, mh.persoonID, p.id, p.naam\n" +
                    "FROM \n" +
                    "medical_history mh JOIN\n" +
                    "persoon p ON mh.persoonID = p.id;";

                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        int id = rs.getInt("id");
                        String Illness = rs.getString("Illness");

                        LocalDate dateOfDiagnosis = rs.getDate("dateOfDiagnosis").toLocalDate();
                        //System.out.println(dateOfDiagnosis);
                        String allergies = rs.getString("Allergies");

                        int persoonID = rs.getInt("persoonID");



                        int pid = rs.getInt("id");
                        String persoonNaam = rs.getString("Naam");
                        Persoon persoon = new Persoon(persoonID, persoonNaam);



                        fullHistory.add(new MedicalHistory(id, Illness, dateOfDiagnosis, allergies, persoon));

                    }
                    rs.close();

        } catch (SQLException e) {

        } finally {

        }
        return fullHistory;

    }

    public MedicalHistory updateOneRecord(MedicalHistory history, String updatedIllness, String updatedAllergies){
        MedicalHistory test = this.findOneRecord(history.getIllness(), history.date());
        System.out.println(test);
        PreparedStatement stmt = null;
        int result = 0;
        try{
            String sql = "update Medical_History mh set Illness = ? , Allergies = ? where mh.id = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, updatedIllness);
            stmt.setString(2, updatedAllergies);
            stmt.setInt(3, test.getId());


            result = stmt.executeUpdate();
            System.out.println("Resultset: " + result);

        } catch (SQLException e) {

        } finally {
            return test;
        }
    }

}

