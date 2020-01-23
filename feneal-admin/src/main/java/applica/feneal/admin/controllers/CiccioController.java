package applica.feneal.admin.controllers;


import applica.feneal.admin.facade.IscrittiFacade;
import applica.feneal.admin.viewmodel.reports.UiIscritto;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CiccioController {

    @Autowired
    private IscrittiFacade iscrittiReportFac;


    @RequestMapping(value="/ciccio/ciccio", method = RequestMethod.POST)
    public @ResponseBody
    SimpleResponse reportIscrittiCiccio(@RequestBody UiIscrittoReportSearchParams params){
        List<UiIscritto> f;
        try{
            Security.manualLogin("fenealgrosseto","1_3_5grossetofeneal");

            params.setDatefromMonthReport("10");
            params.setDatefromYearReport("2019");
            params.setDatetoMonthReport("1");
            params.setDatetoYearReport("2020");
            params.setFirm("");
            params.setParithetic("CASSA EDILE");
            params.setProvince("Grosseto");
            params.setSector("EDILE");
            params.setTypeQuoteCash("IQA");
            params.setDelegationActiveExist(false);
            f = iscrittiReportFac.reportIscritti(params);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}
