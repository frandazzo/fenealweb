package applica.feneal.services;

import applica.feneal.domain.model.core.inps.IncrocioInps;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.feneal.domain.model.core.inps.RistornoInps;

import java.util.Date;
import java.util.List;

public interface InpsPugliaServices {
    long incrociaQuoteInps(RistornoInps ristorno, List<InpsFile> files) throws Exception;

    List<QuotaInps> inserisciQuoteInps(String filepath) throws Exception;


    IncrocioInps getIncrociobyId(long id) throws Exception;

    List<RistornoInps> getRistorni();

    void deleteRistorno(long id);

    List<QuotaInps> findQuote(Date lastDateStart, Date lastDateEnd);

    IncrocioInps getIncrociobyIdWithoutQuote(long id);
}
