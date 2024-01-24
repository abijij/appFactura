package org.abijij.appfactura.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura {

    private Cliente cliente;
    private ItemFactura[] items;
    private int indiceItems;
    private int folio;
    private String descripcion;
    private Date fecha;
    private static final int MAX_ITEMS = 10;
    private static int ultimoFolio;

    public Factura(Cliente cliente, String descripcion) {
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.items = new ItemFactura[MAX_ITEMS];
        this.folio = ++ultimoFolio;
        this.fecha = new Date();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ItemFactura[] getItems() {
        return items;
    }


    public int getFolio() {
        return folio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void addItemFactura(ItemFactura item) {
        if (indiceItems < MAX_ITEMS) {
            this.items[indiceItems++] = item;
        }
    }

    public Float calcularTotal() {
        Float total = 0.0f;

        for (ItemFactura item : items) {
            if (item == null) {
                continue;
            }
            total += item.calcularImporters();
        }
        return total;
    }

    public String generarDetalle() {
        StringBuilder sb = new StringBuilder("Factura Numero: ");
        sb.append(folio)
                .append("\nCliente: ")
                .append(this.cliente.getNombre())
                .append("\nRFC: ")
                .append(this.cliente.getRFC())
                .append("\nDescripcion: ")
                .append(this.descripcion)
                .append("\n");

        SimpleDateFormat df = new SimpleDateFormat("dd , MMMM, yyyy");
        sb.append("Fecha emision de la factura: ")
                .append(df.format(this.fecha))
                .append("\n")
                .append("\n#\tNombre\t$\tDescripcion\tStock\tCantidad\tTotal\n");

        for(ItemFactura item: this.items){
            if(item == null){
                continue;
            }
            sb.append(item.getProducto().getId())
                    .append('\t')
                    .append(item.getProducto().getNombre())
                    .append("\t")
                    .append(item.getProducto().getPrecio())
                    .append("\t")
                    .append(item.getProducto().getDescripcion())
                    .append("\t")
                    .append(item.getProducto().getStock())
                    .append("\t")
                    .append(item.getCantidad())
                    .append("\t")
                    .append(item.calcularImporters())
                    .append("\n");
        }
        sb.append("\nGran Total de $ ")
                .append(calcularTotal());

        return sb.toString();
    }

    public static boolean contieneNumeros(String cadena) {
        for (char c : cadena.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contieneLetras(String cadena) {
        for (char c : cadena.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
