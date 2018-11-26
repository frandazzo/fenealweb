package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.servizi.ComunicazioniRepository;
import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.domain.model.core.servizi.search.UiComunicazioneReportSearchParams;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoReportSearchParams;
import applica.feneal.services.ReportComunicazioniService;
import applica.feneal.services.ReportDocumentiService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
@Service
public class ReportComunicazioniServiceImpl implements ReportComunicazioniService {

    @Autowired
    private ComunicazioniRepository comRep;

    @Override
    public List<Comunicazione> retrieveComunicazioni(UiComunicazioneReportSearchParams params) {

        Date dateFrom = createDate(1, params.getDatefromMonthReport(), params.getDatefromYearReport());
        Date dateTo = createDate(0, params.getDatetoMonthReport(), params.getDatetoYearReport());
        String typeComm = params.getTypeCommunication();
        String causaleCom = params.getCausaleComunicazione();
        String province = params.getProvince();


        //posso adesso fare la query

        LoadRequest req = LoadRequest.build();

        if (dateFrom != null) {
            Filter f1 = new Filter("data", dateFrom, Filter.GTE);
            req.getFilters().add(f1);
        }

        if (dateTo != null) {
            Filter f2 = new Filter("data", dateTo, Filter.LTE);
            req.getFilters().add(f2);
        }

        if (!StringUtils.isEmpty(typeComm)){
            Filter f3 = new Filter("tipo", Long.parseLong(typeComm), Filter.EQ);
            req.getFilters().add(f3);
        }

        if (!StringUtils.isEmpty(causaleCom)){
            Filter f4 = new Filter("causale", Long.parseLong(causaleCom), Filter.EQ);
            req.getFilters().add(f4);
        }

        if (!StringUtils.isEmpty(province)){
            Integer proId = Integer.parseInt(province);
            Filter f5 = new Filter("province.id", proId, Filter.EQ);
            req.getFilters().add(f5);
        }

        List<Comunicazione> comm = comRep.find(req).getRows();


        return comm;

    }


    /* Crea oggetto java Date dato il mese e l'anno */
    private Date createDate(int day, String month, String year) {

        if (month == null || year == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (day == 0)
            calendar.set(Calendar.MONTH, Integer.valueOf(month));
        else
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);

        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        Date date = calendar.getTime();

        return date;
    }
}
