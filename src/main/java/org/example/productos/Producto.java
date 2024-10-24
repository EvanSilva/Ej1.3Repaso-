package org.example.productos;

import java.io.Serializable;

public class Producto implements Serializable {

    private int id = 0;
    private String descripcion = "";
    private double precio = 0L;

    public Producto(int id,String descripcion, double precio) {
        this.descripcion = descripcion;
        this.id = id;
        this.precio = precio;
    }

    public Producto() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
