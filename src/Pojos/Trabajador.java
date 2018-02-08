/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import Vistas.FormUtilidadEmpleado;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author clopez
 */
public class Trabajador {

    private String nombre;
    private String cedula;
    private String cargo;
    private Float salario;
    private String tiempo;
    private String categoria = "A";

    public Trabajador(String nombre, String cedula, String cargo, Float salario, String tiempo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.cargo = cargo;
        this.salario = salario;
        this.tiempo = tiempo;
    }

    public Trabajador() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Float calcularUtilidad(String tiempo, float salario) {
        float utilidad = 0;
        if (tiempo.equals("Menos de 1 año")) {
            utilidad = (float) (salario * 0.05);
        }
        if (tiempo.equals("1 año o mas y menos de 2 años")) {
            utilidad = (float) (salario * 0.07);
        }        
        if (tiempo.equals("Dos años o mas y menos de 5 años")) {
            utilidad = (float) (salario * 0.10);
        }
        if (tiempo.equals("5 años o mas y menos de 10 años")) {
            utilidad = (float) (salario * 0.15);
        }
        if (tiempo.equals("10 años o mas")) {
            utilidad = (float) (salario * 0.20);
        }
        return utilidad;
    }

    public String guardar() {
        String mensaje = "Error al guardar el empleado";
        String ruta = new File("").getAbsolutePath() + "\\src\\Modelo\\";
        File archivo = new File(ruta + "BDEmpleados.txt");
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (FileWriter escribir = new FileWriter(archivo, true)) {
                    escribir.write(getCedula() + "," + getNombre() + "," + getCargo() + "," + getSalario() + "," + getTiempo() + "\n");
                    escribir.close();
                }
            } else {
                try (FileWriter escribir = new FileWriter(archivo, true)) {
                    escribir.write(getCedula() + "," + getNombre() + "," + getCargo() + "," + getSalario() + "," + getTiempo() + "\n");
                    escribir.close();
                }
            }
            mensaje = "Empleado Guardado";
        } catch (IOException ex) {
            System.out.println("error al crear el archivo " + ex.getMessage());
        }
        return mensaje;
    }

    public void mostrar() {
        String ruta = new File("").getAbsolutePath() + "\\src\\Modelo\\BDEmpleados.txt";
        File archivo = new File(ruta);
        String usuario = "";
        if (archivo.exists()) {
            DefaultTableModel modelo;
            String Titulos[] = {"Cedula", "Nombre", "Cargo", "Salario", "Antiguedad", "Utilidad", "Categoria"};
            modelo = new DefaultTableModel(null, Titulos) {
                @Override
                public boolean isCellEditable(int row, int column) { //para evitar que las celdas sean editables
                    return false;
                }
            };
            Object[] columna = new Object[7];
            try {
                FileReader lector = new FileReader(ruta);
                BufferedReader contenido = new BufferedReader(lector);
                while ((usuario = contenido.readLine()) != null) {
                    String datos[] = usuario.split(",");
                    columna[0] = datos[0];
                    columna[1] = datos[1];
                    columna[2] = datos[2];
                    columna[3] = datos[3];
                    columna[4] = datos[4];
                    columna[5] = calcularUtilidad(datos[4],Float.parseFloat(datos[3]));
                    columna[6] = getCategoria(datos[4]);
                    modelo.addRow(columna);
                }
                FormUtilidadEmpleado.tblListUsers.setModel(modelo);
            } catch (IOException ex) {
                System.out.println("error =" + ex.getMessage());
            }
        }
    }

    public String getCategoria(String tiempo) {
        if (tiempo.equals("5 años o mas y menos de 10 años")) {
            categoria = "B";
        }
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }   
}
