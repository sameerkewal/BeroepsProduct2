package sr.unasat.jdbc.crud.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MedicalHistory {
    private String illness;
    private Integer id;


    private LocalDate dateOfDiagnosis;
    private String allergies;
    private Persoon persoon;

    public MedicalHistory(int id, String illness, LocalDate dateOfDiagnosis, String allergies, Persoon persoon) {
        this.illness = illness;
        this.id = id;
        this.dateOfDiagnosis = dateOfDiagnosis;
        this.allergies = allergies;
        this.persoon = persoon;

    }

    public String getIllness() {
        return this.illness;
    }

    public Integer getId() {
        return id;
    }



    public java.sql.Date getDateOfDiagnosis(){
        DateFormat formatter = new SimpleDateFormat("YYY-MM-dd");
        Date myDate = null;
        try {
            myDate = formatter.parse(String.valueOf(this.dateOfDiagnosis));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        return sqlDate;
    }

    public LocalDate date(){
        return this.dateOfDiagnosis;
    }

    public String getAllergies() {
        return allergies;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public String toString(){
        return  "Medical History{" +
                " id = " + id +
                ", illness = " + this.illness +
                ", Date of diagnosis = " + this.dateOfDiagnosis.toString() +
                ", Allergies = " + this.allergies +
                ", Persoon = " + persoon +
                 "}";
    }
}