package applica.feneal.services.impl.quote;

/**
 * Created by fgran on 22/05/2016.
 */
public class ErrorsCounter {
    private int errors;

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void incrementErrorNumber() {

        errors = errors + 1;
    }
}
