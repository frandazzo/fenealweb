package applica.feneal.admin.utils;

import applica.feneal.admin.crudconfig.DelegaFileRenderer;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalStateFieldRenderer;
import applica.feneal.admin.fields.renderers.readonly.ReadOnlyTextFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.domain.model.FenealEntities;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.exceptions.FormNotFoundException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by antoniolovicario on 01/05/16.
 * Questa classe permette di definire l'oggetto Form utilizzato dal crud dell'applica framework di specifiche entità del sistema.
 */
@Component
public class FormUtils {

    @Autowired
    private ApplicationContext applicationContext;

    public Form generateFormForEntity(String entity, Delega d) throws FormNotFoundException {
        switch (entity) {
            case FenealEntities.LAVORATORE:
                return generateWorkerForm();
            case FenealEntities.DELEGA:
                return generateDelegaForm();
            case FenealEntities.DELEGA_EDIT:
                return generateDelegaFormForEdit(d);
        }

        throw new FormNotFoundException(String.format("Nessun form trovato per l'entità %s", entity));

    }

    private Form generateDelegaForm() {

        Form form = new Form();
        form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
        form.setIdentifier("delega");

        FormDescriptor formDescriptor = new FormDescriptor(form);
        formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("province", String.class, "Provincia", "Dati generali", applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class));

       formDescriptor.addField("documentDate", Date.class, "Data documento", "Dati generali", applicationContext.getBean(DatePickerRenderer.class));
        formDescriptor.addField("workerId", Lavoratore.class, "Lavoratore", "Dati generali", applicationContext.getBean(HiddenFieldRenderer.class));

        formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(CausaleIscrizioneDelegaSelectFieldRenderer.class));
        formDescriptor.addField("sector", Sector.class, "Settore", "Dati generali", applicationContext.getBean(SectorTypeWithoutInpsSelectRenderer.class));
        formDescriptor.addField("paritethic", Sector.class, "Ente", "Dati generali", applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class));
        formDescriptor.addField("workerCompany", Azienda.class, "Azienda", "Dati generali", applicationContext.getBean(AziendeSingleSearchFieldRenderer.class));


        formDescriptor.addField("attachment", String.class, "Scansione", "Dati generali", applicationContext.getBean(DelegaFileRenderer.class));
       // formDescriptor.addField("nomeattachment", String.class, "", "Dati generali", applicationContext.getBean(HiddenFieldRenderer.class));

        formDescriptor.addField("notes", String.class, "Note", "Dati generali", applicationContext.getBean(TextAreaFieldRenderer.class));

        formDescriptor.addField("collaborator", Collaboratore.class, "Collaboratore", "Dati collaboratore comunale", applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class));


        formDescriptor.addField("firstAziendaEdile", Azienda.class, "Azienda edile", "ATTENZIONE: SOLO come riferimento all'azienda per il settore edile. Non sarà inclusa in eventuali ricerche", applicationContext.getBean(AziendeSingleSearchFieldRenderer.class));
        formDescriptor.addField("validityDate", Date.class, "Data validità (impianti fissi)", "Dati generali", applicationContext.getBean(DatePickerRenderer.class));



        return form;
    }


    private Form generateDelegaFormForEdit(Delega d) {

        Form form = new Form();
        form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
        form.setIdentifier("delega");
        FormDescriptor formDescriptor = new FormDescriptor(form);

        formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("province", String.class, "Provincia", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
        formDescriptor.addField("workerId", Lavoratore.class, "Lavoratore", "Dati generali", applicationContext.getBean(HiddenFieldRenderer.class));
        formDescriptor.addField("documentDate", Date.class, "Data documento", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));

        formDescriptor.addField("sector", Sector.class, "Settore", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
        if (d.getSector().getType().equals(Sector.sector_edile)){
            formDescriptor.addField("paritethic", Sector.class, "Ente", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
        }else{
            formDescriptor.addField("workerCompany", Azienda.class, "Azienda", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
        }

        formDescriptor.addField("attachment", String.class, "Scansione", "Dati generali", applicationContext.getBean(DelegaFileRenderer.class));
       // formDescriptor.addField("nomeattachment", String.class, "", "Dati generali", applicationContext.getBean(HiddenFieldRenderer.class));




        switch (d.getState()) {
            case Delega.state_accepted:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("sendDate", Date.class, "Data inoltro", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("acceptDate", Date.class, "Data accettazione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));

                break;
            case Delega.state_subscribe:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(CausaleIscrizioneDelegaSelectFieldRenderer.class));
                break;
            case Delega.state_sent:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("sendDate", Date.class, "Data inoltro", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                break;
            case Delega.state_activated:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("sendDate", Date.class, "Data inoltro", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("acceptDate", Date.class, "Data accettazione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("activationDate", Date.class, "Data attivazione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));

                break;
            case Delega.state_revoked:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("revokeDate", Date.class, "Data revoca", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("revokeReason", CausaleRevoca.class, "Causale revoca", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));

                //se la delega è a fine corsa mostro anche lo stato precedetne
                formDescriptor.addField("preecedingState", String.class, "Stato precedente", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));


                break;
            case Delega.state_cancelled:
                formDescriptor.addField("subscribeReason", CausaleIscrizioneDelega.class, "Causale iscrizione", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("cancelDate", Date.class, "Data annullamento", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("cancelReason", CausaleRevoca.class, "Causale annullamento", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));
                formDescriptor.addField("preecedingState", String.class, "Stato precedente", "Dati generali", applicationContext.getBean(ReadOnlyTextFieldRenderer.class));

                break;
            default:
                break;
        }

        formDescriptor.addField("notes", String.class, "Note", "Dati generali", applicationContext.getBean(TextAreaFieldRenderer.class));
        formDescriptor.addField("collaborator", Collaboratore.class, "Collaboratore", "Dati collaboratore comunale", applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class));

        if (d.getSector().getDescription().equals(Sector.sector_edile))
            formDescriptor.addField("firstAziendaEdile", Azienda.class, "Azienda edile", "ATTENZIONE: SOLO come riferimento all'azienda per il settore edile. Non sarà inclusa in eventuali ricerche", applicationContext.getBean(AziendeSingleSearchFieldRenderer.class));
        formDescriptor.addField("validityDate", String.class, "Data validità (impianti fissi)", "Dati generali", applicationContext.getBean(DefaultFieldRenderer.class));


        return form;
    }

    private Form generateWorkerForm() {
        Form form = new Form();
        form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
        form.setIdentifier("worker");

        FormDescriptor formDescriptor = new FormDescriptor(form);
        formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");;
        formDescriptor.addField("surname", String.class, "Cogn.", "Dati anagrafici", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("name", String.class, "Nome", "Dati anagrafici", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");

        formDescriptor.addField("sex", String.class, "Sesso", "Dati anagrafici", applicationContext.getBean(SexSelectFieldRenderer.class)).putParam(Params.COLS, Values.COLS_5).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("birthDate", String.class, "Data nasc.", "Dati anagrafici", applicationContext.getBean(DatePickerRenderer.class)).putParam(Params.COLS, Values.COLS_7).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("nationality", String.class, "Nazione di nascita", "Dati anagrafici", applicationContext.getBean(OptionalStateFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("birthProvince", String.class, "Prov. nasc.", "Dati anagrafici", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt3").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("birthPlace", String.class, "Com. nasc.", "Dati anagrafici", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt3").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("fiscalcode", String.class, "Cod. fis.", "Dati anagrafici", applicationContext.getBean(FiscalCodeFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt4").putParam(Params.FORM_COLUMN, " ");
        formDescriptor.addField("image", String.class, "Immagine", "Dati anagrafici", applicationContext.getBean(UserImageFieldRenderer.class)).putParam(Params.COLS, Values.COLS_10).putParam(Params.ROW, "dt5").putParam(Params.FORM_COLUMN, " ");

        formDescriptor.addField("livingProvince", String.class, "Provincia", "Residenza", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("livingCity", String.class, "Comune", "Residenza", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("address", String.class, "Indir.", "Residenza", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt7").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("cap", String.class, "CAP", "Residenza", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt7").putParam(Params.FORM_COLUMN, "  ");

        formDescriptor.addField("phone", String.class, "Tel.", "Comunicazioni", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("cellphone", String.class, "Cell.", "Comunicazioni", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("mail", String.class, "E-mail", "Comunicazioni", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt9").putParam(Params.FORM_COLUMN, "  ");

        formDescriptor.addField("ce", String.class, "C.E.", "Varie", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt10").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("ec", String.class, "E.C.", "Varie", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt10").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("fund", String.class, "Fondo", "Varie", applicationContext.getBean(FundSelectRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt11").putParam(Params.FORM_COLUMN, "  ");
        formDescriptor.addField("notes", String.class, "Note", "Varie", applicationContext.getBean(TextAreaFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt12").putParam(Params.FORM_COLUMN, "  ");

        return form;
    }
}
