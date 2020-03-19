package proyectopoo;

public abstract class Tienda {
    protected int contadorProducto;
    protected String nombreProducto;
    protected String tipoProducto;
    protected int cantidad;
    protected double valorUnitario;
    protected double iVA;
    protected int cantidadRestar;

    public abstract void calcular();

    public String getNombreProducto() {
        return nombreProducto;
    }


    public String getTipoProducto() {
        return tipoProducto;
    }


    public int getCantidad() {
        return cantidad;
    }


    public double getValorUnitario() {
        return valorUnitario;
    }


    public double getiVA() {
        return iVA;
    }

    public int getContadorProducto() {
        return contadorProducto;
    }

    public void setContadorProducto(int contadorProducto) {
        this.contadorProducto = contadorProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public void setiVA(double iVA) {
        this.iVA = iVA;
    }

    public int getCantidadRestar() {
        return cantidadRestar;
    }

    public void setCantidadRestar(int cantidadRestar) {
        this.cantidadRestar = cantidadRestar;
    }
}
