package applica.feneal.domain.model.core.lavoratori;

import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.setting.Fondo;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.feneal.domain.utils.Box;
import org.apache.commons.lang.StringUtils;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 05/04/2016.
 */
public class Lavoratore extends SecuredDomainEntity {

    public static final String MALE = "M";
    public static final String FEMALE = "F";

    //AATTENZIONE : questa proprietà è stata aggiunta solamente per
    //gestire la nuova ricerca utente multiterritoriale richiesta
    //dalla lombardia (03/04/2019)
    //poichè la facade non restituisce un dto ma restituisce direttamente lìoggetto di dominio alllora
    //per evitare di fare il dto, la conversione ecc
    //utilizzo una bruttissima scorciatoia (lo metto come proprietà transiente nell'oggetto di dominio)
    //da ggiustare quanto prima
    private transient String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String name;
    private String surname;
    private String sex;
    private String image;
    private String fiscalcode;
    private Date birthDate;
    private String nationality;
    private String birthProvince;
    private String birthPlace;   // comune di nascita
    private String livingProvince;
    private String livingCity;
    private String address;
    private String cap;
    private String phone;
    private String cellphone;
    private String mail;
    private String ce; // cassa edile
    private String ec; // ?????
    private Fondo fund;
    private String notes;

    private String ultimaComunicazione;

    //proprieà che concatenanao in modi differenti il nome e cognome per farorie ricerche globali
    //con stringa unica
    //esse vengono impostate prima del salvataggio nel servizio che aggiorna l'anagrafica...
    private String namesurname;
    private String surnamename;
    private String surnamenamebirth;

    public void setSurnamenamebirth(String surnamenamebirth) {
        this.surnamenamebirth = surnamenamebirth;
    }

    public String getUltimaComunicazione() {
        return ultimaComunicazione;
    }

    public void setUltimaComunicazione(String ultimaComunicazione) {
        this.ultimaComunicazione = ultimaComunicazione;
    }

    public Lavoratore(){}
    public Lavoratore(UtenteDbNazionale prototype){
        surname= prototype.getCognome();
        name = prototype.getNome();

        //non esistendo un modo per scrivere per riferimento due stringhe uso la classe box....
        if (StringUtils.isEmpty(name)){
            Box box = new Box();
            box.setValue(surname);
            box.setValue1(name);
            calculateSplitNameAndSurname(box);
            surname = box.getValue().toString();
            name = box.getValue1().toString();
        }


        sex= prototype.getSesso().equals("MASCHIO")?Lavoratore.MALE : Lavoratore.FEMALE;
        fiscalcode= prototype.getCodiceFiscale();
        birthDate= prototype.getDataNascita();
        nationality= prototype.getNomeNazione();
        birthProvince= prototype.getNomeProvincia();
        birthPlace= prototype.getNomeComune();
        livingProvince= prototype.getNomeProvinciaResidenza();
        livingCity= prototype.getNomeComuneResidenza();
        address= prototype.getIndirizzo();
        cap= prototype.getCap();
        cellphone= prototype.getTelefono();




    }



    public String calculateNormalizedCellphone(){

        String telNumber = cellphone;
        if (StringUtils.isEmpty(telNumber))
            telNumber = phone;

        if (StringUtils.isEmpty(telNumber))
            return "";
        if (StringUtils.isEmpty(telNumber.trim()))
            return "";


        //rimuovo il +39
        telNumber = telNumber.replace("+39", "");

        if (telNumber.length() < 9)
            return "";
        //rimuovo tutti i caratteri all'infouri dei nimeri
        String telefono = telNumber.replaceAll("[^\\d.]", "").replace(".","");
        if (telefono.length() > 10 && telefono.startsWith("39"))
            telefono = telefono.substring(2);

        if (telefono.length() > 10)return "";

        //rieseguo dopo la scrematura
        if (telefono.length() < 9)
            return "";
        String firstThreeDigits = telefono.substring(0,3);
        try{

            int d = Integer.parseInt(firstThreeDigits);
            if (d < 320)
                return "";

            if (d >= 400)
                return "";

            return telNumber;
        }catch (Exception e)
        {
            return "";
        }




    }

    private void calculateSplitNameAndSurname(Box box){

        String sur = box.getValue().toString().toUpperCase();
        //String nam = box.getValue1().toString().toUpperCase();

        //avvio l'algoritmo per la separazione del nome e del cognome
        //puo capitare nel database nazionale che sia valorizzato solo il cognome
        //allora bisognerà splittare il nome e il cognome in base algi spazi
        //per definizione viene scritto prima il nome e poi il cognome
        //pertanto eseguo una split del cognome sugli spazi e se il numero di elementi è 1 il nome sarà "(vuoto)"
        //altrimenti se il numero è 2 allora il secondo elemmento sarà il nome

        //per prima cosa rimuovo tutti gli eventuali spazi
        while (org.apache.commons.lang3.StringUtils.countMatches(sur, "  ") > 0){
            String reduced = sur.replace("  " , " ");
            sur = reduced;
        }
        //ottengo adesso la lista degli elementi del cognome nome...
        String[] p = sur.split(" ");

        if (p.length == 1){
            box.setValue1("(vuoto)");
            return;
        }

        if (p.length == 2){
            box.setValue(p[0]);
            box.setValue1(p[1]);
            return;
        }

        //se ci sono piu di due elementi controllo il primo elemento e se è diverso da
        List<String> dan = Arrays.asList("DE", "DI", "DEL", "DELLI" , "DELLA", "DALLA" , "LA", "LE", "LI", "LO", "EL");
        if (!dan.contains(p[0])){
            //allora metto il primo elemento nel cognome e tutti gli altri nel nome
            box.setValue(p[0]);
            box.setValue1(calculateNameFromArrayPosition(p, 1));
        }else{
            //metto i primi due nel cognome e i restanti nel nome
            box.setValue(p[0] + " " + p[1]);
            box.setValue1(calculateNameFromArrayPosition(p, 2));
        }

    }

    private String calculateNameFromArrayPosition(String[] elements, int position){
        String result = "";
        int i = 0;
        for (String element : elements) {
            if (i>=position)
                result = result + " " + element;
            i++;
        }

        return result.trim();
    }

    public Lavoratore(LiberoDbNazionale prototype) {
        name = prototype.getNome();
        surname= prototype.getCognome();
        sex= prototype.getSesso().equals("MASCHIO")?Lavoratore.MALE : Lavoratore.FEMALE;
        fiscalcode= prototype.getCodiceFiscale();
        birthDate= prototype.getDataNascita();
        nationality= prototype.getNomeNazione();
        birthProvince= prototype.getNomeProvincia();
        birthPlace= prototype.getNomeComune();
        livingProvince= prototype.getNomeProvinciaResidenza();
        livingCity= prototype.getNomeComuneResidenza();
        address= prototype.getIndirizzo();
        cap= prototype.getCap();
        cellphone= prototype.getTelefono();
    }

    @Override
    public String toString() {
        return String.format("%s %s", surname, name);
    }


    public String getSurnamenamebirth(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String d = "";
        if (birthDate != null){
            d = f.format(birthDate);
        }

        if (birthDate == null)
            return surnamename;

        return String.format("%s(%s)",surnamename, d);
    }

    public String getNamesurname() {
        return namesurname;
    }

    public void setNamesurname(String namesurname) {
        this.namesurname = namesurname;
    }

    public String getSurnamename() {
        return surnamename;
    }

    public void setSurnamename(String surnamename) {
        this.surnamename = surnamename;
    }

    public String getName() {
        if (name!=null)
            return name.toUpperCase();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        if (surname!=null)
            return surname.toUpperCase();
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFiscalcode() {
        if (fiscalcode!=null)
            return fiscalcode.toUpperCase();
        return fiscalcode;
    }

    public void setFiscalcode(String fiscalcode) {
        this.fiscalcode = fiscalcode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        if (nationality!=null)
            return nationality.toUpperCase();
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthProvince() {
        if (birthProvince!=null)
            return birthProvince.toUpperCase();
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getBirthPlace() {
        if (birthPlace!=null)
            return birthPlace.toUpperCase();
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getLivingProvince() {
        if (livingProvince!=null)
            return livingProvince.toUpperCase();
        return livingProvince;
    }

    public void setLivingProvince(String livingProvince) {
        this.livingProvince = livingProvince;
    }

    public String getLivingCity() {
        if (livingCity!=null)
            return livingCity.toUpperCase();
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getAddress() {
        if (address!=null)
            return address.toUpperCase();
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCe() {
        return ce;
    }

    public void setCe(String ce) {
        this.ce = ce;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public Fondo getFund() {
        return fund;
    }

    public void setFund(Fondo fund) {
        this.fund = fund;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
