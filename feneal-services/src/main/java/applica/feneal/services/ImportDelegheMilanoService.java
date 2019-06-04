package applica.feneal.services;


import applica.feneal.domain.model.core.deleghe.milano.DelegheMilanoObject;

public interface ImportDelegheMilanoService {

    DelegheMilanoObject importDelegheMilano(String importData) throws Exception;
}
