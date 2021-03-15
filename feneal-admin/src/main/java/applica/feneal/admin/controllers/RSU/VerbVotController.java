package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.viewmodel.RSU.UiContrattoRsu;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VerbVotController {
//
//    @Autowired
//    private VerbVotFacade verbVotFcd
//
//    @RequestMapping(value = "/verbvot/{id}", method = RequestMethod.DELETE)
//    @PreAuthorize("isAuthenticated()")
//    public @ResponseBody
//    SimpleResponse delete(@PathVariable long id) {
//
//        try {
//            aziendeRsuFacade.deleteVerbVot(id);
//            return new ValueResponse("ok");
//        } catch(Exception e) {
//            e.printStackTrace();
//            return new ErrorResponse(e.getMessage());
//        }
//    }
//
//    @RequestMapping(value = "/verbvot", method = RequestMethod.POST)
//    @PreAuthorize("isAuthenticated()")
//    public @ResponseBody
//    SimpleResponse saveRsu(@RequestBody UiContrattoRsu anag) {
//
//        try {
//            long id = aziendeRsuFacade.saveVerbVote(anag);
//            return new ValueResponse(id);
//        } catch(Exception e) {
//            e.printStackTrace();
//            return new ErrorResponse(e.getMessage());
//        }
//    }
}
