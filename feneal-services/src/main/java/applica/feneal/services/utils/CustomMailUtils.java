package applica.feneal.services.utils;

import applica.framework.library.mail.OptionsMailAuthenticator;
import applica.framework.library.options.OptionsManager;
import org.springframework.util.StringUtils;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

/**
 * Created by fgran on 01/06/2016.
 */
public class CustomMailUtils {
    public CustomMailUtils() {
    }

    public static Session getMailSession(OptionsManager options) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", options.get("smtp.host"));
        String smtpPort = options.get("smtp.port");
        if (StringUtils.hasLength(options.get("mail.smtp.starttls.enable"))){
            properties.put("mail.smtp.starttls.enable", "true");
        }
        properties.put("mail.smtp.port", Integer.valueOf(StringUtils.hasLength(smtpPort)?Integer.parseInt(smtpPort):25));
        properties.put("mail.smtp.auth", "true");
        return Session.getInstance(properties, new OptionsMailAuthenticator(options));
    }

    public static boolean isValid(String mail) {
        if(!StringUtils.hasLength(mail)) {
            return false;
        } else {
            boolean result = true;

            try {
                new InternetAddress(mail);
                if(!hasNameAndDomain(mail)) {
                    result = false;
                }
            } catch (AddressException var3) {
                result = false;
            }

            return result;
        }
    }

    private static boolean hasNameAndDomain(String mail) {
        String[] tokens = mail.split("@");
        return tokens.length == 2 && StringUtils.hasLength(tokens[0]) && StringUtils.hasLength(tokens[1]);
    }
}

