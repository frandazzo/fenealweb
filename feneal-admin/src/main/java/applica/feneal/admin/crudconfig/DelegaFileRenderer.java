package applica.feneal.admin.crudconfig;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.framework.library.velocity.VelocityBuilderProvider;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.velocity.VelocityFormFieldRenderer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Writer;
import java.util.List;

@Component
public class DelegaFileRenderer extends VelocityFormFieldRenderer {
    @Autowired
    private Security sec;

    public static final String DEFAULT_ACTION = "upload/file";

    public DelegaFileRenderer() {
    }

    public List<String> getAllowedExtensions() {
        return null;
    }

    public String getAction() {
        return "upload/file";
    }

    public String getPath() {
        //il path dove salvo dipende dalla cartella della company dell'utente loggato
        User u = ((User) sec.getLoggedUser());
        Company c = u.getCompany();

        String folderName = "cartella_" + c.getLid();


        return folderName + "/deleghe/";
    }

    public void render(Writer writer, FormField field, Object value) {


        Assert.isTrue("upload/file".equals(this.getAction()) && StringUtils.hasLength(this.getPath()), this.getClass().getName() + ": getPath() cannot return null (do you override the method?)");
        this.setTemplatePath("templates/fields/filesf.vm");




        if (!StringUtils.hasLength(getTemplatePath())) return;

        Template template = VelocityBuilderProvider.provide().engine().getTemplate(getTemplatePath(), "UTF-8");
        VelocityContext context = new VelocityContext();
        context.put("field", field);
        context.put("value", value != null ? value : "");
        //inserisco il nome dell'attachemtn
        if (field.getForm().getData() != null)
            context.put("attachmentName", field.getForm().getData().get("nome" + field.getProperty()));
        else
            context.put("attachmentName", "");


        setupContext(context);

        template.merge(context, writer);





        // super.render(writer, field, value);
    }

    protected void setupContext(VelocityContext context) {
        super.setupContext(context);
        context.put("action", this.getAction());
        context.put("extensions", this.getAllowedExtensions());
        context.put("path", this.getPath());
    }
}













