package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.domain.model.setting.TipoDocumento;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.Date;

/**
 * Created by angelo on 06/05/2016.
 */
public class MagazzinoDelega extends SecuredDomainEntity {


    private Lavoratore lavoratore;
    private Date data;
    private Province province;
    private Paritethic paritethic;  //ente
    private Collaboratore collaboratore;
    private String otherparithetics;


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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Paritethic getParitethic() {
        return paritethic;
    }

    public void setParitethic(Paritethic paritethic) {
        this.paritethic = paritethic;
    }

    public Collaboratore getCollaboratore() {
        return collaboratore;
    }

    public void setCollaboratore(Collaboratore collaboratore) {
        this.collaboratore = collaboratore;
    }

    public void setOtherparithetics(String otherparithetics) {
        this.otherparithetics = otherparithetics;
    }

    public String getOtherparithetics() {
        return otherparithetics;
    }
}
