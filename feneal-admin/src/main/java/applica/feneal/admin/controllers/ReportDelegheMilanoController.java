package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.ReportDelegheMilanoFacade;
import applica.feneal.admin.form.renderers.DelegheMilanoSearchFormRenderer;
import applica.feneal.domain.model.core.ImportDelegheMilanoInput;
import applica.feneal.admin.fields.renderers.ImportDelegheMilanoFileFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.feneal.services.ImportDelegheMilanoService;
import applica.framework.library.responses.*;
import applica.framework.widgets.*;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class ReportDelegheMilanoController {


    @Autowired
    private ImportDelegheMilanoService importDelegheMilanoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ReportDelegheMilanoFacade rptDelegheMilano;


    @RequestMapping(value = "/deleghemil",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DelegheMilanoSearchFormRenderer.class));
            form.setIdentifier("reportDelMil");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("file", Date.class, "File deleghe", null,applicationContext.getBean(ImportDelegheMilanoFileFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report deleghe");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ErrorResponse(ex.getMessage());
        }
    }


    @RequestMapping(value = "/importdeleghemil",method = RequestMethod.POST)
    public @ResponseBody
    SimpleResponse reportDelMil(HttpServletRequest request, HttpServletResponse response, @RequestBody ImportDelegheMilanoInput file) {

        try{
             String file1 = file.getFile();
            return new ValueResponse(importDelegheMilanoService.importDelegheMilano(file1));
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }


    @Autowired
    private ReportDelegheMilanoFacade importazioneFacade;


    @RequestMapping(value = "/importDB", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse gridImportazioni(HttpServletRequest request) {

        try {
            return null;

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

}
