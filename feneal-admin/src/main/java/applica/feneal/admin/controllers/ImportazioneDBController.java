package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.ImportazioneFacade;
import applica.feneal.admin.fields.renderers.ImportedDBCellRenderer;
import applica.feneal.admin.utils.SessionUtils;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.services.QuoteAssociativeService;
import applica.feneal.services.impl.quote.ImportReport;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.GridResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.widgets.Grid;
import applica.framework.widgets.GridDescriptor;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by angelo on 05/05/2016.
 */
@Controller
public class ImportazioneDBController {

    @Autowired
    private SessionUtils session;
    @Autowired
    private QuoteAssociativeService quosvc;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ImportazioneFacade importazioneFacade;

    @RequestMapping(value = "/importazioneDB", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse gridImportazioni(HttpServletRequest request) {

        try {
            Grid grid = new Grid();

            grid.setRenderer(applicationContext.getBean(DefaultGridRenderer.class));

            grid.setIdentifier("importazioneDB");
            grid.setFormIdentifier("importazioneDB");
            grid.setTitle("Lista importazioni");
            grid.setRowsPerPage(100);

            GridDescriptor gridDescriptor = new GridDescriptor(grid);

            //gridDescriptor.addColumn("id", "id", String.class, true, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("nomeProvincia", "Provincia", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("settore", "Settore", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("dataInvio", "Data invio", Date.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("tipoInvio", "Tipo invio", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("responsabile", "Responsabile", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("tipoPeriodo", "Tipo periodo", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("anno", "Anno", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("periodo", "Periodo", String.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("dataInizio", "Data inizio", Date.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("dataFine", "Data fine", Date.class, false, applicationContext.getBean(DefaultCellRenderer.class));
            gridDescriptor.addColumn("synched", "Importato", Boolean.class, false, applicationContext.getBean(ImportedDBCellRenderer.class));

            List<Importazione> importazioni = importazioneFacade.getImportazioniPerTerritorio();
            List<Map<String, Object>> data = new ArrayList<>();


            for (Importazione i : importazioni) {
                Map<String, Object> map = new HashMap<>();

                map.put("id", i.getId());
                map.put("nomeProvincia", i.getNomeProvincia());
                map.put("settore", i.getSettore());
                map.put("dataInvio", i.getDataInvio());
                map.put("tipoInvio", i.getTipoInvio());
                map.put("responsabile", i.getResponsabile());
                map.put("tipoPeriodo", i.getTipoPeriodo());
                map.put("anno", i.getAnno());
                map.put("periodo", i.getPeriodo());
                map.put("dataInizio", i.getDataInizio());
                map.put("dataFine", i.getDataFine());
                map.put("synched", i.isSynched());

                data.add(map);
            }

            grid.setData(data);

            GridResponse response = new GridResponse();
            response.setContent(grid.writeToString());

            return response;

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/avviaimportazione/{importId}")
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse startImport(@PathVariable int importId) {

        try {
           return new ValueResponse(quosvc.importQuote(quosvc.getImportazioneById(importId)));

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/clearimportazionestatus")
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse clearstatusImport() {

        try {
            //            //azzero tutti i valori nella session
            session.put("StartAnagraficheLav", "");
            session.put("AnagraficheLav", "");
            session.put("EndAnagraficheLav", "");
            session.put("StartAnagraficheAz", "");
            session.put("AnagraficheAz", "");
            session.put("EndAnagraficheAz", "");
            session.put("StartImport", "");
            session.put("EndImport", "");
            session.put("ImportData", "");
            session.put("EndAllImport", "");
            return new ValueResponse("ok");

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/importazionestatus")
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse statusImport() {

        try {
            ImportReport r = new ImportReport();

            r.setStartAnagraficheLav((String)session.get("StartAnagraficheLav"));
            r.setAnagraficheLav((String)session.get("AnagraficheLav"));
            r.setEndAnagraficheLav((String)session.get("EndAnagraficheLav"));
            r.setStartAnagraficheAz((String)session.get("StartAnagraficheAz"));
            r.setAnagraficheAz((String)session.get("AnagraficheAz"));
            r.setEndAnagraficheAz((String)session.get("EndAnagraficheAz"));
            r.setStartImport((String)session.get("StartImport"));
            r.setEndImport((String)session.get("EndImport"));
            r.setImportData((String)session.get("ImportData"));
            r.setEndAllImport((String)session.get("EndAllImport"));

            if (!StringUtils.isEmpty(r.getEndAllImport()))
                r.setTerminated(true);

            return new ValueResponse(r);

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/importpanel/{importId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String importPanel(@PathVariable int importId, Model model) {
        model.addAttribute("importId", importId);
        return "import/importPanel";

    }



}
