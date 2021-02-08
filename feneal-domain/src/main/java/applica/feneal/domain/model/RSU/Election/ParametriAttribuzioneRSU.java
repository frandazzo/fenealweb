package applica.feneal.domain.model.RSU.Election;

public class ParametriAttribuzioneRSU {

    private double quozienteElettorale;
    private double quozienteElettorale2_3;
    private double quozienteElettorale1_3;
    private double sogliaDiSbarramento;
    private double sogliaDiSbarramentoListaDominante;
    private int rSUElegibili2_3;
    private int rSUElegibili1_3;
    private int rSUAggiuntiviEleggibiliDaSolidarieta;


    public int getrSUAggiuntiviEleggibiliDaSolidarieta() {
        return rSUAggiuntiviEleggibiliDaSolidarieta;
    }

    public void setrSUAggiuntiviEleggibiliDaSolidarieta(int rSUAggiuntiviEleggibiliDaSolidarieta) {
        this.rSUAggiuntiviEleggibiliDaSolidarieta = rSUAggiuntiviEleggibiliDaSolidarieta;
    }

    public int getrSUElegibili1_3() {
        return rSUElegibili1_3;
    }

    public void setrSUElegibili1_3(int rSUElegibili1_3) {
        this.rSUElegibili1_3 = rSUElegibili1_3;
    }

    public int getrSUElegibili2_3() {
        return rSUElegibili2_3;
    }

    public void setrSUElegibili2_3(int rSUElegibili2_3) {
        this.rSUElegibili2_3 = rSUElegibili2_3;
    }

    public double getSogliaDiSbarramentoListaDominante() {
        return sogliaDiSbarramentoListaDominante;
    }

    public void setSogliaDiSbarramentoListaDominante(double sogliaDiSbarramentoListaDominante) {
        this.sogliaDiSbarramentoListaDominante = sogliaDiSbarramentoListaDominante;
    }

    public double getSogliaDiSbarramento() {
        return sogliaDiSbarramento;
    }

    public void setSogliaDiSbarramento(double sogliaDiSbarramento) {
        this.sogliaDiSbarramento = sogliaDiSbarramento;
    }

    public double getQuozienteElettorale1_3() {
        return quozienteElettorale1_3;
    }

    public void setQuozienteElettorale1_3(double quozienteElettorale1_3) {
        this.quozienteElettorale1_3 = quozienteElettorale1_3;
    }

    public double getQuozienteElettorale2_3() {
        return quozienteElettorale2_3;
    }

    public void setQuozienteElettorale2_3(double quozienteElettorale2_3) {
        this.quozienteElettorale2_3 = quozienteElettorale2_3;
    }

    public double getQuozienteElettorale() {
        return quozienteElettorale;
    }

    public void setQuozienteElettorale(double quozienteElettorale) {
        this.quozienteElettorale = quozienteElettorale;
    }
}
