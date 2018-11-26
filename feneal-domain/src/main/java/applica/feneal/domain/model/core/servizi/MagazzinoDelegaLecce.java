package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

public class MagazzinoDelegaLecce  extends SecuredDomainEntity
{


        private Lavoratore lavoratore;
        private Date data;
        private Province province;
        private Paritethic paritethic;  //ente
        private Collaboratore collaboratore;
        private String otherparithetics;




        private CausaleIscrizioneDelega subscribeReason;

        private int numGiorniDaSottoscrizione;

        public int getNumGiorniDaSottoscrizione(){

            if (data == null)
                return 0;
            return Days.daysBetween(new DateTime(data),new DateTime(new Date())).getDays();

        }

        public CausaleIscrizioneDelega getSubscribeReason() {
            return subscribeReason;
        }

        public void setSubscribeReason(CausaleIscrizioneDelega subscribeReason) {
            this.subscribeReason = subscribeReason;
        }


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
