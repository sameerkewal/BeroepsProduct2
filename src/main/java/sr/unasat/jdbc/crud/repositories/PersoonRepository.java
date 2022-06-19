package sr.unasat.jdbc.crud.repositories;

import sr.unasat.jdbc.crud.entities.Persoon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersoonRepository {
    private Connection connection;

    public PersoonRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("De driver is geregistreerd!");

            String URL = "jdbc:mysql://localhost:3306/adres_boek";
            String USER = "root";
            String PASS = "sadk2005";
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println(connection);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Persoon> findAllRecords() {
        List<Persoon> persoonList = new ArrayList<Persoon>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "select * from persoon";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("resultset: " + rs);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String naam = rs.getString("naam");
            /*    //Display values
               System.out.print("ID: " + id);
               System.out.print(", Age: " + naam);*/
                persoonList.add(new Persoon(id, naam));
                //  persoonList.add(new Persoon(rs.getInt("id"), rs.getString("naam")));
            }
            rs.close();


        } catch (SQLException e) {

        } finally {

        }
        return persoonList;
    }

    public int insertOneRecord(Persoon persoon) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "insert into persoon (naam) values(?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, persoon.getNaam());
            result = stmt.executeUpdate();
            System.out.println("resultset: " + result);

        } catch (SQLException e) {

        } finally {

        }
        return result;
    }

    public int deleteOneRecord(Persoon persoon) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "DELETE FROM persoon WHERE persoon.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, persoon.getId());
            result = stmt.executeUpdate();
            System.out.println("deleted: " + persoon.getId());

        } catch (SQLException e) {

        } finally {

        }
        return result;
    }

    public Persoon findOneRecord(String name) {
        Persoon persoon = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select * FROM adres_boek.persoon WHERE naam = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            System.out.println("resultset: " + rs);
            if (rs.next()) {

                int foundID = rs.getInt("id");
                String naam = rs.getString("naam");

                persoon = new Persoon(foundID, naam);

            }
            rs.close();
        } catch(SQLException e){

            } finally{
                return persoon;
            }
        }

        }



