package org.example.productos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrearXMLpedidos {

    public static void main(String[] args) {

        Producto monitor = new Producto(100, "Monitor", 100.0);
        Producto rato = new Producto(101, "Rato", 10.0);
        Producto portatil = new Producto(102, "Portatil", 600.0);
        Producto tablet = new Producto(103, "Tablet", 400.0);
        Producto teclado = new Producto(104, "Teclado", 200.0);

        Pedido pedido1 = new Pedido(1, null, "Cliente1");
        List<Producto> listacli1 = new ArrayList<>();
        listacli1.add(monitor);
        listacli1.add(rato);
        pedido1.setListaCompra(listacli1);
        System.out.println(pedido1);

        Pedido pedido2 = new Pedido(2, null, "Cliente2");
        List<Producto> listacli2 = new ArrayList<>();
        listacli2.add(portatil);
        listacli2.add(tablet);
        pedido2.setListaCompra(listacli2);
        System.out.println(pedido2);

        Pedido pedido3 = new Pedido(3, null, "Cliente3");
        List<Producto> listacli3 = new ArrayList<>();
        listacli3.add(monitor);
        listacli3.add(rato);
        pedido3.setListaCompra(listacli3);
        System.out.println(pedido3);


        Pedido pedido4 = new Pedido(4, null, "Cliente4");
        List<Producto> listacli4 = new ArrayList<>();
        listacli4.add(teclado);
        pedido4.setListaCompra(listacli4);
        System.out.println(pedido4);


        Pedido pedido5 = new Pedido(5, null, "Cliente5");
        List<Producto> listacli5 = new ArrayList<>();
        listacli5.add(monitor);
        listacli5.add(rato);
        pedido5.setListaCompra(listacli5);
        System.out.println(pedido5);


        List<Pedido> listaPedidos = new ArrayList<>();
        listaPedidos.add(pedido1);
        listaPedidos.add(pedido2);
        listaPedidos.add(pedido3);
        listaPedidos.add(pedido4);
        listaPedidos.add(pedido5);

        System.out.println(listaPedidos);

        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("ListadePedidos.dat"));) {
            for (Pedido m : listaPedidos) {
                escritor.writeObject(m);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        List<Pedido> pedidosDeserializados = new ArrayList<>();


        try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream("ListadePedidos.dat"));) {
            while (true) {
                Object pedido = lector.readObject();
                if (pedido instanceof Pedido) {
                    pedidosDeserializados.add((Pedido) pedido);
                }
            }

        } catch (EOFException ex) {
            System.out.println("Hemos llegado al final del archivo.");
            for (Pedido m : pedidosDeserializados) {
                System.out.println(m);
            }

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }

        try {
            // Crear un DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("Pedidos");
            doc.appendChild(rootElement);

            for (Pedido pedido : pedidosDeserializados) {

                Element pedidoElement = doc.createElement("Pedido");
                rootElement.appendChild(pedidoElement);

                Element idPedido = doc.createElement("IDpedido");
                idPedido.appendChild(doc.createTextNode(String.valueOf(pedido.getId())));
                pedidoElement.appendChild(idPedido);

                Element nombreCliente = doc.createElement("Cliente");
                nombreCliente.appendChild(doc.createTextNode(pedido.getNombreCliente()));
                pedidoElement.appendChild(nombreCliente);

                Element listaCompraElement = doc.createElement("ListaCompra");
                pedidoElement.appendChild(listaCompraElement);

                for (Producto producto : pedido.getListaCompra()) {
                    // Crear el elemento Producto
                    Element productoElement = doc.createElement("Producto");
                    listaCompraElement.appendChild(productoElement);

                    // Crear y añadir ID del producto
                    Element idProducto = doc.createElement("IDproducto");
                    idProducto.appendChild(doc.createTextNode(String.valueOf(producto.getId())));
                    productoElement.appendChild(idProducto);

                    // Crear y añadir descripción del producto
                    Element productoDescripction = doc.createElement("Descripción");
                    productoDescripction.appendChild(doc.createTextNode(producto.getDescripcion()));
                    productoElement.appendChild(productoDescripction);

                    // Crear y añadir precio del producto
                    Element productoPrecio = doc.createElement("Precio");
                    productoPrecio.appendChild(doc.createTextNode(String.valueOf(producto.getPrecio())));
                    productoElement.appendChild(productoPrecio);
                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Pedidos.xml"));

            // Transformar el DOM y escribir el archivo XML
            transformer.transform(source, result);

            System.out.println("El archivo XML ha sido creado correctamente.");


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
