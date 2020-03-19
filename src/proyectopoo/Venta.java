package proyectopoo;

public class Venta extends Tienda {
    private int cantidadRestar;
    private int cantidadActual;
    private double ivaTotal;
    private double valorTotal;
    public Venta(int contadorProducto, String nombreProducto, String tipoProducto, int cantidad, double valorUnitario,
                 double iVA, int cantidad1) {
        this.contadorProducto = contadorProducto;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.iVA = iVA;
        this.setCantidadRestar(cantidad1);

    }
    public void calcularIvaTotal(){
        setIvaTotal(getCantidadRestar() * iVA);
    }

    public void calcularValorTotal(){
        valorTotal = (cantidadRestar * valorUnitario) + ivaTotal;
    }

    @Override
    public void calcular() {
        setCantidadActual(cantidad - getCantidadRestar());
    }
    public double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public int getCantidadRestar() {
        return cantidadRestar;
    }

    @Override
    public void setCantidadRestar(int cantidadRestar) {
        this.cantidadRestar = cantidadRestar;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }
}
