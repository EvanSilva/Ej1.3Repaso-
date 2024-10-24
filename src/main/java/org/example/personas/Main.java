package org.example.personas;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Persona personaSola = new Persona("Paco", 10);

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Coelho",10));
        personas.add(new Persona("Marta",100));
        personas.add(new Persona("Papadospoulos",60));
        personas.add(new Persona("Perdo",25));

        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("Personasola.bin"));) {

            escritor.writeObject(personaSola);

        } catch (IOException ex) {
            System.err.println(ex);
        }

        try (ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("Personas.bin"));) {

            for (Persona persona: personas) {
                escritor.writeUTF(persona.getName());
                escritor.writeInt(persona.getAge());
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        List<Persona> personasDesbinarizadas = new ArrayList<>();

        try (ObjectInputStream lector = new ObjectInputStream(new FileInputStream("personas.bin"))) {
            while (true) {
                String nombre = lector.readUTF();
                int edad = lector.readInt();
                personasDesbinarizadas.add(new Persona(nombre, edad));
            }
        } catch (EOFException e) {

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado", e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }

        // Aquí puedes imprimir las personas desbinarizadas
        for (Persona persona : personasDesbinarizadas) {
            System.out.println(persona.toString());
        }

        Element root = new Element("personas");
        Document documento = new Document(root);

        for (Persona persona : personasDesbinarizadas) {
            Element personaElement = new Element("persona");
            personaElement.addContent(new Element("nombre").setText(persona.getName()));
            personaElement.addContent(new Element("edad").setText(String.valueOf(persona.getAge())));
            root.addContent(personaElement);
        }

        try (FileOutputStream fos = new FileOutputStream("personas.xml")) {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(documento, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SAXBuilder saxBuilder = new SAXBuilder();
        File archivoXML = new File("personas.xml"); // Asegúrate de que este archivo exista

        try {
            // Construir el documento desde el archivo
            Document document = saxBuilder.build(archivoXML);
            Element rootLectura = document.getRootElement();

            List<Element> listaPersonas = root.getChildren("persona");

            for (Element persona : listaPersonas) {
                String nombre = persona.getChildText("nombre");
                int edad = Integer.parseInt(persona.getChildText("edad"));

                // Imprimir los datos de cada persona
                System.out.println("Nombre: " + nombre + ", Edad: " + edad);
            }


        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (JDOMException e) {
            System.err.println("Error en el formato XML: " + e.getMessage());
        }




    }
}