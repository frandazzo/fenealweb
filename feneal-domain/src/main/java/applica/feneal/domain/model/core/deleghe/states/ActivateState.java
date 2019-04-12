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
public class ActivateState implements DelegaState {
    @Override
    public List<Integer> getSupportedNextStates(Delega del, ApplicationOptions opt) {
        return Arrays.asList(Delega.state_cancelled, Delega.state_revoked);
    }

    @Override
    public void subscribeDelega(Delega del) {
        //
    }

    @Override
    public void sendDelega(Date date, Delega del) {
    //
    }

    @Override
    public void acceptDelega(Date date, Delega del) {
            //
    }

    @Override
    public void cancelDelega(Date date, Delega del, CausaleRevoca reason, Delega acceptedDelega) {
        //Date date = new Date();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setCancelDate(date);
        del.setState(Delega.state_cancelled);
        del.setPreecedingState(Delega.state_activated);
        del.setCancelReason(reason);
        if (acceptedDelega != null){
            del.setTombstoneDelega(true);
            del.setNotes("Delega definitivamente annullata dall'accettazione della delega con data documento: " + acceptedDelega.getDocumentDate().toString());
        }
    }

    @Override
    public void revokeDelega(Date date, Delega del, CausaleRevoca reason) {
        //Date date = new Date();
        if (date.before(del.getDocumentDate()))
            date = del.getDocumentDate();

        del.setRevokeDate(date);
        del.setState(Delega.state_revoked);
        del.setPreecedingState(Delega.state_activated);
        del.setRevokeReason(reason);
    }

    @Override
    public void activateDelega(Delega del) {
        //
    }

    @Override
    public void goBack(Delega delega) {
//        if (delega.getPreecedingState().equals(Delega.state_accepted))
//        {
//            delega.setActivationDate(null);
//            delega.setState(Delega.state_accepted);
//            delega.setPreecedingState(Delega.state_sent);
//        }
//        else if (delega.getPreecedingState().equals(Delega.state_activated))
//        {
//
//        }
//        else if (delega.getPreecedingState().equals(Delega.state_cancelled))
//        {
//
//        }
//        else if (delega.getPreecedingState().equals(Delega.state_revoked))
//        {
//
//        }
//        else if (delega.getPreecedingState().equals(Delega.state_sent)){
//
//
//            delega.setActivationDate(null);
//            delega.setAcceptDate(null);
//            delega.setState(Delega.state_sent);
//            delega.setPreecedingState(Delega.state_subscribe);
//        }
//
//        else { //subscribed state
//            delega.setActivationDate(null);
//            delega.setAcceptDate(null);
//            delega.setSendDate(null);
//            delega.setState(Delega.state_subscribe);
//            delega.setPreecedingState("");
//        }
    }

    @Override
    public void updateDelega(Delega delegaToUpdate, Delega data) {
        //posso soloe le note e il collaaboratore

        delegaToUpdate.setCollaborator(data.getCollaborator());
        delegaToUpdate.setNotes(data.getNotes());
        delegaToUpdate.setValidityDate(data.getValidityDate());
        delegaToUpdate.setNomeattachment(data.getNomeattachment());
        delegaToUpdate.setAttachment(data.getAttachment());
    }
}

