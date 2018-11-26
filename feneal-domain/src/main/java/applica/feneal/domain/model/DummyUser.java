package applica.feneal.domain.model;

import applica.framework.data.hibernate.annotations.IgnoreMapping;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/19/15
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
@IgnoreMapping
public class DummyUser extends User {



    //questa classe è utilizzata esclusivamente per permettere la visualizzazione di form e giglie crup per la entity utente
    //poiche tali form e griglie sono definite univocamente per entità necessito di una entitità dummy per ottenere una griglia o un form
    //per quanto riguarda il repository necessito di un repository wrapper che wrapperà il repository real degli utenti.......



}
