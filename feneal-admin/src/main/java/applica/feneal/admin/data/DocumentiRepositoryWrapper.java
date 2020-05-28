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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
        HttpPost uploadFile = new HttpPost("https://www.uilsgk.com/import/archiviodocumentale");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        // DATI LAVORATORE

        builder.addTextBody("name", documento.getLavoratore().getName(), ContentType.TEXT_PLAIN);
        builder.addTextBody("surname", documento.getLavoratore().getSurname(), ContentType.TEXT_PLAIN);
        builder.addTextBody("sex", documento.getLavoratore().getSex(), ContentType.TEXT_PLAIN);
        builder.addTextBody("fiscalCode", documento.getLavoratore().getFiscalcode(), ContentType.TEXT_PLAIN);
        builder.addTextBody("dateBirth", f.format(documento.getLavoratore().getBirthDate()), ContentType.TEXT_PLAIN);
        builder.addTextBody("nationality",
                documento.getLavoratore().getNationality() != null ? documento.getLavoratore().getNationality():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("birthProvince",
                documento.getLavoratore().getBirthProvince() != null ? documento.getLavoratore().getBirthProvince():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("birthPlace",
                documento.getLavoratore().getBirthPlace() != null ? documento.getLavoratore().getBirthPlace(): "", ContentType.TEXT_PLAIN);
        builder.addTextBody("livingProvince",
                documento.getLavoratore().getLivingProvince() != null ? documento.getLavoratore().getLivingProvince():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("livingCity",
                documento.getLavoratore().getLivingCity() != null ? documento.getLavoratore().getLivingCity():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("address",
                documento.getLavoratore().getAddress() != null ? documento.getLavoratore().getAddress():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("cap",
                documento.getLavoratore().getCap() !=null ? documento.getLavoratore().getCap():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("phone",
                documento.getLavoratore().getPhone() != null ? documento.getLavoratore().getPhone() : "", ContentType.TEXT_PLAIN);
        builder.addTextBody("cellphone",
                documento.getLavoratore().getCellphone() != null ? documento.getLavoratore().getCellphone():"", ContentType.TEXT_PLAIN);
        builder.addTextBody("mail",
                documento.getLavoratore().getMail() !=null ? documento.getLavoratore().getMail():"", ContentType.TEXT_PLAIN);

        builder.addTextBody("externalId", documento.getSid(), ContentType.TEXT_PLAIN);


        // DATI FILE E ALLEGATI

        builder.addTextBody("data",  f.format(documento.getData()), ContentType.TEXT_PLAIN);
        builder.addTextBody("note",  documento.getNotes(), ContentType.TEXT_PLAIN);
        builder.addTextBody("tipoComm",  documento.getTipo().getDescription(), ContentType.TEXT_PLAIN);


        builder.addBinaryBody(
                "file1",
                fileServer.getFile(documento.getAllegato1() !="" ? documento.getAllegato1():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato1()
        );


        builder.addBinaryBody(
                "file2",
                fileServer.getFile(documento.getAllegato2() !="" ? documento.getAllegato2():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato2()
        );

        builder.addBinaryBody(
                "file3",
                fileServer.getFile(documento.getAllegato3() !="" ? documento.getAllegato3():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato3()
        );

        builder.addBinaryBody(
                "file4",
                fileServer.getFile(documento.getAllegato4() !="" ? documento.getAllegato4():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato4()
        );

        builder.addBinaryBody(
                "file5",
                fileServer.getFile(documento.getAllegato5() !="" ? documento.getAllegato5():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato5()
        );

        builder.addBinaryBody(
                "file6",
                fileServer.getFile(documento.getAllegato6() !="" ? documento.getAllegato6():"NON PRESENTE"),
                ContentType.APPLICATION_OCTET_STREAM,
                documento.getNomeallegato6()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String content = EntityUtils.toString(responseEntity);
        return content;

    }

}

