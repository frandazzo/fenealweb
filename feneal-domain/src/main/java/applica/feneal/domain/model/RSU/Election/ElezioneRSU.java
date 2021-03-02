package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Dto.*;
import applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone.CalcolatoreAttribuzioneFactory;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ElezioneRSU {

    private String validationError = "";
    private PattoSolidarieta solidarieta = new PattoSolidarieta();
    private List<ListaElettorale> liste = new ArrayList();
    private Esiti esiti = new Esiti();
    private int anno;
    private String divisione;
    private String azienda;

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public void setSolidarieta(PattoSolidarieta solidarieta) {
        this.solidarieta = solidarieta;
    }

    public List<ListaElettorale> getListe() {
        return liste;
    }

    public void setListe(List<ListaElettorale> liste) {
        this.liste = liste;
    }

    public void setEsiti(Esiti esiti) {
        this.esiti = esiti;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public void setDivisione(String divisione) {
        this.divisione = divisione;
    }

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

//    public ElezioneRSU()
//    {
//        Initialize();
//    }

    public void Initialize(ElezioneDto e)
    {

        this.getSolidarieta().setAccordoSolidarieta(PattoSolidarieta.TipoAccordoSolidarieta.Interconfederale);
        this.getSolidarieta().setPercentualeSogliaInterconfederale(50);
        this.getSolidarieta().setPercentualeSogliaCategoria(40);
        this.getSolidarieta().setPercentualeSogliaInCasoListaDominante(30);
        this.getSolidarieta().setCriterioSolidarietaApplicabile(false);

        this.fromDto(e);
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

    public ElezioneRSU fromDto(ElezioneDto dto) {
        ElezioneRSU elezione = this;

        elezione.setAzienda(dto.getAzienda());
        elezione.setValidationError(dto.getValidationError());
        elezione.setAnno(dto.getAnno());
        elezione.setDivisione(dto.getDivisione());

        if(dto.getListe() != null && dto.getListe().size() > 0){
            elezione.setListe(setListeElettoraleFromDto(dto.getListe()));
        }else {
        elezione.setListe(new ArrayList<ListaElettorale>());
        }

        if(dto.getEsiti() != null){
            elezione.setEsiti(setEsitiFromDto(dto.getEsiti()));
        }


        return elezione;
    }

    public ElezioneDto toDto(ElezioneRSU elezioneRSU){
        ElezioneDto elezione = new ElezioneDto();

        elezione.setAzienda(elezioneRSU.getAzienda());
        elezione.setValidationError(elezioneRSU.getValidationError());
        elezione.setAnno(elezioneRSU.getAnno());
        elezione.setDivisione(elezioneRSU.getDivisione());

        if(elezioneRSU.getListe() != null && elezioneRSU.getListe().size() > 0){
            elezione.setListe(setListeElettoraliToDto(elezioneRSU.getListe()));
        }else {
            elezione.setListe(new ArrayList<ListaElettoraleDto>());
        }

        return elezione;
    }

    private List<ListaElettoraleDto> setListeElettoraliToDto(List<ListaElettorale> liste) {
        List<ListaElettoraleDto> result = new ArrayList<>();
        for (ListaElettorale l : liste) {
            ListaElettoraleDto lista = new ListaElettoraleDto();
            lista.setValidationError(l.getValidationError());
            lista.setFirmataria(l.getFirmatariaCCNL());
            lista.setName(l.getNome());

            result.add(lista);
        }

        return result;
    }


    private Esiti setEsitiFromDto(EsitiDto esiti) {
        Esiti result = new Esiti();

        result.setEsitoVotazione(setEsitoVotazioneFromDto(esiti.getEsitoVotazione()));
        result.setAttribuzioneRSU(setAttribuzioniRsuFromDto(esiti.getAttribuzioneRSU()));


        return result;
    }

    private EsitoAttribuzioneRSU setAttribuzioniRsuFromDto(EsitoAttribuzioneRsuDto attribuzioneRSU) {
        EsitoAttribuzioneRSU result = new EsitoAttribuzioneRSU();

        result.setContesti(null);

        return result;
    }

    private EsitoVotazione setEsitoVotazioneFromDto(EsitoVotazioneDto esitoVotazione) {
        EsitoVotazione esitVot = new EsitoVotazione();
        esitVot.setSchedeNulle(esitoVotazione.getSchedeNulle());
        esitVot.setSchedeBianche(esitoVotazione.getSchedeBianche());
        esitVot.setAventiDiritto(esitoVotazione.getAventiDiritto());
        esitVot.setrSUElegibili(esitoVotazione.getrSUElegibili());
        esitVot.setCalcoloQuozienteElettoraleConSchedeNulle(esitoVotazione.isCalcoloQuozienteElettoraleConSchedeNulle());
        esitVot.setVotazioni(setVotantiFormDto(esitoVotazione.getVotazioni()));

        return esitVot;
    }

    private List<Votazione> setVotantiFormDto(List<VotazioneDto> votazioni) {
        List<Votazione> list = new ArrayList<>();
        for (VotazioneDto v : votazioni){
            Votazione votazione = new Votazione();
            votazione.setVoti(v.getVoti());

            ListaElettorale l = new ListaElettorale();
            l.setNome(v.getLista().getName());
            l.setFirmatariaCCNL(v.getLista().isFirmataria());

            votazione.setLista(l);
            list.add(votazione);
        }

        return list;
    }

    private List<ListaElettorale> setListeElettoraleFromDto(List<ListaElettoraleDto> liste) {
        List<ListaElettorale> result = new ArrayList<>();

        for (ListaElettoraleDto l : liste) {
            ListaElettorale lista = new ListaElettorale();
            lista.setValidationError(l.getValidationError());
            lista.setNome(l.getName());
            lista.setFirmatariaCCNL(l.isFirmataria());

            result.add(lista);
        }

        return result;
    }


    public void addLista(String nome, Boolean firmataria) {
        this.validationError = "";
        if(StringUtils.isEmpty(nome.trim())|| nome == null){
            this.validationError = "Il nome della lista non può essere nullo";
            return;
        }
        
        if(ExistLista(nome)){
            this.validationError = "Il nome della lista è già presente";
            return;
        }

        ListaElettorale newLista = new ListaElettorale(nome, firmataria);
        this.liste.add(newLista);
    }

    private boolean ExistLista(String nome) {
        for (ListaElettorale l: this.liste) {
            if(l.getNome().toLowerCase().equals(nome.toLowerCase()))
                return true;
        }
        return false;
    }
}
