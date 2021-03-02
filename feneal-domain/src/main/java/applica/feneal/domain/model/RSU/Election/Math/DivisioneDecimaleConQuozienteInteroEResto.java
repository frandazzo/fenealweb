package applica.feneal.domain.model.RSU.Election.Math;

public class DivisioneDecimaleConQuozienteInteroEResto {

    private double dividendo;
    private double divisore;
    private double quoziente;
    private double resto;

    public DivisioneDecimaleConQuozienteInteroEResto(double dividendo, double divisore) throws Exception {
        if (divisore == 0d)
            throw new Exception("Il divisore non può essere nullo");
        this.dividendo = dividendo;
        this.divisore = divisore;
    }

    public int getQuoziente() throws Exception {

            try
            {
                return (int )quoziente;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore nella trasformazione del quoziennte in un numero intero", ex);
            }

    }



    public void Dividi()
    {
        this.quoziente = Math.floor(Math.round((this.dividendo / this.divisore)*100.0)/100.0);
        this.resto = Math.round(this.dividendo % this.divisore);
    }


    public double getResto() {
        return resto;
    }
}
