package applica.feneal.domain.model.core.deleghe;

import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.option.ApplicationOptions;

import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 05/04/2016.
 */
public interface DelegaState {
    List<Integer> getSupportedNextStates(Delega del, ApplicationOptions opt);

    //sottoscrizione
    void subscribeDelega(Delega del);
    //inoltro
    void sendDelega(Date date, Delega del);
    //accettazione
    void acceptDelega(Date date, Delega del);
    //annullamento
    void cancelDelega(Date date, Delega del, CausaleRevoca reason, Delega acceptedDelega);
    //revoca
    void revokeDelega(Date date, Delega del, CausaleRevoca reason);
    //attivazione
    void activateDelega(Delega del);

    //ripristino lo stato precedente
    void goBack(Delega delega);


    void updateDelega(Delega delegaToUpdate, Delega data);
}
