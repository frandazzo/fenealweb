package applica.feneal.admin.facade.RSU;

import applica.feneal.domain.model.RSU.Dto.ElezioneDto;
import applica.feneal.domain.model.RSU.Dto.UiElezioneDtoForListe;
import applica.feneal.domain.model.RSU.Election.ElezioneRSU;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.feneal.services.RSU.SedeRsuService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportElezioniRsuFacade {

    @Autowired
    private AziendaRsuService aziendaRsuService;

    @Autowired
    private SedeRsuService sedeRsuService;

    @Autowired
    private Security sec;

    public ElezioneDto setDatiGeneraliElezioneRsu(Long firmRsu, Long sedeRsu, int anno) {
        ElezioneDto dto = new ElezioneDto();
        ElezioneRSU e = new ElezioneRSU();

        //importo i dati nel dto
        AziendaRSU a = aziendaRsuService.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),firmRsu);
        SedeRSU s = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),sedeRsu);
        if(a != null)
            dto.setAzienda(a.getDescription());

        if(s != null) {
            dto.setDivisione(s.getDescription());
        }else{
            dto.setDivisione("");
        }

        dto.setAnno(anno);

        //estraggo l'entita ElezioneRSU dal dto
        e.Initialize(dto);
        //controllo che i dati generali siano corretti
        e.CheckHeaderData();

        dto = e.toDto(e);

        return dto;
    }

    public ElezioneDto createAndAddListToElezioneRsu(UiElezioneDtoForListe uiElezione) {
        ElezioneRSU e = new ElezioneRSU();
        ElezioneDto newDto = new ElezioneDto();

        if(uiElezione.getDto() != null)
            e.Initialize(uiElezione.getDto());

        e.addLista(uiElezione.getNome(),uiElezione.getFirmataria());
        newDto = e.toDto(e);
        return newDto;
    }
}
