package endpoint;

public class Request {

    private int formula;
    private double betta;
    private double gamma;
    private int order;

    public Request() {
    }

    public Request(int formula, double betta, double gamma, int order) {
        this.formula = formula;
        this.betta = betta;
        this.gamma = gamma;
        this.order = order;
    }

    public int getFormula() {
        return formula;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }

    public double getBetta() {
        return betta;
    }

    public void setBetta(double betta) {
        this.betta = betta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
