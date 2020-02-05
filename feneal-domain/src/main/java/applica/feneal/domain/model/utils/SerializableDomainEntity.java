package applica.feneal.domain.model.utils;

import applica.framework.AEntity;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@IgnoreMapping
@JsonIgnoreProperties({ "iid", "sid", "lid" })
public class SerializableDomainEntity extends AEntity {
}
