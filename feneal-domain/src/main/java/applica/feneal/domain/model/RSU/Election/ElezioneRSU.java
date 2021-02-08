package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone.CalcolatoreAttribuzioneFactory;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ElezioneRSU {

    private String validationError = "";
    private PattoSolidarieta solidarieta = new PattoSolidarieta();
    private List<ListaElettorale> liste = new ArrayList();
    private Esiti esiti = new Esiti();
    private int anno;
    private String divisione;
    private String azienda;

    public int getAnno(){
        return anno;
    }


    public String getDivisione() {
        return divisione;
    }

    public String getAzienda() {
        return azienda;
    }

    public PattoSolidarieta getSolidarieta(){
        return solidarieta;
    }

    public List getPartecipanti(){
        return liste;
    }

    public Esiti getEsiti(){
        return esiti;
    }

    private ElezioneRSU()
    {
    }


    public void Initialize()
    {
        this.anno = new GregorianCalendar().get(Calendar.YEAR);
        this.getSolidarieta().setAccordoSolidarieta(PattoSolidarieta.TipoAccordoSolidarieta.Categoria);
        this.getSolidarieta().setPercentualeSogliaInterconfederale(50);
        this.getSolidarieta().setPercentualeSogliaCategoria(40);
        this.getSolidarieta().setPercentualeSogliaInCasoListaDominante(30);
        this.getSolidarieta().setCriterioSolidarietaApplicabile(false);

    }



    public String getValidationError() {
        return this.validationError;
    };

    public boolean CheckListeData()
    {
        this.validationError = "";
        if (this.liste.size() <= 1)
            this.validationError = "Inserire almeno due liste <br/>";
        if (StringUtils.isEmpty(this.validationError))
            return true;
        return false;
    }

    public boolean CheckHeaderData()
    {
        this.validationError = "";
        if (StringUtils.isEmpty(this.azienda))
            this.validationError = "L'azienda deve essere specificata <br/>";
        if (this.anno <= 2010)
            this.validationError += "Specificare un anno valido";
        if (StringUtils.isEmpty(this.validationError))
            return true;

        return false;
    }

    public boolean CheckEsitoVotazioneData() throws Exception {
        if (this.esiti == null)
            throw new Exception("Esiti nulli");
        if (this.esiti.getEsitoVotazione() == null)
            throw new Exception("EsitoVotazione nullo");
        return  this.esiti.getEsitoVotazione().checkValidation();
    }

    public boolean CheckSolidarietaData() throws Exception {
        this.validationError = "";
        if (this.solidarieta == null)
            throw new Exception("Solidarietà nulla");
        if (this.solidarieta.getPercentualeSogliaCategoria() < 0 || this.solidarieta.getPercentualeSogliaCategoria() > 100)
            this.validationError = "Specificare una percentuale di soglia di sbarramento categoria valida (compresa tra 0 e 100) <br/>" ;
        if (this.solidarieta.getPercentualeSogliaInterconfederale() < 0 || this.solidarieta.getPercentualeSogliaInterconfederale() > 100)
            this.validationError = "Specificare una percentuale di soglia di sbarramento interconfederale valida (compresa tra 0 e 100) <br/>" ;
        if (this.solidarieta.getPercentualeSogliaInCasoListaDominante() < 0 || this.solidarieta.getPercentualeSogliaInCasoListaDominante() > 100)
            this.validationError = "Specificare una percentuale di soglia di sbarramento valida in caso di lista dominante (compresa tra 0 e 100)";
        if (StringUtils.isEmpty(this.validationError))
            return true;

        return false;
    }



    public ParametriAttribuzioneRSU CalcolaParametriAttribuzione() throws Exception {
        return CalcolatoreParametriAttribuzione.CalcolaParametriAttribuzione(this.esiti.getEsitoVotazione(), this.solidarieta);
    }

    public void CalcolaEsitoAttribuzioneRSU() throws Exception {
        EsitoAttribuzioneRSU esito = CalcolatoreAttribuzioneFactory.GetStrategy(false).CalcolaEsitoAttribuzione(this);
        this.esiti.setAttribuzioneRSU(esito);

    }



}
