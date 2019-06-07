package applica.feneal.admin.facade;


import applica.feneal.domain.model.core.quote.varese.UiDettaglioQuotaVarese;
import applica.feneal.domain.model.User;


import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.varese.UiQuoteVareseObject;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.services.AziendaService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.ReportQuoteVareseService;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportQuoteVareseFacade {

    @Autowired
    private ReportQuoteVareseService rptQuoteserv;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private Security security;

    private UiQuoteVareseObject convertToUiDettaglioQuota(List<DettaglioQuotaAssociativa> quoteDetails, UiQuoteVareseObject obj) {

        List<UiDettaglioQuotaVarese> conNum = obj.getConNumero();
        List<UiDettaglioQuotaVarese> senzaNum = obj.getSenzaNumero();


        for (DettaglioQuotaAssociativa dettaglio : quoteDetails) {
            UiDettaglioQuotaVarese q = new UiDettaglioQuotaVarese();

            q.setId(dettaglio.getLid());
            q.setIdQuota(dettaglio.getIdRiepilogoQuotaAssociativa());
            q.setProvincia(dettaglio.getProvincia());

            Lavoratore lav = lavSvc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), dettaglio.getIdLavoratore());

            q.setLavoratoreNomeCompleto(String.format("%s %s", lav.getSurname(), lav.getName()));
            q.setLavoratoreCodiceFiscale(lav.getFiscalcode());
            q.setLavoratoreCell(lav.getNormalizedCellPhone());
            q.setLavoratoreId(lav.getLid());
            q.setLavoratoreCap(lav.getCap());
            q.setLavoratoreComuneResidenza(lav.getLivingCity());
            q.setLavoratoreProvinciaResidenza(lav.getLivingProvince());
            q.setLavoratoreIndirizzo(lav.getAddress());
            q.setLavoratoreUltimaComunicazione(lav.getUltimaComunicazione());


            if(!StringUtils.isEmpty(q.getLavoratoreCell())){
                conNum.add(q);
            }
            else {

                if (!StringUtils.isEmpty(lav.getCellphone()))
                    q.setLavoratoreWrongCell(lav.getCellphone());
                else
                    q.setLavoratoreWrongCell(lav.getPhone());

                senzaNum.add(q);
            }

        }

        return obj;
    }

    public UiQuoteVareseObject reportQuote(UiQuoteVareseReportSearchParams params) {
        List<DettaglioQuotaAssociativa> rpt = rptQuoteserv.retrieveQuoteVarese(params);

        UiQuoteVareseObject obj = new UiQuoteVareseObject();

        return convertToUiDettaglioQuota(rpt, obj);
    }


    public String createFile(String a) throws Exception {
        return rptQuoteserv.compileFileForLavoratore(a);
    }
}
