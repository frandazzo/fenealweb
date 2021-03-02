package applica.feneal.domain.model.RSU.Dto;

import java.util.ArrayList;
import java.util.List;

public class EsitoAttribuzioneRsuDto {
    private List<AbstractContestoAttribuzioneDto> contesti = new ArrayList<AbstractContestoAttribuzioneDto>();

    public List<AbstractContestoAttribuzioneDto> getContesti() {
        return contesti;
    }

    public void setContesti(List<AbstractContestoAttribuzioneDto> contesti) {
        this.contesti = contesti;
    }
}
