package proyectopoo;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Producto extends Tienda {
    private double tempIVA;

    public Producto(int contadorProducto, String nombreProducto, String tipoProducto, String cantidad,
                    String valorUnitario, String tempIVA) {
        this.contadorProducto = contadorProducto;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.cantidad = parseInt(cantidad);
        this.valorUnitario = parseDouble(valorUnitario);
        this.tempIVA = parseDouble(tempIVA);
    }

    @Override
    public void calcular() {
        setiVA(getValorUnitario() * (tempIVA / 100 ));
    }


}
