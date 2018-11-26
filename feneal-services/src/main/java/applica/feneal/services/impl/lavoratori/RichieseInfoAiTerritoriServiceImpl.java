package applica.feneal.services.impl.lavoratori;

import applica.feneal.domain.data.core.servizi.RichiesteInfoRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.services.GeoService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.MessageService;
import applica.feneal.services.RichieseInfoAiTerritoriService;
import applica.feneal.services.messages.MessageInput;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
@Service
public class RichieseInfoAiTerritoriServiceImpl implements RichieseInfoAiTerritoriService {
    @Autowired
    LavoratoreService lavSvc;

    @Autowired
    RichiesteInfoRepository richiRep;

    @Autowired
    GeoService geo;

    @Autowired
    MessageService messageService;

    @Override
    public void requireInfo(MessageInput message, List<String> fiscalCodes ,String provinceCode) throws Exception {

        //devo creare una richiesta di dinfo ai territori per ogni lavoratgore
        for (String fiscalCode : fiscalCodes) {
            Lavoratore l = lavSvc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode, provinceCode);

            RichiestaInfo info = new RichiestaInfo();
            info.setData(new Date());
            info.setDestinatario(StringUtils.join(message.getRecipients(), ";"));
            info.setLavoratore(l);
            info.setRequestToProvince(message.getProvinceInfo());
            info.setProvince(geo.getProvinceByName(provinceCode));
            info.setNote(message.getContent());


            //a questo punto posso salvare
            richiRep.save(info);
        }


        //adesso posso inviare le mail
        messageService.sendMessages(message);
        //oltre ad inviare la mail devo salvare tutte le richieste di informazione sui lavoratori

    }

    @Override
    public void requireInfoWorker(MessageInput message, String provinceCode) throws Exception {

        messageService.sendMessages(message);

    }



    @Override
    public List<RichiestaInfo> findListForLavoratoreIdAndProvinceName(long lavoratoreId, String provincia) {
        LoadRequest req = LoadRequest.build().filter("lavoratore", lavoratoreId).filter("requestToProvince", provincia);
        return  richiRep.find(req).getRows();

    }
}
