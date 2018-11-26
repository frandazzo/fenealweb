package applica.feneal.services;

import applica.feneal.domain.model.core.analisi.IscrittiDescriptor;

public interface AnalisysService {
    Integer[] getAnniIscrizioni();
    IscrittiDescriptor getIscrittiPerSettore(int anno, String region);
    IscrittiDescriptor getIscrittiPerAreaGeografica(int anno, String region);

}
