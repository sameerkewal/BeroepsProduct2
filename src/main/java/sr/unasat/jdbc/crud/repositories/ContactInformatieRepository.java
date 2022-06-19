package sr.unasat.jdbc.crud.repositories;

import sr.unasat.jdbc.crud.entities.ContactInformatie;
import sr.unasat.jdbc.crud.entities.Land;
import sr.unasat.jdbc.crud.entities.Persoon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactInformatieRepository {
    private Connection connection;

    public ContactInformatieRepository() {
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

    public List<ContactInformatie> findAllRecords() {
        List<ContactInformatie> contactList = new ArrayList<ContactInformatie>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "select ci.id, ci.adres, ci.telefoon_nummer , p.id pid, p.naam pnaam, l.id lid, l.naam land_naam" +
                    " from contact_informatie ci" +
                    " join persoon p" +
                    " on p.id = ci.persoon_id" +
                    " join land l  " +
                    " on l.id = ci.land_id";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("resultset: " + rs);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String adres = rs.getString("adres");
                int telefoonNummer = rs.getInt("telefoon_nummer");

                int persoonId = rs.getInt("pid");
                String persoonNaam = rs.getString("pnaam");
                Persoon persoon = new Persoon(persoonId, persoonNaam);

                int landId = rs.getInt("lid");
                String landNaam = rs.getString("land_naam");
                Land land = new Land(landId, landNaam);

                contactList.add(new ContactInformatie(id, adres, telefoonNummer, persoon, land));
                //  persoonList.add(new Persoon(rs.getInt("id"), rs.getString("naam")));
            }
            rs.close();


        } catch (SQLException e) {

        } finally {

        }
        return contactList;
    }

    public ContactInformatie findOneRecord(int telNum, String ciAdres) {
        ContactInformatie contactInformatie = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select ci.id, ci.adres, ci.telefoon_nummer , p.id pid, p.naam pnaam, l.id lid, l.naam land_naam" +
                    " from contact_informatie ci" +
                    " join persoon p" +
                    " on p.id = ci.persoon_id" +
                    " join land l" +
                    " on l.id = ci.land_id where ci.telefoon_nummer = ? or ci.adres = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, telNum);
            stmt.setString(2, ciAdres);
            ResultSet rs = stmt.executeQuery();
            System.out.println("resultset: " + rs);
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String adres = rs.getString("adres");
                int telefoonNummer = rs.getInt("telefoon_nummer");

                int persoonId = rs.getInt("pid");
                String persoonNaam = rs.getString("pnaam");
                Persoon persoon = new Persoon(persoonId, persoonNaam);

                int landId = rs.getInt("lid");
                String landNaam = rs.getString("land_naam");
                Land land = new Land(landId, landNaam);

                contactInformatie = new ContactInformatie(id, adres, telefoonNummer, persoon, land);
            }
            rs.close();


        } catch (SQLException e) {

        } finally {

        }
        return contactInformatie;
    }

    public int updateOneRecord(ContactInformatie contact) {
        PreparedStatement stmt = null;
        int result = 0;
        try {
            String sql = "update contact_informatie ci set ci.telefoon_nummer = ?, ci.persoon_id = ? where ci.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, contact.getTelefoonNummer());
            stmt.setInt(2, contact.getPersoon().getId());
            stmt.setInt(3, contact.getId());
            result = stmt.executeUpdate();
            System.out.println("resultset: " + result);

        } catch (SQLException e) {

        } finally {

        }
        return result;
    }

}