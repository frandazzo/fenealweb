package applica.feneal.admin.crudconfig;


import applica.feneal.admin.data.RSU.VerbalizzazioneVotazioneRepositoryWrapper;
import applica.feneal.admin.fields.renderers.DocumentFileRenderer;
import applica.feneal.admin.fields.renderers.empty.EmptyFieldRenderer;
import applica.feneal.domain.data.core.RSU.VerbalizzazioneVotazioneRepository;
import applica.feneal.domain.model.core.RSU.VerbalizzazioneVotazione;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;

import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.ReadOnlyFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;

import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna 12/03/2021.
 */
@Component
public class VerbVotazioneCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(VerbalizzazioneVotazione.class, "verbvot")
                .repository(VerbalizzazioneVotazioneRepositoryWrapper.class)
                .field("aziendaRsu", "Azienda RSU", HiddenFieldRenderer.class)
                .field("sedeRsu", "Sede RSU", HiddenFieldRenderer.class)
                .field("contrattoRSU", "Contratto RSU", HiddenFieldRenderer.class)
                .field("dataVotazione", "Data", HiddenFieldRenderer.class)
                .field("verbalizzazione", "Verbalizzazione", DocumentFileRenderer.class)
                .field("nomeverbalizzazione", "nomeverbalizzazione", EmptyFieldRenderer.class)
                .field("risultatoVotazione", "Risultato Votazione", DocumentFileRenderer.class)
                .field("nomerisultatoVotazione", "nomerisultatoVotazione", EmptyFieldRenderer.class)
                .field("note", "Note", TextAreaFieldRenderer.class);

//
        GridConfigurator.configure(VerbalizzazioneVotazione.class, "verbvot")
                .repository(VerbalizzazioneVotazioneRepositoryWrapper.class)
                .column("aziendaRsu", "Azienda", true)
                .column("sedeRsu", "Sede", false)
                .column("contrattoRSU", "Contratto", false)
                .column("dataVotazione", "Data", false);
    }
}
