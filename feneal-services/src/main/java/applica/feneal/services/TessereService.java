package applica.feneal.services;

import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.tessere.Tessera;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by fgran on 03/06/2016.
 */
public interface TessereService {

    String printTesseraForWorker(long workerId, String settore, String provincia) throws Exception;
    Tessera findPrintedTessera(String fiscalCode , int year);
    List<Tessera> findOtherPrintedTessere(String fiscalCode, int year);
    String printTessere(List<DettaglioQuotaAssociativa> iscrizioni, String settore, String provincia, boolean onlyWithoutTessera, boolean global) throws IOException;

}
