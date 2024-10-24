package org.example.productos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

    private int id = 0;
    private String nombreCliente = "";
    private List<Producto> listaCompra = new ArrayList<>();

    public Pedido(int id, List<Producto> listaCompra, String nombreCliente) {
        this.id = id;
        this.listaCompra = listaCompra;
        this.nombreCliente = nombreCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(List<Producto> listaCompra) {
        this.listaCompra = listaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", listaCompra=" + listaCompra +
                '}';
    }
}
