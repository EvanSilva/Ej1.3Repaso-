package org.example.personas;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContadorXML {

    public static void main(String[] args) {

        SAXBuilder saxBuilder = new SAXBuilder();
        File archivoXML = new File("personas.xml");

        try {

            // Construir el documento desde el archivo
            Document document = saxBuilder.build(archivoXML);
            Element root = document.getRootElement();

            Map<String, Integer> contadorEtiquetas = new HashMap<>();

            contarEtiquetas(root, contadorEtiquetas);

            for (Map.Entry<String, Integer> entry : contadorEtiquetas.entrySet()) {
                System.out.println("Etiqueta: " + entry.getKey() + ", Cuenta: " + entry.getValue());
            }


        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (
                JDOMException e) {
            System.err.println("Error en el formato XML: " + e.getMessage());
        }

    }

    private static void contarEtiquetas(Element elemento, Map<String, Integer> contador) {
        // Incrementar la cuenta para la etiqueta actual
        String nombreEtiqueta = elemento.getName();
        contador.put(nombreEtiqueta, contador.getOrDefault(nombreEtiqueta, 0) + 1);

        // Recorrer los hijos del elemento
        List<Element> hijos = elemento.getChildren();
        for (Element hijo : hijos) {
            contarEtiquetas(hijo, contador); // Llamada recursiva para contar hijos
        }
    }

}
