package applica.feneal.domain.model.core;

import java.util.Calendar;
import java.util.Date;

public class Semester {

    private Date start;
    private Date end;
    private SemesterNumber semesterNumber = SemesterNumber.First;
    private int year;


    public int getSemesterValue(){
        if (semesterNumber == SemesterNumber.First)
            return 1;
        return  2;
    }

    public boolean contains(Date d){

        if (start.getTime() <= d.getTime() && end.getTime() >= d.getTime())
            return  true;
        return false;

    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static Semester createSemester(Date date){
        return new Semester(date);
    }

    public static Semester createSemester(){
        return new Semester(new Date());
    }

    private Semester(Date d){
        calculateSemesterAndYear(d);

        calculatePeriod();
    }

    private void calculateSemesterAndYear(Date d) {
        Calendar.getInstance().setTime(d);
        int syear = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);

        //se il mese è gennao(0), febbraio (1) e marzo(2)
        //o
        if (month < 3 ){
            semesterNumber = SemesterNumber.First;
            year = syear;
            //l'anno rimane invariato

        }else if (month > 8){
            semesterNumber = SemesterNumber.First;
            year = syear + 1;
            //stiamo parlando del primo semestre dell'anno successivo
            //a quello attuale
        }
        else{
            semesterNumber = SemesterNumber.Second;
            year = syear;
        }
    }

    private void calculatePeriod() {
        Calendar c = Calendar.getInstance();
        if (semesterNumber == SemesterNumber.First){


            c.set(year -1, Calendar.OCTOBER, 1, 0, 0, 0);
            start = c.getTime();

            c.set(year, Calendar.MARCH, 31, 0, 0,0);
            end = c.getTime();
            return;

        }
        c.set(year, Calendar.APRIL, 1, 0, 0, 0);
        start = c.getTime();

        c.set(year, Calendar.SEPTEMBER, 30, 0, 0,0);
        end = c.getTime();

    }


}

enum SemesterNumber{
    First,
    Second
}
