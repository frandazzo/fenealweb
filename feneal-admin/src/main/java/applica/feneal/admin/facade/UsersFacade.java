package applica.feneal.admin.facade;

import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.User;
import applica.framework.library.options.OptionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by angelo on 29/04/2016.
 */
@Component
public class UsersFacade {

    @Autowired
    private OptionsManager options;

    @Autowired
    private UsersRepository usersRep;


    public void changeStatus(Long userId){
        User user = usersRep.get(userId).orElseThrow(() -> new RuntimeException("User does not exist"));

        boolean isActive = user.isActive();
        user.setActive(!isActive);

        usersRep.save(user);
    }

    public void changePassword(Long userId, String newPassword){
        User user = usersRep.get(userId).orElseThrow(() -> new RuntimeException("User does not exist"));

        String encodedPassword = new Md5PasswordEncoder().encodePassword(newPassword, null);
        user.setPassword(encodedPassword);
        user.setDecPass(newPassword);

        usersRep.save(user);
    }

    public void downloadConnectorSoftware(HttpServletResponse response) throws IOException {

        String path = options.get("connector.path");
        InputStream is = new FileInputStream(path);

        response.setHeader("Content-Disposition", "attachment;filename=Connector.exe");
        //response.setContentType("application/zip");
        response.setStatus(200);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.close();
    }

    public void downloadTesterMailSoftware(HttpServletResponse response) throws IOException {
        String path = options.get("testermail.path");
        InputStream is = new FileInputStream(path);

        response.setHeader("Content-Disposition", "attachment;filename=MailTester.exe");
        //response.setContentType("application/zip");
        response.setStatus(200);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.close();
    }
}
