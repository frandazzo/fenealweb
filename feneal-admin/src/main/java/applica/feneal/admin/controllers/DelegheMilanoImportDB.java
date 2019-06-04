package applica.feneal.admin.controllers;


import applica.feneal.admin.facade.ReportDelegheMilanoFacade;
import applica.feneal.domain.model.core.deleghe.milano.DelegaMilano;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.GridResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.widgets.Grid;
import applica.framework.widgets.GridDescriptor;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class DelegheMilanoImportDB {

}
