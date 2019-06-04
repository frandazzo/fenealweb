package applica.feneal.services.impl;

import applica.feneal.services.FileSearchPath;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileSearchPathImpl implements FileSearchPath {

    private String fileNameToSearch;
    private String result;

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public String getResult() {
        return result;
    }


    public String searchFilePath(File dir, String fileName) throws Exception{


        String pathFound = null;
        FileSearchPathImpl fileSearch = new FileSearchPathImpl();

        //try different directory and filename :)
        fileSearch.searchDirectory(new File(dir.getCanonicalPath()), fileName+".TIF");

        if(!fileSearch.getResult().isEmpty()) {
            pathFound = fileSearch.getResult();
        }
        return pathFound;
    }


    public void searchDirectory(File directory, String fileNameToSearch) {

        setFileNameToSearch(fileNameToSearch);

        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    private void search(File file) {

        if (file.isDirectory()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (getFileNameToSearch().equals(temp.getName())) {
                            result = temp.getAbsoluteFile().toString();
                        }

                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }

    }
}
