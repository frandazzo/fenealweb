package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.admin.viewmodel.UiLavoratoreTimeLineItem;
import applica.feneal.admin.viewmodel.UiLavoratoreTimelineYearGroup;
import applica.feneal.domain.model.dbnazionale.DelegaNazionale;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;

import java.util.List;

/**
 * Created by fgran on 06/04/2016.
 */
public class UiCompleteLavoratoreSummary {


    private List<DelegaNazionale> deleghe;
    private List<LiberoDbNazionale> iscrizioniAltroSindacato;
    private List<Iscrizione> iscrizioni;
    private List<UiLavoratoreTimelineYearGroup> timelineList;


    public UiLavoratoreAnagraficaSummary getData() {
        return data;
    }

    public void setData(UiLavoratoreAnagraficaSummary data) {
        this.data = data;
    }

    public UiIscrittoAnnoInCorso getIscrittoData() {
        return iscrittoData;
    }

    public void setIscrittoData(UiIscrittoAnnoInCorso iscrittoData) {
        this.iscrittoData = iscrittoData;
    }

    public UiPrintedTessera getTesseraData() {
        return tesseraData;
    }

    public void setTesseraData(UiPrintedTessera tesseraData) {
        this.tesseraData = tesseraData;
    }

    public List<UiPrintedTessera> getOtherTessereData() {
        return otherTessereData;
    }

    public void setOtherTessereData(List<UiPrintedTessera> otherTessereData) {
        this.otherTessereData = otherTessereData;
    }

    private UiLavoratoreAnagraficaSummary data;

    private UiIscrittoAnnoInCorso iscrittoData;

    private UiPrintedTessera tesseraData;

    private List<UiPrintedTessera> otherTessereData;


    public void setDeleghe(List<DelegaNazionale> deleghe) {
        this.deleghe = deleghe;
    }

    public List<DelegaNazionale> getDeleghe() {
        return deleghe;
    }

    public void setIscrizioniAltroSindacato(List<LiberoDbNazionale> iscrizioniAltroSindacato) {
        this.iscrizioniAltroSindacato = iscrizioniAltroSindacato;
    }

    public List<LiberoDbNazionale> getIscrizioniAltroSindacato() {
        return iscrizioniAltroSindacato;
    }

    public void setIscrizioni(List<Iscrizione> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }

    public List<Iscrizione> getIscrizioni() {
        return iscrizioni;
    }


    public void setTimelineList(List<UiLavoratoreTimelineYearGroup> timelineList) {
        this.timelineList = timelineList;
    }

    public List<UiLavoratoreTimelineYearGroup> getTimelineList() {
        return timelineList;
    }
}
