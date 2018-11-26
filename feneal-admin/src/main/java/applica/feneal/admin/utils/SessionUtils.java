package applica.feneal.admin.utils;

import applica.feneal.domain.model.utils.LocalStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/11/15
 * Time: 4:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SessionUtils implements LocalStorage {

    public void put(String key, Object value){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);


        session.setAttribute(key,value);


    }


    public Object get(String key){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);


        return session.getAttribute(key);
    }



}
