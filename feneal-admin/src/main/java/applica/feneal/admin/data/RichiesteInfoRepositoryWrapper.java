package applica.feneal.admin.data;

import applica.feneal.domain.data.core.servizi.RichiesteInfoRepository;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.services.MessageService;
import applica.feneal.services.messages.MessageInput;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
@Component
public class RichiesteInfoRepositoryWrapper implements Repository<RichiestaInfo> {

    @Autowired
    private MessageService msgServ;

   @Autowired
   private RichiesteInfoRepository rep;


    @Override
    public Optional<RichiestaInfo> get(Object o) {
        return rep.get(o);
    }

    @Override
    public LoadResponse<RichiestaInfo> find(LoadRequest loadRequest) {
        return rep.find(loadRequest);
    }

    @Override
    public void save(RichiestaInfo r) {
        rep.save(r);
        if (r.isSendMail())
        {
            if (!StringUtils.isEmpty(r.getNote())){
                MessageInput mailInput = new MessageInput();
                mailInput.setContent(r.getNote());
                mailInput.setRecipients(Arrays.asList(r.getDestinatario()));
                mailInput.setMailFrom("fenealgest@gmail.com");
                mailInput.setSubject("Richiesta territorio");
                msgServ.sendSimpleMail(mailInput);
            }
        }
    }

    @Override
    public void delete(Object o) {
        rep.delete(o);
    }

    @Override
    public Class<RichiestaInfo> getEntityType() {
        return RichiestaInfo.class;
    }
}
