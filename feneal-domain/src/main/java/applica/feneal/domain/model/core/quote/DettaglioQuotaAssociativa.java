package applica.feneal.domain.model.core.quote;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.*;

/**
 * Created by fgran on 20/05/2016.
 */
public class DettaglioQuotaAssociativa extends SecuredDomainEntity{

    //uso tabelle fortemente denormalizzate
    //riferimento al padre
    private long idRiepilogoQuotaAssociativa;

    private String provincia;
    private Date dataRegistrazione;
    private Date dataDocumento;
    private String tipoDocumento;
    private String settore;
    private String ente;
    private Date dataInizio;
    private Date dataFine;
    private double quota;
    private String livello;
    private String contratto;
    private String tipoPrestazione;
    private String note;

    private long idAzienda;
    private long idLavoratore;


    private transient int id_Iscrizione;



    public String getTipoPrestazione() {
        return tipoPrestazione;
    }

    public void setTipoPrestazione(String tipoPrestazione) {
        this.tipoPrestazione = tipoPrestazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public int getId_Iscrizione() {
        return id_Iscrizione;
    }

    public void setId_Iscrizione(int id_Iscrizione) {
        this.id_Iscrizione = id_Iscrizione;
    }

    public long getIdRiepilogoQuotaAssociativa() {
        return idRiepilogoQuotaAssociativa;
    }

    public void setIdRiepilogoQuotaAssociativa(long idRiepilogoQuotaAssociativa) {
        this.idRiepilogoQuotaAssociativa = idRiepilogoQuotaAssociativa;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public double getQuota() {
        return quota;
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }

    public long getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(long idAzienda) {
        this.idAzienda = idAzienda;
    }

    public long getIdLavoratore() {
        return idLavoratore;
    }

    public void setIdLavoratore(long idLavoratore) {
        this.idLavoratore = idLavoratore;
    }


    public static DettaglioQuotaAssociativa createFromIscrizione(Iscrizione iscrizione, Lavoratore lavoratore, Azienda azienda, long companyId) throws Exception {
        DettaglioQuotaAssociativa q = new DettaglioQuotaAssociativa();
        q.setCompanyId(companyId);
        q.setProvincia(iscrizione.getNomeProvincia());
        q.setTipoDocumento(iscrizione.getSettore().equals(Sector.sector_inps)?RiepilogoQuoteAssociative.IQI:RiepilogoQuoteAssociative.IQA);
        q.setDataRegistrazione(new Date());
        q.setDataDocumento(iscrizione.getDataFine());
        q.setSettore(iscrizione.getSettore());
        q.setContratto(iscrizione.getContratto());
        q.setDataFine(iscrizione.getDataFine());
        q.setDataInizio(iscrizione.getDataInizio());
        q.setEnte(iscrizione.getEnte());
        if (azienda != null)
            q.setIdAzienda(azienda.getLid());
        if (lavoratore == null)
            throw new Exception("Impossibile inserire l'iscrizione. lavoratore nullo");
        q.setIdLavoratore(lavoratore.getLid());
        q.setLivello(iscrizione.getLivello());
        q.setQuota(iscrizione.getQuota());


        return q;
    }

    public List<Integer> findMissingYearsFromIscrizioni(List<Iscrizione> iscrizioni) {

        List<Integer> result = new ArrayList<>();

        Calendar c = new GregorianCalendar();
        c.setTime(this.dataInizio);
        int startYear = c.get(Calendar.YEAR);

        Calendar c1 = new GregorianCalendar();
        c1.setTime(this.dataFine);
        int endYear = c1.get(Calendar.YEAR);

        //aggiungo alla lista tutti gli anni delle iscrizioni che non sono compresi
        //tra startyear e endyear
        for (int i = startYear; i <= endYear; i++) {

            //ciclo tra le diverse iscrizioni per trovare l'iscrizione per l'anno iesimo
            //se non cè la aggiungo alla lista
            boolean found = false;
            for (Iscrizione iscrizione : iscrizioni) {
                if (iscrizione.getNomeProvincia().toUpperCase().equals(this.provincia.toUpperCase()))
                    if (iscrizione.getSettore().equals(this.settore))
                        if (iscrizione.getAnno() == i)
                            found = true;

            }
            if (!found)
                result.add(i);
        }


        return result;


    }

    public Iscrizione toIscrizioneForYear(Integer integer, Lavoratore l, Azienda az, String regionName, int idProvincia) {
        Iscrizione result = new Iscrizione();

        result.setAnno(integer);
        result.setNomeProvincia(this.getProvincia());
        result.setNomeRegione(regionName);
        result.setSettore(this.getSettore());
        result.setEnte(this.getEnte());
        result.setLivello(this.getLivello());
        result.setNomeCompleto(l.getSurnamename());
        if (az != null){
            result.setAzienda(az.getDescription());
            result.setPiva(az.getPiva());
        }
        result.setId_Provincia(idProvincia);




        return result;
    }
}
