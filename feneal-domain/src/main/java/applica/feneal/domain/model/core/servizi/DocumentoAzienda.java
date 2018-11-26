package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.domain.model.setting.TipoDocumento;
import applica.framework.AEntity;

import java.util.Date;

/**
 * Created by angelo on 03/05/16.
 */
public class DocumentoAzienda extends AEntity {


    private Azienda azienda;
    private Date data;
    private TipoDocumento tipo;

    private String notes;

    private String allegato1;
    private String allegato2;
    private String allegato3;
    private String allegato4;
    private String allegato5;
    private String allegato6;
    private String nomeallegato1;
    private String nomeallegato2;
    private String nomeallegato3;
    private String nomeallegato4;
    private String nomeallegato5;
    private String nomeallegato6;

    private Province province;
    private Collaboratore collaboratore;


    public String getNomeallegato1() {
        return nomeallegato1;
    }

    public void setNomeallegato1(String nomeallegato1) {
        this.nomeallegato1 = nomeallegato1;
    }

    public String getNomeallegato2() {
        return nomeallegato2;
    }

    public void setNomeallegato2(String nomeallegato2) {
        this.nomeallegato2 = nomeallegato2;
    }

    public String getNomeallegato3() {
        return nomeallegato3;
    }

    public void setNomeallegato3(String nomeallegato3) {
        this.nomeallegato3 = nomeallegato3;
    }

    public String getNomeallegato4() {
        return nomeallegato4;
    }

    public void setNomeallegato4(String nomeallegato4) {
        this.nomeallegato4 = nomeallegato4;
    }

    public String getNomeallegato5() {
        return nomeallegato5;
    }

    public void setNomeallegato5(String nomeallegato5) {
        this.nomeallegato5 = nomeallegato5;
    }

    public String getNomeallegato6() {
        return nomeallegato6;
    }

    public void setNomeallegato6(String nomeallegato6) {
        this.nomeallegato6 = nomeallegato6;
    }

    public Azienda getAzienda() {
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        this.azienda = azienda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAllegato1() {
        return allegato1;
    }

    public void setAllegato1(String allegato1) {
        this.allegato1 = allegato1;
    }

    public String getAllegato2() {
        return allegato2;
    }

    public void setAllegato2(String allegato2) {
        this.allegato2 = allegato2;
    }

    public String getAllegato3() {
        return allegato3;
    }

    public void setAllegato3(String allegato3) {
        this.allegato3 = allegato3;
    }

    public String getAllegato4() {
        return allegato4;
    }

    public void setAllegato4(String allegato4) {
        this.allegato4 = allegato4;
    }

    public String getAllegato5() {
        return allegato5;
    }

    public void setAllegato5(String allegato5) {
        this.allegato5 = allegato5;
    }

    public String getAllegato6() {
        return allegato6;
    }

    public void setAllegato6(String allegato6) {
        this.allegato6 = allegato6;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Collaboratore getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(Collaboratore collaboratore) {
        this.collaboratore = collaboratore;
    }
}
