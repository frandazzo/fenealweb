package applica.feneal.data.hibernate.core.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.deleghe.DelegaBariRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.framework.LoadRequest;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class DelegheBariRepository extends HibernateRepository<DelegaBari> implements DelegaBariRepository {


    @Autowired
    private LavoratoriRepository lavRep;

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public List<DelegaBari> getDelegheByLavoratore(long lavoratoreId) {
        LoadRequest req = LoadRequest.build().filter("worker", lavoratoreId);
        return this.find(req).getRows();
    }

    @Override
    public DelegaBari getDelegaByLavoratoreAndData(long lavoratoreId, Date date) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date d = cal.getTime();

        LoadRequest req = LoadRequest.build().filter("worker", lavoratoreId)
                .filter("protocolDate",d);
        return this.find(req).findFirst().orElse(null);
    }


    @Override
    public Class<DelegaBari> getEntityType() {
        return DelegaBari.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("protocolDate", true));
    }


}

