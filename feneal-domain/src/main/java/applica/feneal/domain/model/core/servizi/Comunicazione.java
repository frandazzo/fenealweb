package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.feneal.domain.model.setting.TipoComunicazione;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.Date;

/**
 * Created by fgran on 28/04/2016.
 */
public class Comunicazione extends SecuredDomainEntity {


    private Lavoratore lavoratore;
    private Date data;
    private TipoComunicazione tipo;
    private CausaleComunicazione causale;
    private Province province;
    private String oggetto;

    public Lavoratore getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(Lavoratore lavoratore) {
        this.lavoratore = lavoratore;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TipoComunicazione getTipo() {
        return tipo;
    }

    public void setTipo(TipoComunicazione tipo) {
        this.tipo = tipo;
    }

    public CausaleComunicazione getCausale() {
        return causale;
    }

    public void setCausale(CausaleComunicazione causale) {
        this.causale = causale;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }
}
