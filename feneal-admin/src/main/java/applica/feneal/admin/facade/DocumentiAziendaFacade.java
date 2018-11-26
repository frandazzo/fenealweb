package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.reports.UiDocumentoAzienda;
import applica.feneal.domain.model.core.servizi.DocumentoAzienda;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoAziendaReportSearchParams;
import applica.feneal.services.ReportDocumentiAziendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class DocumentiAziendaFacade {

    @Autowired
    private ReportDocumentiAziendaService docAzService;

    public List<UiDocumentoAzienda> reportDocumentiAzienda(UiDocumentoAziendaReportSearchParams params){
        List<DocumentoAzienda> doc = docAzService.retrieveDocumentiAzienda(params);

        return convertDocumentiToUiDocumentiAzienda(doc);
    }

    private List<UiDocumentoAzienda> convertDocumentiToUiDocumentiAzienda(List<DocumentoAzienda> doc) {
        List<UiDocumentoAzienda> result = new ArrayList<>();

        for (DocumentoAzienda documento : doc) {
            UiDocumentoAzienda d = new UiDocumentoAzienda();

            d.setDocData(documento.getData());
            d.setDocTipoDocumento(documento.getTipo().toString());
            if (documento.getCollaboratore() != null)
                d.setDocCollaboratore(documento.getCollaboratore().toString());
            d.setDocProvince(documento.getProvince().toString());

            d.setAziendaRagioneSociale(documento.getAzienda().getDescription());
            d.setAziendaCitta(documento.getAzienda().getCity());
            d.setAziendaProvincia(documento.getAzienda().getProvince());
            d.setAziendaCap(documento.getAzienda().getCap());
            d.setAziendaIndirizzo(documento.getAzienda().getAddress());
            d.setAziendaNote(documento.getAzienda().getNotes());
            d.setAziendaId(documento.getAzienda().getLid());

            result.add(d);
        }

        return result;
    }

}
