package applica.feneal.services.impl;

import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.services.ComunicazioniService;
import org.springframework.context.ApplicationContext;

import java.util.Calendar;
import java.util.List;

public class SmsMailMerger  implements Runnable{


    private final List<Lavoratore> lavoratori;
    private final String text;
    private String province;
    private ApplicationContext context;

    public SmsMailMerger(List<Lavoratore> lavoratori, String text, String province, ApplicationContext context){

        this.lavoratori = lavoratori;
        this.text = text;
        this.province = province;
        this.context = context;
    }


    @Override
    public void run() {
        ComunicazioniService comSvc = context.getBean(ComunicazioniService.class);
        LavoratoriRepository lavRep = context.getBean(LavoratoriRepository.class);
        int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);


        for (Lavoratore lavoratore : lavoratori) {
            try {
                lavoratore.setUltimaComunicazione(String.valueOf(annoCorrente));
                lavRep.save(lavoratore);
                comSvc.sendSms(lavoratore.getCellphone(), lavoratore.getLid(),"1",text,province);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
