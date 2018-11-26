package applica.feneal.services;

import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.services.messages.MessageInput;

import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
public interface RichieseInfoAiTerritoriService {

    void requireInfo(MessageInput message, List<String> fiscalCodes, String provinceCode) throws Exception;

    void requireInfoWorker(MessageInput message, String provinceCode) throws Exception;

    List<RichiestaInfo> findListForLavoratoreIdAndProvinceName(long lavoratoreId, String provincia);
}
