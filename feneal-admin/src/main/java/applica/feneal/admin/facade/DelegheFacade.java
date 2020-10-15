package applica.feneal.admin.facade;

import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiAppDelega;
import applica.feneal.admin.viewmodel.deleghe.*;
import applica.feneal.admin.viewmodel.reports.UiDelega;
import applica.feneal.domain.data.core.*;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDeleghe;
import applica.feneal.domain.model.core.deleghe.UiDelegheReportSearchParams;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.services.DelegheService;
import applica.feneal.services.ListaLavoroService;
import applica.feneal.services.ReportDelegheService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 14/04/2016.
 */
@Component
public class DelegheFacade {

    @Autowired
    private DelegheService delegheService;

    @Autowired
    private RevocationReasonRepository revRep;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private LavoratoriRepository lavoratoriRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ParitheticRepository paritheticRepository;

    @Autowired
    private AziendeRepository aziendeRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SignupDelegationReasonRepository subscribeReason;

    @Autowired
    private ReportDelegheService delService;

    @Autowired
    private ListaLavoroService lSrv;

    @Autowired
    private Security security;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private ApplicationOptionRepository appRep;

    public List<UIDelega> getAllWorkerDeleghe(long workerId) {
        List<Delega> delegas = delegheService.getAllWorkerDeleghe(workerId);
        List<UIDelega> uiDelegaFormEntities = new ArrayList<>();
        for (Delega delega: delegas) {
            uiDelegaFormEntities.add(getUIDelegaFromModelEntity(delega));
        }
        return uiDelegaFormEntities;
    }

    private UIDelega getUIDelegaFromModelEntity(Delega delega) {
        UIDelega uiDelega = new UIDelega();
        uiDelega.setState(delega.getState());
        uiDelega.setId(String.valueOf(delega.getId()));
        uiDelega.setSupportedNextStates(delegheService.getDelegaPermittedNextStates(delega, appRep.find(null).getRows()));
        uiDelega.setDocumentDate(FenealDateUtils.getStringFromDate(delega.getDocumentDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setValidityDate(FenealDateUtils.getStringFromDate(delega.getValidityDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));

        uiDelega.setSendDate(FenealDateUtils.getStringFromDate(delega.getSendDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setActivationDate(FenealDateUtils.getStringFromDate(delega.getActivationDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setRevokeDate(FenealDateUtils.getStringFromDate(delega.getRevokeDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setCancelDate(FenealDateUtils.getStringFromDate(delega.getCancelDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setAcceptDate(FenealDateUtils.getStringFromDate(delega.getAcceptDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER));
        uiDelega.setSector(delega.getSector() != null ? delega.getSector().toString() : null);
        uiDelega.setParitethic(delega.getParitethic() != null? delega.getParitethic().toString() : null);
        if (delega.getWorker() != null) {
            uiDelega.setWorker(delega.getWorker() != null? delega.getWorker().toString() : null);
            uiDelega.setWorkerId(String.valueOf(delega.getWorker().getLid()));
        }

        uiDelega.setWorkerCompany(delega.getWorkerCompany() != null? delega.getWorkerCompany().toString() : null);
        uiDelega.setWorkerCompanyId(delega.getWorkerCompany() != null? Long.toString(delega.getWorkerCompany().getLid()) : null);
        uiDelega.setFirstAziendaEdile(delega.getFirstAziendaEdile() != null? delega.getFirstAziendaEdile().toString() : null);
        uiDelega.setProvince(delega.getProvince() != null? delega.getProvince().toString() : null);
        uiDelega.setSubscribeReason(delega.getSubscribeReason() != null? delega.getSubscribeReason().toString() : null);
        uiDelega.setRevokeReason(delega.getRevokeReason() != null? delega.getRevokeReason().toString() : null);
        uiDelega.setNotes(delega.getNotes());
        uiDelega.setCollaborator(delega.getCollaborator() != null? delega.getCollaborator().getDescription():"");
        uiDelega.setContract(delega.getContract() != null ? delega.getContract().getDescription() : "");
        return uiDelega;
    }

    public List<UiDelega> reportDeleghe(UiDelegheReportSearchParams params) {
        List<Delega> del = delService.retrieveDeleghe(params);

        return convertDelegheToUiDeleghe(del);
    }


    private List<UiDelega> convertDelegheToUiDeleghe(List<Delega> del) {

        List<UiDelega> result = new ArrayList<>();

        for (Delega delega : del) {
            UiDelega d = new UiDelega();


            d.setContract(delega.getContract() != null ? delega.getContract().getDescription() : "");

            d.setDelegaProvincia(delega.getProvince().getDescription());
            if (delega.getSubscribeReason() != null)
                d.setDelegaCausaleSottoscrizione(delega.getSubscribeReason().getDescription());
            if (delega.getRevokeReason() != null)
                d.setDelegaCausaleRevoca(delega.getRevokeReason().getDescription());
            if (delega.getCancelReason() != null)
                d.setDelegaCausaleAnnullamento(delega.getCancelReason().getDescription());

            if (!StringUtils.isEmpty(delega.getNomeattachment()))
                d.setDelegaScansione(delega.getNomeattachment());

            d.setValidityDate(delega.getValidityDate());
            d.setDelegaDataDocumento(delega.getDocumentDate());

                d.setDelegaDataInoltro(delega.getSendDate());

                d.setDelegaDataAccettazione(delega.getAcceptDate());

                d.setDelegaDataAttivazione(delega.getActivationDate());

                d.setDelegaDataAnnullamento(delega.getCancelDate());

                d.setDelegaDataRevoca(delega.getRevokeDate());


            if (delega.getSector() != null)
                d.setDelegaSettore(delega.getSector().getDescription());
            if (delega.getParitethic() != null)
                d.setDelegaEnteBilaterale(delega.getParitethic().getDescription());
            if (delega.getCollaborator()!= null)
                d.setDelegaCollaboratore(delega.getCollaborator().getDescription());

            d.setDelegaStato(getDelegaStateString(delega.getState()));
            d.setDelegaId(delega.getLid());
            d.setDelegaNote(delega.getNotes());
            d.setLavoratoreId(delega.getWorker().getLid());
            d.setLavoratoreCap(delega.getWorker().getCap());
            d.setLavoratoreCellulare(delega.getWorker().getCellphone());
            d.setLavoratorMail(delega.getWorker().getMail());
            d.setLavoratoreTelefono(delega.getWorker().getPhone());
            d.setLavoratoreCittaResidenza(delega.getWorker().getLivingCity());
            d.setLavoratoreCodiceFiscale(delega.getWorker().getFiscalcode());
            d.setLavoratoreCognome(delega.getWorker().getSurname());

            d.setLavoratoreDataNascita(delega.getWorker().getBirthDate());
            d.setLavoratoreIndirizzo(delega.getWorker().getAddress());
            d.setLavoratoreLuogoNascita(delega.getWorker().getBirthPlace());
            d.setLavoratoreNazionalita(delega.getWorker().getNationality());
            d.setLavoratoreNome(delega.getWorker().getName());
            d.setLavoratoreCittaResidenza(delega.getWorker().getLivingCity());
            d.setLavoratoreProvinciaNascita(delega.getWorker().getBirthProvince());
            d.setLavoratoreProvinciaResidenza(delega.getWorker().getLivingProvince());
            if (delega.getWorker().getSex().equals("M"))
                d.setLavoratoreSesso(Lavoratore.MALE);
            else
                d.setLavoratoreSesso(Lavoratore.FEMALE);

            d.setLavoratoreCodiceCassaEdile(delega.getWorker().getCe());
            d.setLavoratoreCodiceEdilcassa(delega.getWorker().getEc());
            if (delega.getWorker().getFund() != null)
                d.setLavoratoreFondo(delega.getWorker().getFund().getDescription());
            d.setLavoratoreNote(delega.getWorker().getNotes());

            if (delega.getWorkerCompany() != null) {
                d.setAziendaRagioneSociale(delega.getWorkerCompany().getDescription());
                d.setAziendaCitta(delega.getWorkerCompany().getCity());
                d.setAziendaProvincia(delega.getWorkerCompany().getProvince());
                d.setAziendaCap(delega.getWorkerCompany().getCap());
                d.setAziendaIndirizzo(delega.getWorkerCompany().getAddress());
                d.setAziendaNote(delega.getWorkerCompany().getNotes());
                d.setAziendaId(delega.getWorkerCompany().getLid());
            }
            if (delega.getFirstAziendaEdile() != null) {
                d.setFirstAziendaEdile(delega.getFirstAziendaEdile().getDescription());

            }


            result.add(d);
        }

        return result;
    }

    private String getDelegaStateString(Integer delegaState) {

        switch (delegaState) {
            case Delega.state_accepted:
                return "Accettata";
            case Delega.state_subscribe:
                return "Sottoscritta";
            case Delega.state_sent:
                return "Inoltrata";
            case Delega.state_activated:
                return "Attivata";
            case Delega.state_revoked:
                return "Revocata";
            case Delega.state_cancelled:
                return "Cancellata";
            default:
                return "";
        }
    }


    public UISaveDelegaResponse saveDelega(UIDelega delega) throws Exception {
        Delega l = convertUIDelegaToModelEntity(delega);
        //se sono utent elombardo

        User u = ((User) security.getLoggedUser());

        if(u.getCompany().getRegionId() == 30){
            if (StringUtils.isEmpty(l.getNomeattachment()))
                throw new Exception("La scansione della delega è obbligatoria");
        }

        delegheService.saveOrUpdate(((User) security.getLoggedUser()).getLid(),l);

        //Restituisco l'oggetto delega in quanto per la navigazione è necessario sia l'id della delega
        // che quello del suo lavoratore
        return getSaveDelegaResponse(l);
    }

    private UISaveDelegaResponse getSaveDelegaResponse(Delega l) {
        UISaveDelegaResponse response = new UISaveDelegaResponse();
        response.setId(l.getLid());
        response.setWorkerId(l.getWorker().getLid());
        return response;
    }

    private Delega convertUIDelegaToModelEntity(UIDelega uiDelega) {
        Delega delega = new Delega();
        delega.setId(uiDelega.getId());
        if(!StringUtils.isEmpty(uiDelega.getAttachment())){
            delega.setAttachment(uiDelega.getAttachment());
            delega.setNomeattachment(uiDelega.getNomeattachment());
        }else{
            delega.setAttachment(uiDelega.getAttachment());
            delega.setNomeattachment("");
        }


        try {
            delega.setValidityDate(StringUtils.hasLength(uiDelega.getValidityDate()) ? FenealDateUtils.getDateFromString(uiDelega.getValidityDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER): null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        delega.setNotes(uiDelega.getNotes());
        try {
            delega.setDocumentDate(StringUtils.hasLength(uiDelega.getDocumentDate()) ? FenealDateUtils.getDateFromString(uiDelega.getDocumentDate(), FenealDateUtils.FORMAT_DATE_DATEPICKER): null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (StringUtils.hasLength(uiDelega.getSector())) {
            try {
                LoadRequest req = LoadRequest.build().filter("type", uiDelega.getSector());
                delega.setSector(sectorRepository.find(req).findFirst().orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.hasLength(uiDelega.getParitethic())) {
            try {
                long parithethicId = Long.parseLong(uiDelega.getParitethic());
                delega.setParitethic(paritheticRepository.get(parithethicId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (StringUtils.hasLength(uiDelega.getContract())) {
            try {
                long contractId = Long.parseLong(uiDelega.getContract());
                delega.setContract(contractRepository.get(contractId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (StringUtils.hasLength(uiDelega.getWorkerCompany())) {
            try {
                long aziendaId = Long.parseLong(uiDelega.getWorkerCompany());
                delega.setWorkerCompany(aziendeRepository.get(aziendaId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (StringUtils.hasLength(uiDelega.getFirstAziendaEdile())) {
            try {
                long aziendaId = Long.parseLong(uiDelega.getFirstAziendaEdile());
                delega.setFirstAziendaEdile(aziendeRepository.get(aziendaId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }



        if (StringUtils.hasLength(uiDelega.getCollaborator())) {
            try {
                long collaboratoreId = Long.parseLong(uiDelega.getCollaborator());
                delega.setCollaborator(collaboratorRepository.get(collaboratoreId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.hasLength(uiDelega.getWorkerId())) {
            try {
                long lavoratoreId = Long.parseLong(uiDelega.getWorkerId());
                delega.setWorker(lavoratoriRepository.get(lavoratoreId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(uiDelega.getProvince())) {
            try {
                Integer provinceID = Integer.parseInt(uiDelega.getProvince());
                delega.setProvince(provinceRepository.get(provinceID).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(uiDelega.getSubscribeReason())) {
            try {
                Long subscribeReasonId = Long.parseLong(uiDelega.getSubscribeReason());
                delega.setSubscribeReason(subscribeReason.get(subscribeReasonId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        //lo stato non va modificato dal form
        //le date relative agli stati non devono poter essere modificate...


        return delega;
    }

    public void deleteDelega(long delegaId) {
        delegheService.deleteDelega(((User) security.getLoggedUser()).getLid(), delegaId);
    }

    public Delega getDelegaById(Long id) {
        return delegheService.getDelegaById(id);
    }


    public void changeState(UiDelegaChangeState state) {

        Date d = parseDate(state);



        Delega delega = getDelegaById(Long.parseLong(state.getDelegaId()));
        switch (Integer.parseInt(state.getState())) {
            case Delega.state_accepted:

                delegheService.acceptDelega(d, delega);
                return;
            case Delega.state_subscribe:
                delegheService.subscribeDelega(delega);
                return;
            case Delega.state_sent:
                delegheService.sendDelega(d, delega);
                return;
            case Delega.state_activated:
                delegheService.activateDelega(d, delega);
                return;
            case Delega.state_revoked:
                CausaleRevoca c = retrieveCausale(state.getCausaleId());
                delegheService.revokeDelega(d, delega, c);
                return;
            case Delega.state_cancelled:

                CausaleRevoca c1 = retrieveCausale(state.getCausaleId());
                delegheService.cancelDelega(d, delega, c1);
                return;
            case Delega.ACTION_BACK:
                delegheService.goBack(delega);
                return;
        }
    }

    private CausaleRevoca retrieveCausale(String causaleId) {
        try{
            return revRep.get(Integer.parseInt(causaleId)).orElse(null);
        }catch (Exception e){
            return null;
        }
    }

    private Date parseDate(UiDelegaChangeState state) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = f.parse(state.getDate());
        } catch (Exception e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public void revertState(Long id) {
        Delega delega = getDelegaById(id);
        delegheService.goBack(delega);
    }

    public void inoltreDeleghe(UiInoltraDeleghe uiSend)  {
        List<Delega> deleghe = new ArrayList<>();

        for (UiDelega uiDelega : uiSend.getSelectedDeleghe()) {
            Delega del = delegheService.getDelegaById(uiDelega.getDelegaId());
            if (del != null)
                deleghe.add(del);
        }


        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        try {
            d = f.parse(uiSend.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        delegheService.inoltraDeleghe(deleghe, d);
    }

    public void accettaDeleghe(UiAccettaDeleghe uiSend)  {
        List<Delega> deleghe = new ArrayList<>();

        for (UiDelega uiDelega : uiSend.getSelectedDeleghe()) {
            Delega del = delegheService.getDelegaById(uiDelega.getDelegaId());
            if (del != null)
                deleghe.add(del);
        }


        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        try {
            d = f.parse(uiSend.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        delegheService.accettaDeleghe(deleghe, d);
    }

    public ListaLavoro createListalavoro(List<UiDelega> deleghe, String description) throws Exception {
        List<Delega> com = convertUiToDeleghe(deleghe);
        return lSrv.createListaFromDeleghe(com, description);
    }

    private List<Delega> convertUiToDeleghe(List<UiDelega> deleghe) {
        List<Delega> a = new ArrayList<>();

        for (UiDelega uiDelega : deleghe) {
            Delega s = new Delega();
            s.setWorker(lavRep.get(uiDelega.getLavoratoreId()).orElse(null));

            //non mi serviranno gli altri campi.... dato che utilizzo il metodo solo per la crerazione
            //della òlista di lavoro

            a.add(s);
        }

        return a;
    }



    public String saveDelegaForApp(UiAppDelega delega) throws Exception {
        Delega l = convertUIAppDelegaToModelEntity(delega);

        delegheService.saveOrUpdate(((User) security.getLoggedUser()).getLid(),l);

        return "OK";
    }

    private Delega convertUIAppDelegaToModelEntity(UiAppDelega uiDelega) {
        Delega delega = new Delega();

        delega.setId(uiDelega.getId());
        delega.setNotes(uiDelega.getNote());

        delega.setDocumentDate(new Date());


        if (StringUtils.hasLength(uiDelega.getSettore())) {
            try {
                LoadRequest req = LoadRequest.build().filter("type", uiDelega.getSettore());
                delega.setSector(sectorRepository.find(req).findFirst().orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (uiDelega.getSettore().equals("EDILE")){
            if (StringUtils.hasLength(uiDelega.getEnte())) {
                try {
                    long parithethicId = Long.parseLong(uiDelega.getEnte());
                    delega.setParitethic(paritheticRepository.get(parithethicId).orElse(null));
                }catch (Exception e) {

                    //se ho inviato il testo ...
                    Paritethic t = paritheticRepository.find(LoadRequest.build().filter("type", uiDelega.getEnte(), Filter.EQ)).findFirst().orElse(null);
                    delega.setParitethic(t);

                }
            }
        }


        if (!uiDelega.getSettore().equals("EDILE")){
            if (StringUtils.hasLength(uiDelega.getAzienda())) {
                try {
                    long aziendaId = Long.parseLong(uiDelega.getAzienda());


                    delega.setWorkerCompany(aziendeRepository.get(aziendaId).orElse(null));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (StringUtils.hasLength(uiDelega.getCollaboratore())) {
            try {
                long collaboratoreId = Long.parseLong(uiDelega.getCollaboratore());
                delega.setCollaborator(collaboratorRepository.get(collaboratoreId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.hasLength(uiDelega.getIdLavoratore())) {
            try {
                long lavoratoreId = Long.parseLong(uiDelega.getIdLavoratore());
                delega.setWorker(lavoratoriRepository.get(lavoratoreId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(uiDelega.getProvincia())) {
            try {

                delega.setProvince(provinceRepository.find(LoadRequest.build().filter("description",uiDelega.getProvincia(), Filter.EQ)).findFirst().orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.hasLength(uiDelega.getCausaleIscrizione())) {
            try {
                Long subscribeReasonId = Long.parseLong(uiDelega.getCausaleIscrizione());
                delega.setSubscribeReason(subscribeReason.get(subscribeReasonId).orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


        //lo stato non va modificato dal form
        //le date relative agli stati non devono poter essere modificate...


        return delega;
    }

    public String importDeleghePerBariCassaEdile(ImportDeleghe file) throws Exception {
        return delegheService.importaDelegheBariCassaEdile(file);
    }

    public List<UiDelega> reportDelegheForInoltroOrAccettazione(UiDelegheReportSearchParams params) {
        List<Delega> del = delService.retrieveDelegheForInoltroEAccettazioneMassivo(params);

        return convertDelegheToUiDeleghe(del);

    }
}
