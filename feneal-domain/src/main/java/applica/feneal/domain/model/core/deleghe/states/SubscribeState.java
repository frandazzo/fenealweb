package applica.feneal.domain.model.core.deleghe.states;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.DelegaState;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.option.ApplicationOptions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 05/04/2016.
 */
public class SubscribeState implements DelegaState {
    @Override
    public void subscribeDelega(Delega del) {
        del.setState(Delega.state_subscribe);
    }
    public List<Integer> getSupportedNextStates(Delega del, ApplicationOptions opt) {
        return Arrays.asList(Delega.state_sent, Delega.state_accepted, Delega.state_cancelled, Delega.state_revoked);
    }

    @Override
    public void sendDelega(Date date, Delega del) {
        if (date == null)
            date=del.getDocumentDate();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();


        del.setSendDate(date);
        del.setState(Delega.state_sent);
        del.setPreecedingState(Delega.state_subscribe);
    }

    @Override
    public void acceptDelega(Date date, Delega del) {
        if (date == null)
            date=del.getDocumentDate();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setSendDate(date);
        del.setAcceptDate(date);
        del.setState(Delega.state_accepted);
        del.setPreecedingState(Delega.state_subscribe);
    }

    @Override
    public void cancelDelega(Date date, Delega del, CausaleRevoca reason, Delega acceptedDelega) {

        //Date date = new Date();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setCancelDate(date);
        del.setState(Delega.state_cancelled);
        del.setPreecedingState(Delega.state_subscribe);
        del.setCancelReason(reason);
    }

    @Override
    public void revokeDelega(Date date, Delega del, CausaleRevoca reason) {
        //Date date = new Date();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setRevokeDate(date);
        del.setState(Delega.state_revoked);
        del.setPreecedingState(Delega.state_subscribe);
        del.setRevokeReason(reason);
    }

    @Override
    public void activateDelega(Delega del) {
        Date date = new Date();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setActivationDate(date);
        del.setState(Delega.state_activated);
        del.setPreecedingState(Delega.state_subscribe);

        del.setActivatedWithoutAcceptance(true);
        del.setNotes("Delega attivata automaticamente da un incasso anche se non sottoscritta");
    }

    @Override
    public void goBack(Delega delega) {

    }

    @Override
    public void updateDelega(Delega delegaToUpdate, Delega data) {
        //posso solo cambiare la causale sottoscrizione le note e il collaaboratore

        delegaToUpdate.setSubscribeReason(data.getSubscribeReason());
        delegaToUpdate.setCollaborator(data.getCollaborator());
        delegaToUpdate.setNotes(data.getNotes());
        delegaToUpdate.setValidityDate(data.getValidityDate());
        delegaToUpdate.setNomeattachment(data.getNomeattachment());
        delegaToUpdate.setAttachment(data.getAttachment());
}
}
