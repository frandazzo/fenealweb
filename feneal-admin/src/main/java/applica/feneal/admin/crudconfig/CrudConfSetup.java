package applica.feneal.admin.crudconfig;

import applica.feneal.admin.Bootstrapper;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheLecceRepository;
import applica.framework.library.utils.NullableDateConverter;
import applica.framework.widgets.CrudConfiguration;
import applica.framework.widgets.CrudConstants;
import applica.framework.widgets.CrudFactory;
import applica.framework.widgets.Grid;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.forms.processors.DefaultFormProcessor;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import applica.feneal.services.impl.setup.AppSetup;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CrudConfSetup implements AppSetup {

    @Autowired
    private CrudFactory crudFactory;

    @Autowired
    private PermissionsSetup permisisonSetup;

    @Autowired
    private UserCrudConfig userCrudConfig;

    @Autowired
    private NormalUserCrudConfig compUsrcrudConf;

    @Autowired
    private NormalUser1CrudConfig compUsrcrudConf1;

    @Autowired
    private CompanyCrudConfig companyCrudConfig;

    @Autowired
    private SectorCrudConfig sectorCrudConfig;

    @Autowired
    private ParitethicCrudConfig paritheticCrudConfig;

    @Autowired
    private VisibleFunctionCrudConfig visibleFunctionCrudConfig;

    @Autowired
    private WidgetCrudConfig widgetCrudConfig;

    @Autowired
    private ApplicationOptionCrudConfig applicationOptionCrudConfig;

    @Autowired
    private ContractCrudConfig contractCrudConfig;

    @Autowired
    private RevocationReasonCrudConfig revocationReasonCrudConfig;

    @Autowired
    private DocumentTypeCrudConfig documentTypeCrudConfig;

    @Autowired
    private FundCrudConfig fundCrudConfig;

    @Autowired
    private CommunicationTypeCrudConfig communicationTypeConfig;

    @Autowired
    private CommunicationReasonCrudConfig communicationReasonCrudConfig;

    @Autowired
    private ProcedureTypeCrudConfig procedureTypeCrudConfig;

    @Autowired
    private CollaboratorCrudConfig collaboratorCrudConfig;

    @Autowired
    private SignupDelegationReasonCrudConfig signupDelegationReasonCrudConfig;

    @Autowired
    private AziendaCrudConfig azCrudConfig;

    @Autowired
    private ListaLavoroCrudConfig listaLavoroCrudConfig;

    @Autowired
    private DocumentiCrudConfig docConf;

    @Autowired
    private DocumentiAziendaCrudConfig docAziendaConf;

    @Autowired
    private ComunicazioniCrudConfig comConf;

    @Autowired
    private MagazzinoDelegheCrudConfig magazzDelConf;

    @Autowired
    private RichiesteCrudConfig ricConf;

    @Autowired
    private QuoteAssocCrudConfig quoteAssocConf;

    @Autowired
    private MagazzineDelegheLecceCrudConfig magSDelLecceCrud;

    @Autowired
    private ReferentiComunaliCrudConfig referentiComunaliCrudConfig;

    @Override
    public void setup() {

        initializeCrudConfigurationDefaultValues();
        userCrudConfig.setup();
        companyCrudConfig.setup();
        compUsrcrudConf.setup();
        sectorCrudConfig.setup();
        paritheticCrudConfig.setup();
        visibleFunctionCrudConfig.setup();
        widgetCrudConfig.setup();
        contractCrudConfig.setup();
        applicationOptionCrudConfig.setup();
        docConf.setup();
        revocationReasonCrudConfig.setup();
        documentTypeCrudConfig.setup();
        fundCrudConfig.setup();
        communicationTypeConfig.setup();
        communicationReasonCrudConfig.setup();
        procedureTypeCrudConfig.setup();
        collaboratorCrudConfig.setup();
        signupDelegationReasonCrudConfig.setup();
        azCrudConfig.setup();
        listaLavoroCrudConfig.setup();
        docAziendaConf.setup();
        comConf.setup();
        ricConf.setup();
        quoteAssocConf.setup();
        magazzDelConf.setup();
        magSDelLecceCrud.setup();
        compUsrcrudConf1.setup();
        referentiComunaliCrudConfig.setup();


        permisisonSetup.setup();

    }

    private void initializeCrudConfigurationDefaultValues(){


        NullableDateConverter dateConverter = new NullableDateConverter();
        dateConverter.setPatterns(new String[] { "dd/MM/yyyy HH:mm", "MM/dd/yyyy HH:mm", "yyyy-MM-dd HH:mm", "dd/MM/yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "HH:mm" });
        ConvertUtils.register(dateConverter, Date.class);

        CrudConfiguration.instance().setCrudFactory(crudFactory);

        Package pack = Bootstrapper.class.getPackage();
        try {
            CrudConfiguration.instance().scan(pack);
        } catch (Exception e) {
            e.printStackTrace();

        }

        CrudConfiguration.instance().registerGridRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultGridRenderer.class);
        CrudConfiguration.instance().registerFormRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultFormRenderer.class);
        CrudConfiguration.instance().registerFormFieldRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, "", DefaultFieldRenderer.class);
        CrudConfiguration.instance().registerFormProcessor(CrudConstants.DEFAULT_ENTITY_TYPE, DefaultFormProcessor.class);
        CrudConfiguration.instance().registerCellRenderer(CrudConstants.DEFAULT_ENTITY_TYPE, "", DefaultCellRenderer.class);
        CrudConfiguration.instance().setParam(CrudConfiguration.DEFAULT_ENTITY_TYPE, Grid.PARAM_ROWS_PER_PAGE, "20");

    }




}
