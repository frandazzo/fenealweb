package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.MultiSearchableInputFieldRenderer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataExportFilenamesFieldRenderer extends MultiSearchableInputFieldRenderer {

    @Override
    public String getServiceUrl() {
        return null;
    }

    @Override
    public String getTemplatePath() {
        return "templates/fields/multisearchableInputDataExportFilenames.vm";
    }

    @Override
    public List<SimpleItem> getSelectedItems(FormField field, Object value) {
        return null;
    }

    @Override
    public void render(Writer writer, FormField field, Object value) {
        this.setTemplatePath("/templates/fields/multi_searchable_input.vm");
        List<SimpleItem> items = this.getSelectedItems(field, value);
        this.putExtraContextValue("selectedItems", items);

        try {
            ObjectMapper o = new ObjectMapper();

            List<SimpleItem> result = new ArrayList<>();

            for (String val : ((List<String>)field.getForm().getData().get("dataExportFilenames"))) {
               SimpleItem simpleItem = new SimpleItem();
               simpleItem.setLabel(val);
               simpleItem.setValue(val);

               result.add(simpleItem);
            }

            //String jsonData = o.writeValueAsString(field.getForm().getData().get("dataExportFilenames"));
            String jsonData = o.writeValueAsString(result);

            this.putExtraContextValue("dataExportFilenames", jsonData);
        } catch (JsonProcessingException e) {
            this.putExtraContextValue("dataExportFilenames", "[]");
        }

        super.render(writer, field, null);
    }
}
