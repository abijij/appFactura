package org.abijij.appfactura;

import org.abijij.appfactura.model.Cliente;
import org.abijij.appfactura.model.Factura;
import org.abijij.appfactura.model.ItemFactura;
import org.abijij.appfactura.model.Producto;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.abijij.appfactura.model.Factura.contieneNumeros;

public class EjemploFactura {
    public static void main(String[] args) {

        System.out.println("Bienvenido vamor a realizar la factura");
        Cliente cliente = new Cliente();
        String nombref = null;
        String RFC = null;
        Scanner c = new Scanner(System.in);
        do {
            try {
                System.out.println("Ingresa tu nombre completo");
                nombref = c.nextLine();

                if (contieneNumeros(nombref)){
                    System.out.println("El nombre no debe contener números. Por favor, ingrese una descripción válida");
                    nombref = null;
                } else if (nombref.isEmpty()) {
                    System.out.println("Por favor ingrese un nombre valido");

                    nombref = null;
                }

            }catch (InputMismatchException e) {

                System.out.println("Por favor ingrese un nombre valido");
                c.nextLine();
                nombref = null;

            }

        }while (nombref == null);

        cliente.setNombre(nombref);

        do {
            System.out.println("Ingresa tu RFC");
            RFC = c.nextLine();
            if (RFC.isEmpty() || RFC.length() < 12){
                System.out.println("Por favor ingrese un RFC valido");

            }

        }while (RFC.isEmpty() || RFC.length() < 12 );


        cliente.setRFC(RFC);



        String desc = null;
        Scanner s = new Scanner(System.in);
        do {
            try {
                System.out.println("Ingrese la descripcion de la factura: ");
                 desc = s.nextLine();

                 if (contieneNumeros(desc)){
                     System.out.println("La descripción no debe contener números. Por favor, ingrese una descripción válida");
                     desc = null;
                 } else if (desc.isEmpty()) {
                     System.out.println("Por favor una descripcion valida");
                     desc = null;
                 }

            }catch (InputMismatchException e) {
                System.out.println("Por favor ingrese una descripcion valida");
                s.next();
                desc = null;

            }

        }while (desc==null);


        Factura factura = new Factura(cliente,desc);

        Producto producto;
        String nombre ;
        Float precio ;
        String descripcion ;
        int stock = 0 ;
        int cantidad = 0 ;

        System.out.println();

        for (int i =0; i<5; i++){
            producto = new Producto();

            do {
                System.out.print("Ingrese producto n# " + producto.getId() + ": ");
                nombre = s.nextLine();
                if (nombre.isEmpty()){
                    System.out.println("Por favor ingrese un nombre valido");
                }
                else if (contieneNumeros(nombre)){
                    System.out.println("La descripción no debe contener números. Por favor, ingrese una descripción válida");
                    nombre = null;
                }


            }while (nombre.isEmpty());
            producto.setNombre(nombre);
            do {
                try {
                    System.out.print("Ingrese el precio: $ ");
                    String input = s.nextLine().trim();  // Leer toda la línea y eliminar espacios en blanco
                    if (input.isEmpty()) {
                        System.out.println("Por favor, ingrese un precio válido.");
                        precio = -1f;
                    } else {
                        precio = Float.parseFloat(input);
                        if (precio < 0) {
                            System.out.println("Por favor, ingrese un precio válido.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un valor numérico para el precio.");
                    precio = -1f;
                }
            } while (precio < 0);
            producto.setPrecio(precio);

            do {
                System.out.println("Ingrese la descripcion del producto: ");
                descripcion = s.nextLine();
                if (descripcion.isEmpty()){
                    System.out.println("Por favor ingrese una descripcion valida");
                }
                else if (contieneNumeros(descripcion)){
                    System.out.println("La descripción no debe contener números. Por favor, ingrese una descripción válida");
                    descripcion = null;
                }
            }while (descripcion.isEmpty());
            producto.setDescripcion(descripcion);

            do {
                System.out.print("Ingrese el stock del producto: ");
                String stockInput = s.nextLine().trim();
                if (stockInput.isEmpty()) {
                    System.out.println("Por favor, ingrese un stock válido.");
                    stock = -1;
                } else {
                    try {
                        stock = Integer.parseInt(stockInput);
                        if (stock < 0) {
                            System.out.println("Por favor, ingrese un stock válido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un valor numérico para el stock.");
                        stock = -1;  // Establecer un valor no válido para repetir el bucle
                    }
                }
            } while (stock < 0);
            producto.setStock(stock);

            do {
                System.out.print("Ingrese la cantidad: ");
                while (!s.hasNextInt()) {
                    System.out.println("Error: Ingrese un valor numérico para la cantidad.");
                    s.next(); // Consumir entrada no válida
                }
                cantidad = s.nextInt();

                if (cantidad <= 0) {
                    System.out.println("Por favor, ingrese una cantidad válida mayor que cero.");
                } else if (cantidad > stock) {
                    System.out.print("No hay suficiente Stock del producto. Vuelva a seleccionar la cantidad.");
                }

                s.nextLine(); // Consumir el carácter de nueva línea
            } while (cantidad <= 0 || cantidad > stock);

            ItemFactura item = new ItemFactura(producto, cantidad);

            factura.addItemFactura(item);

            System.out.println();
           

        }
        System.out.println(factura.generarDetalle());
    }
}
