package applica.feneal.services;

import java.io.File;

public interface FileSearchPath {
    String searchFilePath(File dir, String fileName) throws Exception;
}
