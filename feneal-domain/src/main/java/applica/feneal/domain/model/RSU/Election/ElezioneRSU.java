package applica.feneal.domain.model.RSU.Election;

import applica.feneal.domain.model.RSU.Dto.*;
import applica.feneal.domain.model.RSU.Election.CalcoloEsitoVotaizone.CalcolatoreAttribuzioneFactory;
import applica.feneal.domain.model.RSU.Election.Math.CalcolatoreParametriAttribuzione;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
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

        PattoSolidarieta pattoSolidarieta = new PattoSolidarieta();
        pattoSolidarieta.setAccordoSolidarieta(PattoSolidarieta.TipoAccordoSolidarieta.Interconfederale);
        pattoSolidarieta.setPercentualeSogliaInterconfederale(50);
        pattoSolidarieta.setPercentualeSogliaCategoria(40);
        pattoSolidarieta.setPercentualeSogliaInCasoListaDominante(30);
        pattoSolidarieta.setCriterioSolidarietaApplicabile(false);
        e.setSolidarieta(pattoSolidarieta);

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



    ///--------------------------FROM DTO------------------------
    public ElezioneRSU fromDto(ElezioneDto dto) {
        ElezioneRSU elezione = this;

        elezione.setAzienda(dto.getAzienda());
        elezione.setValidationError(dto.getValidationError());
        elezione.setAnno(dto.getAnno());
        elezione.setDivisione(dto.getDivisione());
        elezione.setSolidarieta(dto.getSolidarieta());

        if(dto.getListe() != null && dto.getListe().size() > 0){
            elezione.setListe(setListeElettoraleFromDto(dto.getListe()));
        }else {
        elezione.setListe(new ArrayList<ListaElettorale>());
        }

        if(dto.getEsiti() != null){
            elezione.setEsiti(setEsitiFromDto(dto.getEsiti().getEsitoVotazione()));
        }

        return elezione;
    }

    private Esiti setEsitiFromDto(EsitoVotazioneDto esitoVotazione) {
        Esiti e = new Esiti();

        EsitoVotazione esitVot = new EsitoVotazione();
        esitVot.setSchedeNulle(esitoVotazione.getSchedeNulle());
        esitVot.setSchedeBianche(esitoVotazione.getSchedeBianche());
        esitVot.setAventiDiritto(esitoVotazione.getAventiDiritto());
        esitVot.setrSUElegibili(esitoVotazione.getrSUElegibili());
        esitVot.setCalcoloQuozienteElettoraleConSchedeNulle(esitoVotazione.isCalcoloQuozienteElettoraleConSchedeNulle());
        esitVot.setVotazioni(setVotantiFormDto(esitoVotazione.getVotazioni()));

        e.setEsitoVotazione(esitVot);

        return e;
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




    //-----------TO DTO-------------------------------



    public ElezioneDto toDto(ElezioneRSU elezioneRSU){
        ElezioneDto elezione = new ElezioneDto();

        elezione.setAzienda(elezioneRSU.getAzienda());
        elezione.setValidationError(elezioneRSU.getValidationError());
        elezione.setAnno(elezioneRSU.getAnno());
        elezione.setDivisione(elezioneRSU.getDivisione());
        elezione.setSolidarieta(elezioneRSU.getSolidarieta());

        if(elezioneRSU.getListe() != null && elezioneRSU.getListe().size() > 0){
            elezione.setListe(setListeElettoraliToDto(elezioneRSU.getListe()));
        }else {
            elezione.setListe(new ArrayList<ListaElettoraleDto>());
        }

        if(elezioneRSU.getEsiti() != null){
            elezione.setEsiti(setEsitiToDto(elezioneRSU.getEsiti().getEsitoVotazione()));
        }else {
            elezione.setEsiti(new EsitiDto());
        }

        return elezione;
    }

    private EsitiDto setEsitiToDto(EsitoVotazione esitoVotazione) {
        EsitiDto e = new EsitiDto();
        EsitoVotazioneDto dto = new EsitoVotazioneDto();

        dto.setVotazioni(setVotantiToDto(esitoVotazione.getVotazioni()));
        dto.setSchedeNulle(esitoVotazione.getSchedeNulle());
        dto.setSchedeBianche(esitoVotazione.getSchedeBianche());
        dto.setAventiDiritto(esitoVotazione.getAventiDiritto());
        dto.setrSUElegibili(esitoVotazione.getRSUElegibili());
        dto.setCalcoloQuozienteElettoraleConSchedeNulle(esitoVotazione.getCalcoloQuozienteElettoraleConSchedeNulle());

        e.setEsitoVotazione(dto);
        return e;
    }

    private List<VotazioneDto> setVotantiToDto(List<Votazione> votazioni) {
        List<VotazioneDto> list = new ArrayList<>();
        for (Votazione v : votazioni){
            VotazioneDto votazione = new VotazioneDto();
            votazione.setVoti(v.getVoti());

            ListaElettoraleDto l = new ListaElettoraleDto();
            l.setName(v.getLista().getNome());
            l.setFirmataria(v.getLista().getFirmatariaCCNL());
            l.setValidationError(v.getLista().getValidationError());

            votazione.setLista(l);
            list.add(votazione);
        }

        return list;
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

    public void deleteFromLista(String nome) {
        this.validationError = "";
        if(StringUtils.isEmpty(nome.trim())|| nome == null){
            this.validationError = "Il nome della lista non è stato trovato";
            return;
        }

        Iterator<ListaElettorale> l = this.liste.iterator();
        while (l.hasNext()){
            ListaElettorale ll = l.next();
            if(ll.getNome().toLowerCase().equals(nome.toLowerCase()))
                l.remove();
        }


    }

    public void editFromList(String oldnome, Boolean oldfirmataria,String nome, Boolean firmataria) {
        this.validationError = "";
        if(StringUtils.isEmpty(oldnome.trim())|| oldnome == null){
            this.validationError = "Il nome della lista non è stato trovato";
            return;
        }

        if(!StringUtils.equals(nome.toUpperCase(),oldnome.toUpperCase())){
            if(ExistLista(nome)){
                this.validationError = "Il nome della lista è già presente";
                return;
            }

            this.deleteFromLista(oldnome);
        }else{
            this.deleteFromLista(oldnome);
        }


    }
}
