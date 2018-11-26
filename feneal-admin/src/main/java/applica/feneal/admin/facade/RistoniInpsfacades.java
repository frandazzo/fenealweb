package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.inps.FileToValidate;
import applica.feneal.admin.viewmodel.inps.IncrocioQuoteInps;
import applica.feneal.admin.viewmodel.inps.QuotaInpsDTO;
import applica.feneal.admin.viewmodel.inps.RistornoInpsSummary;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.inps.IncrocioInps;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.feneal.domain.model.core.inps.RistornoInps;
import applica.feneal.services.InpsPugliaServices;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class RistoniInpsfacades {

    @Autowired
    private InpsPugliaServices inpsService;

    @Autowired
    private Security sec;


    public RistornoInpsSummary getRistornoInpsSummaryById(long id) throws Exception {


        IncrocioInps i = inpsService.getIncrociobyId(id);

        RistornoInpsSummary summary = new RistornoInpsSummary();
        summary.setId(i.getRistorno().getLid());
        summary.setCostoInpsRiga(String.valueOf(i.getRistorno().getCostoInpsRiga()));
        summary.setData(i.getRistorno().getData());
        summary.setPercentualeRistorno(String.valueOf(i.getRistorno().getPercentualeRistorno()));
        summary.setPercentualeRistornoEdili(String.valueOf(i.getRistorno().getPercentualeRistornoEdili()));
        summary.setTitolo(i.getRistorno().getTitolo());
        summary.setPercentualeEffettiva(i.getRistorno().getPercentualeEffettiva());

        summary.setCostoTessera(String.valueOf(i.getRistorno().getCostoTessera()));

        for (InpsFile o : i.getFiles()) {
            FileToValidate v = new FileToValidate();
            v.setFilename(o.getFilename());
            v.setFilepath(o.getFilepath());
            if (o.isCsc())
                summary.getCscFiles().add(v);
            else
                summary.getInpsFiles().add(v);
        }


        summary.setPagamentiReferenti(i.getListaReferenti());


        for (QuotaInps quotaInps : i.getQuote()) {
            QuotaInpsDTO dto1 = new QuotaInpsDTO();
            dto1.setTitoloRistorno(quotaInps.getRistorno() != null? quotaInps.getRistorno().getTitolo(): "");
            dto1.setCscFilename(quotaInps.getCscFilename());
            dto1.setCscFilepath(quotaInps.getCscFilepath());
            dto1.setDataDomanda(quotaInps.getDataDomanda());
            dto1.setDataValuta(quotaInps.getDataValuta());
            dto1.setIdRistorno(quotaInps.getRistorno() != null? quotaInps.getRistorno().getLid(): 0);
            dto1.setImporto(quotaInps.getImporto());
            dto1.setImportRistornato(quotaInps.getImportRistornato());
            dto1.setReferente(quotaInps.getReferente());
            dto1.setNumDomanda(quotaInps.getNumDomanda());
            dto1.setPatronato(quotaInps.getPatronato());
            dto1.setLavoratoreCap(quotaInps.getLavoratore().getCap());
            dto1.setLavoratoreCellulare(quotaInps.getLavoratore().getCellphone());
            dto1.setLavoratoreCodiceFiscale(quotaInps.getLavoratore().getFiscalcode());
            dto1.setLavoratoreCognome(quotaInps.getLavoratore().getSurname());
            dto1.setLavoratoreComuneNascita(quotaInps.getLavoratore().getBirthPlace());
            dto1.setLavoratoreComuneResidenza(quotaInps.getLavoratore().getLivingCity());
            dto1.setLavoratoreEdile(quotaInps.isLavoratoreEdile());
            dto1.setLavoratoredataNascita(quotaInps.getLavoratore().getBirthDate());
            dto1.setLavoratoreNazione(quotaInps.getLavoratore().getNationality());
            dto1.setLavoratoreId(quotaInps.getLavoratore().getLid());
            dto1.setLavoratoreIndirizzo(quotaInps.getLavoratore().getAddress());
            dto1.setLavoratoreNome(quotaInps.getLavoratore().getName());


            summary.getQuote().add(dto1);

        }



        for (QuotaInps quotaInps : i.getQuotePagate()) {
            QuotaInpsDTO dto1 = new QuotaInpsDTO();
            dto1.setTitoloRistorno(quotaInps.getRistorno() != null? quotaInps.getRistorno().getTitolo(): "");

            dto1.setCscFilename(quotaInps.getCscFilename());
            dto1.setCscFilepath(quotaInps.getCscFilepath());
            dto1.setDataDomanda(quotaInps.getDataDomanda());
            dto1.setDataValuta(quotaInps.getDataValuta());
            dto1.setIdRistorno(quotaInps.getRistorno() != null? quotaInps.getRistorno().getLid(): 0);
            dto1.setImporto(quotaInps.getImporto());
            dto1.setImportRistornato(quotaInps.getImportRistornato());
            dto1.setReferente(quotaInps.getReferente());
            dto1.setNumDomanda(quotaInps.getNumDomanda());
            dto1.setPatronato(quotaInps.getPatronato());
            dto1.setLavoratoreCap(quotaInps.getLavoratore().getCap());
            dto1.setLavoratoreCellulare(quotaInps.getLavoratore().getCellphone());
            dto1.setLavoratoreCodiceFiscale(quotaInps.getLavoratore().getFiscalcode());
            dto1.setLavoratoreCognome(quotaInps.getLavoratore().getSurname());
            dto1.setLavoratoreComuneNascita(quotaInps.getLavoratore().getBirthPlace());
            dto1.setLavoratoreComuneResidenza(quotaInps.getLavoratore().getLivingCity());
            dto1.setLavoratoreEdile(quotaInps.isLavoratoreEdile());
            dto1.setLavoratoredataNascita(quotaInps.getLavoratore().getBirthDate());
            dto1.setLavoratoreNazione(quotaInps.getLavoratore().getNationality());
            dto1.setLavoratoreId(quotaInps.getLavoratore().getLid());
            dto1.setLavoratoreIndirizzo(quotaInps.getLavoratore().getAddress());
            dto1.setLavoratoreNome(quotaInps.getLavoratore().getName());


            summary.getQuotePagate().add(dto1);

        }

        return summary;
    }


    public long incrociaQuote(IncrocioQuoteInps data) throws Exception {

        User u = ((User) sec.getLoggedUser());

        RistornoInps i = new RistornoInps();
        i.setTitolo(data.getTitle());
        if (data.getRowCost()!= null)
            data.setRowCost(data.getRowCost().replace(",","."));
        i.setCostoInpsRiga(tryParse(data.getRowCost()));
        i.setPercentualeRistornoEdili(tryParse(data.getRetrunPercentEdili()));
        i.setPercentualeRistorno(tryParse(data.getRetrunPercent()));
        i.setPercentualeEffettiva(tryParse(data.getEffectiveCost()));
        i.setCompanyId(u.getCompany().getLid());
        i.setCostoTessera(tryParse(data.getCostoTessera()));
        i.setData(new Date());

        List<InpsFile> files = new ArrayList<>();
        data.getInps().stream().forEach(a -> {
            InpsFile f = new InpsFile();
            f.setFilename(a.getFilename());
            f.setFilepath(a.getFilepath());
            f.setCsc(false);
            f.setCompanyId(u.getCompany().getLid());
            files.add(f);
        });
        data.getCsc().stream().forEach(a -> {
            InpsFile f = new InpsFile();
            f.setFilename(a.getFilename());
            f.setFilepath(a.getFilepath());
            f.setCsc(true);
            f.setCompanyId(u.getCompany().getLid());
            files.add(f);
        });

        return inpsService.incrociaQuoteInps(i, files);

    }

    private double tryParse(String rowCost) {
        try{
            return Double.parseDouble(rowCost);
        }catch (Exception e){
            return 0;
        }

    }

    public void deleteRistorno(long id) {
        inpsService.deleteRistorno(id);
    }

    public List<QuotaInpsDTO> findQuote(Date lastDateStart, Date lastDateEnd) {
        List<QuotaInps> result =   inpsService.findQuote(lastDateStart,lastDateEnd);
        return convertToDtos(result);
    }

    private List<QuotaInpsDTO> convertToDtos(List<QuotaInps> result) {
        List<QuotaInpsDTO> res = new ArrayList<>();

        for (QuotaInps quotaInps : result) {
            res.add(convertToDto(quotaInps));
        }

        return res;
    }

    private QuotaInpsDTO convertToDto(QuotaInps quotaInps) {
        QuotaInpsDTO dto1 = new QuotaInpsDTO();
        dto1.setTitoloRistorno(quotaInps.getRistorno()!= null?quotaInps.getRistorno().getTitolo(): "");
        dto1.setCscFilename(quotaInps.getCscFilename());
        dto1.setCscFilepath(quotaInps.getCscFilepath());
        dto1.setDataDomanda(quotaInps.getDataDomanda());
        dto1.setDataValuta(quotaInps.getDataValuta());
        dto1.setIdRistorno(quotaInps.getRistorno() != null? quotaInps.getRistorno().getLid(): 0);
        dto1.setImporto(quotaInps.getImporto());
        dto1.setImportRistornato(quotaInps.getImportRistornato());
        dto1.setReferente(quotaInps.getReferente());
        dto1.setNumDomanda(quotaInps.getNumDomanda());
        dto1.setPatronato(quotaInps.getPatronato());
        dto1.setLavoratoreCap(quotaInps.getLavoratore().getCap());
        dto1.setLavoratoreCellulare(quotaInps.getLavoratore().getCellphone());
        dto1.setLavoratoreCodiceFiscale(quotaInps.getLavoratore().getFiscalcode());
        dto1.setLavoratoreCognome(quotaInps.getLavoratore().getSurname());
        dto1.setLavoratoreComuneNascita(quotaInps.getLavoratore().getBirthPlace());
        dto1.setLavoratoreComuneResidenza(quotaInps.getLavoratore().getLivingCity());
        dto1.setLavoratoreEdile(quotaInps.isLavoratoreEdile());
        dto1.setLavoratoredataNascita(quotaInps.getLavoratore().getBirthDate());
        dto1.setLavoratoreNazione(quotaInps.getLavoratore().getNationality());
        dto1.setLavoratoreId(quotaInps.getLavoratore().getLid());
        dto1.setLavoratoreIndirizzo(quotaInps.getLavoratore().getAddress());
        dto1.setLavoratoreNome(quotaInps.getLavoratore().getName());


        return  dto1;
    }

    public RistornoInpsSummary getRistornoInpsSummaryByIdWithoutQuote(long id) {
        IncrocioInps i = inpsService.getIncrociobyIdWithoutQuote(id);

        RistornoInpsSummary summary = new RistornoInpsSummary();
        summary.setId(i.getRistorno().getLid());
        summary.setCostoInpsRiga(String.valueOf(i.getRistorno().getCostoInpsRiga()));
        summary.setData(i.getRistorno().getData());
        summary.setPercentualeRistorno(String.valueOf(i.getRistorno().getPercentualeRistorno()));
        summary.setPercentualeRistornoEdili(String.valueOf(i.getRistorno().getPercentualeRistornoEdili()));
        summary.setTitolo(i.getRistorno().getTitolo());
        summary.setPercentualeEffettiva(i.getRistorno().getPercentualeEffettiva());
        summary.setCostoTessera(String.valueOf(i.getRistorno().getCostoTessera()));



        for (InpsFile o : i.getFiles()) {
            FileToValidate v = new FileToValidate();
            v.setFilename(o.getFilename());
            v.setFilepath(o.getFilepath());
            if (o.isCsc())
                summary.getCscFiles().add(v);
            else
                summary.getInpsFiles().add(v);
        }


        return summary;
}
}
