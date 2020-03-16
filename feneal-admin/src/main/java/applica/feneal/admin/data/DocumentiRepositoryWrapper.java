package applica.feneal.admin.data;

import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import applica.framework.fileserver.FileServer;
import applica.framework.security.Security;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;
@org.springframework.stereotype.Repository
public class DocumentiRepositoryWrapper implements Repository<Documento> {

    @Autowired
    private FileServer fileServer;


    @Autowired
    private Security sec;

    @Autowired
    private DocumentiRepository aziendeRepository;



    @Override
    public Optional<Documento> get(Object id) {
        return aziendeRepository.get(id);
    }

    @Override
    public LoadResponse<Documento> find(LoadRequest request) {


        final LoadRequest finalRequest = request;

        return aziendeRepository.find(finalRequest);
    }

    @Override
    public void save(Documento entity) {
        aziendeRepository.save(entity);

        if (((User) sec.getLoggedUser()).getCompany().containProvince("Bolzano")){
            try {
                sendFileToUilWebBolzano(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void delete(Object id) {
        aziendeRepository.delete(id);
    }

    @Override
    public Class<Documento> getEntityType() {
        return aziendeRepository.getEntityType();
    }



    private String sendFileToUilWebBolzano(Documento documento) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://localhost:8081/import/archiviodocumentale");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("name", documento.getLavoratore().getName(), ContentType.TEXT_PLAIN);

// This attaches the file to the POST:

        builder.addBinaryBody(
                "file",
                fileServer.getFile(documento.getAllegato1()),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato1()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String content = EntityUtils.toString(responseEntity);
        return content;

    }

}

