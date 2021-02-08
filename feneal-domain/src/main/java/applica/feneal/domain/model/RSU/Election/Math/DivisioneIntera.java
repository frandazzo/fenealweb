package applica.feneal.domain.model.RSU.Election.Math;

public class DivisioneIntera {


    private int dividendo;
    private int divisore;
    private int quoziente;
    private int resto;

    public DivisioneIntera(int dividendo, int divisore) throws Exception {
        if (this.divisore == 0)
            throw new Exception("Il divisore non può essere nullo");
        this.dividendo = dividendo;
        this.divisore = divisore;
    }



    public void Dividi(){
        this.quoziente = this.dividendo/this.divisore;
        this.resto = this.dividendo%this.divisore;//System.Math.DivRem(this._dividendo, this._divisore, out this._resto);
     }

    public int getQuoziente() {
        return quoziente;
    }

    public int getResto() {
        return resto;
    }

}
