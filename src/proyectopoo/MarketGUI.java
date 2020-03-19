package proyectopoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MarketGUI {
    private JTabbedPane tabbedPane1;
    private JPanel Productos;
    private JPanel Ventas;
    private JButton SALIRButton;
    private JPanel VentanaMarket;
    private JButton agregarButton;
    private JTextField campoValorU;
    private JTextField campoCantidad;
    private JTextField campoNombre;
    private JList listaRegistrar;
    private JPanel RegistroProducto;
    private JComboBox campoTipo;
    private JComboBox campoIVA;
    private JButton botonActualizar;
    private JTextField campoBuscar;
    private JButton buscarButton;
    private JTextField campoComprobar;
    private JButton comrpobarStockButton;
    private JLabel codigoComprobar;
    private JLabel nombreComprobar;
    private JLabel stockComprobar;
    private JTextField cantidadAgregar;
    private JButton botonAgregar;
    private JList listaAgregados;
    private JLabel mensajeStock;
    private JLabel ivaTotal;
    private JLabel totalPagar;
    private JButton pagarButton;
    private JButton cancelarButton;
    private JTextField cantidadAgregarStock;
    private JButton agregarButton1;
    private JTextField codigoAgregarStock;
    private JTable tablaVenta;
    private int contadorProducto = 0;

    private static List<Producto> productos = new ArrayList<Producto>();

    public void limpiar() {
        ivaTotal.setText("");
        totalPagar.setText("");
        codigoComprobar.setText("");
        nombreComprobar.setText("");
        stockComprobar.setText("");
        campoNombre.setText("");
        campoCantidad.setText("");
        campoValorU.setText("");
        campoBuscar.setText("");
        mensajeStock.setText("");
        codigoAgregarStock.setText("");
        cantidadAgregarStock.setText("");
    }

    double sumaIVA = 0;
    double sumaTotal = 0;

    public MarketGUI() {

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // En String para retornar en String, int y double y calcular el iva de cada producto

                Producto producto = new Producto(contadorProducto, campoNombre.getText(),
                        campoTipo.getSelectedItem().toString(), campoCantidad.getText(), campoValorU.getText(),
                        campoIVA.getSelectedItem().toString());
                producto.calcular();
                contadorProducto = contadorProducto + 1;
                productos.add(producto);
                String[] prod = new String[productos.size()];
                // recorrer los productos de la lista productos
                int cont = 0;
                for (Producto producto2 : productos) {
                    prod[cont] = String.format("%1s%34s%35.2f%35s", producto2.contadorProducto,
                            producto2.nombreProducto, Float.parseFloat(String.valueOf(producto2.valorUnitario)),
                            producto2.cantidad);
                    cont++;
                }
                listaRegistrar.setListData(prod);
                limpiar();

            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] prod = new String[productos.size()];
                int cont = 0;
                for (Producto producto2 : productos) {
                    prod[cont] = String.format("%1s%34s%35.2f%35s", producto2.contadorProducto,
                            producto2.nombreProducto, Float.parseFloat(String.valueOf(producto2.valorUnitario)),
                            producto2.cantidad);
                    cont++;
                }
                listaRegistrar.setListData(prod);
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] prod = new String[productos.size()];
                String nombre_a_buscar = campoBuscar.getText();
                int cont = 0;
                for (Producto p : productos) {
                    if (p.nombreProducto.contains(nombre_a_buscar)) {
                        prod[cont] = String.format("%1s%34s%35.2f%35s", p.contadorProducto,
                                p.nombreProducto, Float.parseFloat(String.valueOf(p.valorUnitario)), p.cantidad);
                    }
                    cont++;
                }
                listaRegistrar.setListData(prod);
                limpiar();
            }
        });
        comrpobarStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int comrpobarStock = parseInt(campoComprobar.getText());
                String codigo = "";
                String stock = "";
                String nombrePro = "";
                for (Producto p : productos) {
                    if (p.contadorProducto == comrpobarStock) {
                        codigo = String.valueOf(p.contadorProducto);
                        nombrePro = p.nombreProducto;
                        stock = String.valueOf(p.cantidad);
                    }
                }
                codigoComprobar.setText(codigo);
                nombreComprobar.setText(nombrePro);
                stockComprobar.setText(stock);
            }
        });

        List<Venta> ventas = new ArrayList<Venta>();
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int codigo = parseInt(campoComprobar.getText());
                int cantidad = parseInt(cantidadAgregar.getText());
                for (Producto p : productos) {
                    if (p.contadorProducto == codigo) {
                        if (p.cantidad >= cantidad) {
                            mensajeStock.setText("Producto Agregado");
                            Venta venta = new Venta(p.contadorProducto, p.nombreProducto, p.tipoProducto,
                                    p.cantidad, p.valorUnitario, p.iVA, cantidad);
                            venta.calcularIvaTotal();
                            venta.calcularValorTotal();
                            venta.calcular();
                            p.setCantidad(venta.getCantidadActual());
                            ventas.add(venta);

                            String[] vent = new String[ventas.size()];
                            // recorrer los productos de la lista venta
                            int cont = 0;
                            for (Venta v : ventas) {
                                vent[cont] = String.format("%1s%25s%30s%35s%25.2f%20.2f%20.2f", v.contadorProducto,
                                        v.nombreProducto, v.tipoProducto, v.getCantidadRestar(),
                                        Float.parseFloat(String.valueOf(v.valorUnitario)),
                                        Float.parseFloat(String.valueOf(v.getIvaTotal())),
                                        Float.parseFloat(String.valueOf(v.getValorTotal())));
                                sumaIVA = v.getIvaTotal() + sumaIVA;
                                sumaTotal = v.getValorTotal() + sumaTotal;


                                cont++;
                            }
                            listaAgregados.setListData(vent);
                            ivaTotal.setText(String.format("%.2f", Float.parseFloat(String.valueOf(sumaIVA))));
                            totalPagar.setText(String.format("%.2f", Float.parseFloat(String.valueOf(sumaTotal))));
                        } else {
                            mensajeStock.setText("No hay stock suficiente");
                        }
                    }
                }
                campoComprobar.setText("");
                cantidadAgregar.setText("");
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Producto p : productos) {
                    for (Venta v : ventas) {
                        if (v.contadorProducto == p.contadorProducto) {
                            p.setCantidad(v.cantidad);
                        }
                    }
                }
                sumaIVA = 0;
                sumaTotal = 0;
                System.out.println(ventas.size());
                ventas.clear();
                System.out.println(ventas.size());
                String[] vent = new String[ventas.size()];
                listaAgregados.setListData(vent);
                limpiar();


            }
        });
        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sumaTotal >= 0.01) {
                    JOptionPane.showMessageDialog(null, "Total a pagar:" + sumaTotal +
                            "\nGracias por su compra");
                    String[] vent = new String[ventas.size()];
                    listaAgregados.setListData(vent);
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Agrege productos a comprar");
                }
            }
        });
        agregarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Producto p : productos) {
                    if (p.contadorProducto == parseInt(codigoAgregarStock.getText())) {
                        p.setCantidad((p.cantidad + parseInt(cantidadAgregarStock.getText())));

                    }
                    String[] prod = new String[productos.size()];
                    int cont = 0;
                    for (Producto producto2 : productos) {
                        prod[cont] = String.format("%1s%34s%35.2f%35s", producto2.contadorProducto,
                                producto2.nombreProducto, Float.parseFloat(String.valueOf(producto2.valorUnitario)),
                                producto2.cantidad);
                        cont++;
                    }
                    listaRegistrar.setListData(prod);
                }
                limpiar();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MarketGUI");
        frame.setContentPane(new MarketGUI().VentanaMarket);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        VentanaMarket = new JPanel();
        VentanaMarket.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        VentanaMarket.setEnabled(true);
        VentanaMarket.setMaximumSize(new Dimension(800, 500));
        VentanaMarket.setMinimumSize(new Dimension(700, 482));
        tabbedPane1 = new JTabbedPane();
        VentanaMarket.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(0, 0), null, 0, false));
        Productos = new JPanel();
        Productos.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        Productos.setMaximumSize(new Dimension(300, 800));
        tabbedPane1.addTab("Productos", Productos);
        RegistroProducto = new JPanel();
        RegistroProducto.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 2, new Insets(0, 0, 0, 0), -1, -1));
        RegistroProducto.setForeground(new Color(-16777216));
        Productos.add(RegistroProducto, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(203, 354), new Dimension(200, -1), 0, false));
        RegistroProducto.setBorder(BorderFactory.createTitledBorder("Registre el producto"));
        campoCantidad = new JTextField();
        campoCantidad.setText("");
        RegistroProducto.add(campoCantidad, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        agregarButton = new JButton();
        agregarButton.setText("Agregar");
        RegistroProducto.add(agregarButton, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Nombre de producto");
        RegistroProducto.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Cantidad");
        RegistroProducto.add(label2, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Valor Unitario");
        RegistroProducto.add(label3, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        campoTipo = new JComboBox();
        campoTipo.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Comestibles");
        defaultComboBoxModel1.addElement("Utensilios");
        defaultComboBoxModel1.addElement("Oficina");
        defaultComboBoxModel1.addElement("Industriales");
        campoTipo.setModel(defaultComboBoxModel1);
        RegistroProducto.add(campoTipo, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Tipo de producto");
        RegistroProducto.add(label4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        campoNombre = new JTextField();
        campoNombre.setText("");
        RegistroProducto.add(campoNombre, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("I.V.A.");
        RegistroProducto.add(label5, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("         %");
        RegistroProducto.add(label6, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        campoIVA = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("12");
        defaultComboBoxModel2.addElement("0");
        campoIVA.setModel(defaultComboBoxModel2);
        RegistroProducto.add(campoIVA, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("(0.00) $");
        RegistroProducto.add(label7, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        campoValorU = new JTextField();
        campoValorU.setMargin(new Insets(2, 6, 2, 6));
        campoValorU.setText("");
        RegistroProducto.add(campoValorU, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        Productos.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(478, 354), null, 0, true));
        panel1.setBorder(BorderFactory.createTitledBorder("Productos agregados"));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setFocusable(true);
        scrollPane1.setVisible(true);
        scrollPane1.setWheelScrollingEnabled(true);
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(256, 272), null, 0, false));
        listaRegistrar = new JList();
        listaRegistrar.setMaximumSize(new Dimension(-1, -1));
        listaRegistrar.setMinimumSize(new Dimension(-1, -1));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        listaRegistrar.setModel(defaultListModel1);
        listaRegistrar.setValueIsAdjusting(false);
        scrollPane1.setViewportView(listaRegistrar);
        final JLabel label8 = new JLabel();
        label8.setText("Codigo");
        panel1.add(label8, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        campoBuscar = new JTextField();
        panel2.add(campoBuscar, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(185, 30), null, 0, false));
        buscarButton = new JButton();
        buscarButton.setText("Buscar");
        panel2.add(buscarButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Nombre");
        panel1.add(label9, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        botonActualizar = new JButton();
        botonActualizar.setText("Mostrar todo | Actualizar");
        panel1.add(botonActualizar, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Stock");
        panel1.add(label10, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("        Valor");
        panel1.add(label11, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(92, 16), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        Productos.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder("Agregar Stock"));
        cantidadAgregarStock = new JTextField();
        cantidadAgregarStock.setText("");
        panel3.add(cantidadAgregarStock, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        codigoAgregarStock = new JTextField();
        codigoAgregarStock.setText("");
        panel3.add(codigoAgregarStock, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Codigo");
        panel3.add(label12, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Cantidad");
        panel3.add(label13, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarButton1 = new JButton();
        agregarButton1.setText("agregar");
        panel3.add(agregarButton1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Ventas = new JPanel();
        Ventas.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Vender", Ventas);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 7, new Insets(0, 0, 0, 0), -1, -1));
        Ventas.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder("Producto a vender"));
        final JLabel label14 = new JLabel();
        label14.setText("Ingresa el codigo de el producto para comprobar el stock");
        panel4.add(label14, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(306, 15), null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Codigo:");
        panel4.add(label15, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Nombre de producto");
        panel4.add(label16, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        codigoComprobar = new JLabel();
        codigoComprobar.setText("");
        panel4.add(codigoComprobar, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(19, 0), null, 0, false));
        nombreComprobar = new JLabel();
        nombreComprobar.setText("");
        panel4.add(nombreComprobar, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 0), null, 0, false));
        stockComprobar = new JLabel();
        stockComprobar.setText("");
        panel4.add(stockComprobar, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Stock disponible:");
        panel4.add(label17, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(129, 16), null, 0, false));
        campoComprobar = new JTextField();
        campoComprobar.setText("");
        panel4.add(campoComprobar, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(129, 11), null, 0, false));
        comrpobarStockButton = new JButton();
        comrpobarStockButton.setText("Comrpobar stock");
        panel4.add(comrpobarStockButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(125, 15), null, 0, false));
        cantidadAgregar = new JTextField();
        cantidadAgregar.setText("");
        panel4.add(cantidadAgregar, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Cantidad a agregar");
        panel4.add(label18, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        botonAgregar = new JButton();
        botonAgregar.setText("Agregar Producto");
        panel4.add(botonAgregar, new com.intellij.uiDesigner.core.GridConstraints(2, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mensajeStock = new JLabel();
        mensajeStock.setForeground(new Color(-41116));
        mensajeStock.setText("");
        panel4.add(mensajeStock, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 8, new Insets(0, 0, 0, 0), -1, -1));
        Ventas.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder("Productos Agregados"));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(""));
        listaAgregados = new JList();
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        listaAgregados.setModel(defaultListModel2);
        scrollPane2.setViewportView(listaAgregados);
        final JLabel label19 = new JLabel();
        label19.setText("Codigo");
        panel5.add(label19, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder("I.V.A. TOTAL"));
        ivaTotal = new JLabel();
        ivaTotal.setText("");
        panel6.add(ivaTotal, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("$");
        panel6.add(label20, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(17, 16), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder("Total a pagar"));
        final JLabel label21 = new JLabel();
        label21.setText("$");
        panel7.add(label21, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalPagar = new JLabel();
        totalPagar.setText("");
        panel7.add(totalPagar, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Nombre");
        panel5.add(label22, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(124, 16), null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Tipo Producto");
        panel5.add(label23, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Cantidad   ");
        panel5.add(label24, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(34, 16), null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("Valor unitario");
        panel5.add(label25, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(52, 16), null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("I.V.A.               ");
        panel5.add(label26, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Total");
        panel5.add(label27, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, 16), null, 0, false));
        cancelarButton = new JButton();
        cancelarButton.setText("Cancelar");
        panel5.add(cancelarButton, new com.intellij.uiDesigner.core.GridConstraints(2, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pagarButton = new JButton();
        pagarButton.setText("Pagar");
        panel5.add(pagarButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 30), null, 0, false));
        final JLabel label28 = new JLabel();
        label28.setText("");
        panel5.add(label28, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SALIRButton = new JButton();
        SALIRButton.setBackground(new Color(-41116));
        SALIRButton.setText("SALIR");
        VentanaMarket.add(SALIRButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1.setLabelFor(campoNombre);
        label6.setLabelFor(campoIVA);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return VentanaMarket;
    }


}
