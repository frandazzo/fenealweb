package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.reports.UiDocumento;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoReportSearchParams;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportDocumentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class DocumentiFacade {

    @Autowired
    private ReportDocumentiService docService;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private ListaLavoroService lSrv;

    public List<UiDocumento> reportDocumenti(UiDocumentoReportSearchParams params){
        List<Documento> doc = docService.retrieveDocumenti(params);

        return convertDocumentiToUiDocumenti(doc);

    }


    private List<UiDocumento> convertDocumentiToUiDocumenti(List<Documento> doc) {
        List<UiDocumento> result = new ArrayList<>();





        for (Documento documento : doc) {
            UiDocumento d = new UiDocumento();

            d.setDocData(documento.getData());
            d.setDocTipoDocumento(documento.getTipo().toString());
            if (documento.getCollaboratore() != null)
                d.setDocCollaboratore(documento.getCollaboratore().toString());
            d.setDocProvince(documento.getProvince().toString());
            d.setDocNote(documento.getNotes());
            d.setLavoratoreId(documento.getLavoratore().getLid());
            d.setLavoratoreCap(documento.getLavoratore().getCap());
            d.setLavoratoreCellulare(documento.getLavoratore().getCellphone());
            d.setLavoratorMail(documento.getLavoratore().getMail());
            d.setLavoratoreTelefono(documento.getLavoratore().getPhone());
            d.setLavoratoreCittaResidenza(documento.getLavoratore().getLivingCity());
            d.setLavoratoreCodiceFiscale(documento.getLavoratore().getFiscalcode());
            d.setLavoratoreCognome(documento.getLavoratore().getSurname());

            d.setLavoratoreDataNascita(documento.getLavoratore().getBirthDate());
            d.setLavoratoreIndirizzo(documento.getLavoratore().getAddress());
            d.setLavoratoreLuogoNascita(documento.getLavoratore().getBirthPlace());
            d.setLavoratoreNazionalita(documento.getLavoratore().getNationality());
            d.setLavoratoreNome(documento.getLavoratore().getName());
            d.setLavoratoreCittaResidenza(documento.getLavoratore().getLivingCity());
            d.setLavoratoreProvinciaNascita(documento.getLavoratore().getBirthProvince());
            d.setLavoratoreProvinciaResidenza(documento.getLavoratore().getLivingProvince());
            if (documento.getLavoratore().getSex().equals("MASCHIO"))
                d.setLavoratoreSesso(Lavoratore.MALE);
            else
                d.setLavoratoreSesso(Lavoratore.FEMALE);

            d.setLavoratoreCodiceCassaEdile(documento.getLavoratore().getCe());
            d.setLavoratoreCodiceEdilcassa(documento.getLavoratore().getEc());
            if (documento.getLavoratore().getFund() != null)
                d.setLavoratoreFondo(documento.getLavoratore().getFund().getDescription());
            d.setLavoratoreNote(documento.getLavoratore().getNotes());

            result.add(d);


        }

        return result;
    }


    public ListaLavoro createListalavoro(List<UiDocumento> documenti, String description) throws Exception {
        List<Documento> com = convertUiToDocumento(documenti);
        return lSrv.createListaFromArchivioDocumentale(com,description);
    }

    private List<Documento> convertUiToDocumento(List<UiDocumento> documenti) {
        List<Documento> a = new ArrayList<>();

        for (UiDocumento uiDocumento : documenti) {
            Documento s = new Documento();
            s.setLavoratore(lavRep.get(uiDocumento.getLavoratoreId()).orElse(null));

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }

}
