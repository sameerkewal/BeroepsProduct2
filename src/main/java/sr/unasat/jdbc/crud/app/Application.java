package sr.unasat.jdbc.crud.app;

import sr.unasat.jdbc.crud.entities.ContactInformatie;
import sr.unasat.jdbc.crud.entities.Land;
import sr.unasat.jdbc.crud.entities.MedicalHistory;
import sr.unasat.jdbc.crud.entities.Persoon;
import sr.unasat.jdbc.crud.repositories.ContactInformatieRepository;
import sr.unasat.jdbc.crud.repositories.LandRepository;
import sr.unasat.jdbc.crud.repositories.MedicalHistoryRepository;
import sr.unasat.jdbc.crud.repositories.PersoonRepository;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Application {
    Date date1 = new Date(20, 5, 2002);

    public static void main(String[] args) throws ParseException {
        PersoonRepository persoonRepo = new PersoonRepository();


        // Persoon martha = new Persoon("Martha");
        // persoonRepo.insertOneRecord(martha);

        ContactInformatieRepository ciRepo = new ContactInformatieRepository();
        List<ContactInformatie> contactList = ciRepo.findAllRecords();
        for (ContactInformatie contact : contactList) {
            System.out.println(contact);
        }

        LandRepository landRepo = new LandRepository();

        Land guyana = new Land("Guyana");
        Land brazil = new Land("Brazil");
        landRepo.insertOneRecord(guyana);
        landRepo.insertOneRecord(brazil);

        List<Land> landList = landRepo.findAllRecords();
        for (Land land : landList) {
            System.out.println(land);
        }

/*        Persoon person = new Persoon(4);
        persoonRepo.deleteOneRecord(person);*/

        int pk = persoonRepo.insertOneRecord(new Persoon("Ellen"));

        //PersoonRepository persoonRepo = new PersoonRepository();
        List<Persoon> persoonList = persoonRepo.findAllRecords();
        for (Persoon persoon : persoonList) {
            System.out.println(persoon);

            ContactInformatieRepository ci = new ContactInformatieRepository();
//        ContactInformatie recordFound = ci.findOneRecord(1234, "Manjastraat 10");
//        System.out.println("single record: " + ci);
//
//        recordFound.setTelefoonNummer(8888);
//        recordFound.getPersoon().setId(4);
//        ci.updateOneRecord(recordFound);
//

            System.out.println(ci.findAllRecords());
        }



        Persoon yasmine = persoonRepo.findOneRecord("Yasmine");



        MedicalHistoryRepository mhr = new MedicalHistoryRepository();

        MedicalHistory vb = new MedicalHistory(1,"lightheadedness", LocalDate.of(2002,05,20), "milk", yasmine);
        //mhr.insertIntoRecord(vb);
        System.out.println(mhr.findOneRecord("lightheadedness", LocalDate.of(2002, 05, 20)));

        System.out.println(mhr.findAllRecords());






    }


}




