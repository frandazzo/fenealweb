package applica.feneal.domain.model.utils;

/**
 * Created by fgran on 21/05/2016.
 */
public interface LocalStorage {
     void put(String key, Object value);
     Object get(String key);


}
