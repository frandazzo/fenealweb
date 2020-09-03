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
public class SendState implements DelegaState {
    @Override
    public List<Integer> getSupportedNextStates(Delega del, ApplicationOptions opt) {
        return Arrays.asList(Delega.ACTION_BACK, Delega.state_accepted, Delega.state_cancelled, Delega.state_revoked);
    }

    @Override
    public void subscribeDelega(Delega del) {
        //non è possibile
    }

    @Override
    public void sendDelega(Date date, Delega del) {
        //non è possibile dato che è gia nello stesso stato
    }

    @Override
    public void acceptDelega(Date date, Delega del) {
        if (date == null)
            date=del.getSendDate();
        if (date.before(del.getSendDate()))
            date = del.getSendDate();

        del.setAcceptDate(date);
        del.setState(Delega.state_accepted);
        del.setPreecedingState(Delega.state_sent);
    }

    @Override
    public void cancelDelega(Date date, Delega del, CausaleRevoca reason, Delega acceptedDelega) {
        //Date date = new Date();
        if (date.before(del.getSendDate()))
            date = del.getSendDate();

        del.setCancelDate(date);
        del.setState(Delega.state_cancelled);
        del.setPreecedingState(Delega.state_sent);
        del.setCancelReason(reason);
    }

    @Override
    public void revokeDelega(Date date, Delega del, CausaleRevoca reason) {
        //Date date = new Date();
        if (date.before(del.getSendDate()))
            date = del.getSendDate();

        del.setRevokeDate(date);
        del.setState(Delega.state_revoked);
        del.setPreecedingState(Delega.state_sent);
        del.setRevokeReason(reason);
    }

    @Override
    public void activateDelega(Delega del) {
        Date date = new Date();
        if (date.before(del.getSendDate()))
            date = del.getSendDate();

        del.setActivationDate(date);
        del.setState(Delega.state_activated);
        del.setPreecedingState(Delega.state_sent);

        del.setActivatedWithoutAcceptance(true);
        del.setNotes("Delega attivata automaticamente da un incasso anche se non sottoscritta");
    }

    @Override
    public void goBack(Delega delega) {
        if (delega.getPreecedingState().equals(Delega.state_accepted))
        {

        }
        else if (delega.getPreecedingState().equals(Delega.state_activated))
        {

        }
        else if (delega.getPreecedingState().equals(Delega.state_cancelled))
        {

        }
        else if (delega.getPreecedingState().equals(Delega.state_revoked))
        {

        }
        else if (delega.getPreecedingState().equals(Delega.state_sent)){

        }

        else { //subscribed state
            delega.setSendDate(null);
            delega.setState(Delega.state_subscribe);
        }

    }

    @Override
    public void updateDelega(Delega delegaToUpdate, Delega data) {
        //posso solo cambiare le note e il collaaboratore

        delegaToUpdate.setCollaborator(data.getCollaborator());
        delegaToUpdate.setContract(data.getContract());

        delegaToUpdate.setNotes(data.getNotes());
        delegaToUpdate.setValidityDate(data.getValidityDate());
        delegaToUpdate.setNomeattachment(data.getNomeattachment());
        delegaToUpdate.setAttachment(data.getAttachment());
    }
}

