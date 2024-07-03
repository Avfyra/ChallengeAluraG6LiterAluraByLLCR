package com.aluradesafios.literalurachallenge.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int añoNacimiento;
    private int añoMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Autores(DatosAutores datosAutores) {
        this.name = datosAutores.nombreAutor();
        this.añoNacimiento = datosAutores.añoNacimiento();
        this.añoMuerte = datosAutores.añoMuerte();
    }

    public Autores() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(int añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public int getAñoMuerte() {
        return añoMuerte;
    }

    public void setAñoMuerte(int añoMuerte) {
        this.añoMuerte = añoMuerte;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        String librosTitulos = libros.stream()
                .map(libro -> libro.getTitulo())
                .collect(Collectors.joining(", "));

        return "--------------- AUTOR ---------------\n" +
                "Autor: " + name + "\n" +
                "Fecha de nacimiento: " + añoNacimiento + "\n" +
                "Fecha de fallecimiento: " + añoMuerte + "\n" +
                "Libros: " + librosTitulos + "\n" +
                "-------------------------------------\n";
    }
}

