package com.aluradesafios.literalurachallenge.principal;

import com.aluradesafios.literalurachallenge.models.*;
import com.aluradesafios.literalurachallenge.repository.IAutoresRepository;
import com.aluradesafios.literalurachallenge.repository.ILibrosRepository;
import com.aluradesafios.literalurachallenge.service.ConsumoApi;
import com.aluradesafios.literalurachallenge.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final static String URL_BASE = "https://gutendex.com/books/?search=";

    private IAutoresRepository autoresRepository;
    private ILibrosRepository librosRepository;

    public Principal(IAutoresRepository autoresRepository, ILibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void muestraElMenu() {
        int opcion;
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        System.out.println("\n*** Bienvenido a LiterAlura ***\n");

        while (!salir) {
            System.out.println("Seleccione una opción, por favor.\n");
            System.out.println("1 - Buscar libros por título");
            System.out.println("2 - Mostrar libros registrados");
            System.out.println("3 - Mostrar autores registrados");
            System.out.println("4 - Mostrar autores vivos en un determinado año");
            System.out.println("5 - Mostrar libros por idioma");
            System.out.println("6 - Top 10 libros más descargados");
            System.out.println("7 - Obtener estadísiticas");
            System.out.println("0 - Salir");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibros();
                        break;
                    case 2:
                        librosRegistrados();
                        break;
                    case 3:
                        autoresRegistrados();
                        break;
                    case 4:
                        autoresPorFecha();
                        break;
                    case 5:
                        librosPorIdioma();
                        break;
                    case 6:
                        topDiezLibros();
                        break;
                    case 7:
                        estaditicasApi();
                        break;
                    case 0:
                        System.out.println("Cerrando aplicación...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida, intenta de nuevo");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opción no válida, intenta de nuevo");
                scanner.nextLine(); // Consumir la entrada no válida
            }
        }
    }

    private Datos getDatosLibros() {
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
        return datosLibros;
    }

    private Libros crearLibro(DatosLibros datosLibros, Autores autor) {
        if (autor != null) {
            return new Libros(datosLibros, autor);
        } else {
            System.out.println("Sin autor, no es posible registrar el libro");
            return null;
        }
    }

    private void buscarLibros() {
        System.out.println("Escribe el libro que deseas buscar: ");
        Datos datos = getDatosLibros();

        if (datos.resultados().isEmpty()) {
            System.out.println("\nEl libro no existe en la API de Gutendex, ingresa otro\n");
            return;
        }

        DatosLibros datosLibro = datos.resultados().get(0);
        DatosAutores datosAutores = datosLibro.autor().get(0);

        Libros libroRepositorio = librosRepository.findByTitulo(datosLibro.titulo());
        if (libroRepositorio != null) {
            System.out.println("\nEste libro ya está registrado en la base de datos\n");
            System.out.println(libroRepositorio.toString());
            return;
        }

        Autores autorRepositorio = autoresRepository.findByNameIgnoreCase(datosAutores.nombreAutor());
        Libros libro;
        if (autorRepositorio != null) {
            libro = crearLibro(datosLibro, autorRepositorio);
        } else {
            Autores autor = new Autores(datosAutores);
            autor = autoresRepository.save(autor);
            libro = crearLibro(datosLibro, autor);
        }

        librosRepository.save(libro);
        System.out.println("\n----- LIBRO REGISTRADO -----\n");
        System.out.println(libro);
    }

    private void librosRegistrados() {
        List<Libros> libros = librosRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
            System.out.println("La base de datos está vacía");
            return;
        }

        System.out.println("\n----- LIBROS REGISTRADOS EN LA BASE DE DATOS SON: -----\n");
        libros.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);
    }

    private void autoresRegistrados() {
        List<Autores> autores = autoresRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados");
            return;
        }

        System.out.println("\n----- LOS AUTORES REGISTRADOS EN LA BASE DE DATOS SON: -----");
        autores.sort(Comparator.comparing(Autores::getName));
        for (Autores autor : autores) {
            System.out.println(autor.toString());
        }
    }

    private void autoresPorFecha() {
        System.out.println("\nEscribe el año en el que deseas buscar: ");
        var año = teclado.nextInt();
        teclado.nextLine();
        if(año < 0) {
            System.out.println("\nEl año no puede ser negativo, intenta de nuevo");
            return;
        }
        List<Autores> autoresPorAño = autoresRepository.findByAñoNacimientoLessThanEqualAndAñoMuerteGreaterThanEqual(año, año);
        if (autoresPorAño.isEmpty()) {
            System.out.println("\nNo hay autores registrados en ese año");
            return;
        }
        System.out.println("\n----- LOS AUTORES VIVOS REGISTRADOS EN EL AÑO " + año + " SON: -----\n");
        autoresPorAño.stream()
                .sorted(Comparator.comparing(Autores::getName))
                .forEach(System.out::println);
    }

    private void librosPorIdioma() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\nEscribe la clave del idioma por el que deseas buscar.");
            System.out.println("O ingrsa 0 para volver al menú principal.\n");
            System.out.println("es - Español");
            System.out.println("en - Inglés");
            System.out.println("fr - Francés");
            System.out.println("pt - Portugués");
            System.out.println("0 - Volver al menú principal");

            String idioma = teclado.nextLine().toLowerCase();
            switch (idioma) {
                case "es":
                case "en":
                case "fr":
                case "pt":
                    List<Libros> librosPorIdioma = librosRepository.findByLenguajesContaining(idioma);
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("\nNo hay libros registrados en el idioma seleccionado");
                    } else {
                        System.out.println("\n----- LOS LIBROS REGISTRADOS EN EL IDIOMA SELECCIONADO SON: -----");
                        librosPorIdioma.stream()
                                .sorted(Comparator.comparing(Libros::getTitulo))
                                .forEach(System.out::println);
                    }
                    validInput = true;
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    validInput = true;
                    break;
                default:
                    System.out.println("\nIdioma no válido, intenta de nuevo o ingresa 0 para volver al menú principal");
            }
        }
    }

    private void topDiezLibros() {
        System.out.println("\n----- LOS 10 LIBROS MÁS DESCARGAS EN LA API GUTENDEX SON: -----");
        var json = consumoApi.obtenerDatos(URL_BASE);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        List<Libros> librosGutendex = new ArrayList<>();
        for (DatosLibros datosLibros : datos.resultados()) {
            Autores autor = new Autores(datosLibros.autor().get(0));
            Libros libro = new Libros(datosLibros, autor);
            librosGutendex.add(libro);
        }
        librosGutendex.stream()
                .sorted(Comparator.comparing(Libros::getNumeroDescargas).reversed())
                .limit(10)
                .forEach(System.out::println);

//        System.out.println("\n----- LOS 10 LIBROS MÁS DESCARGADOS REGISTRADOS EN LA BASE DE DATOS SON: -----");
        List<Libros> librosBaseDatos = librosRepository.findAll();
        if (librosBaseDatos.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
            return;
        }
        System.out.println("\n----- LOS 10 LIBROS MÁS DESCARGADOS REGISTRADOS EN LA BASE DE DATOS SON: -----");
        librosBaseDatos.stream()
                .sorted(Comparator.comparing(Libros::getNumeroDescargas).reversed())
                .limit(10)
                .forEach(System.out::println);
    }

    private void estaditicasApi() {
        System.out.println("\n----- ESTADÍSTICAS DE DESCARGAS EN LA API GUTENDEX -----\n");
        var json = consumoApi.obtenerDatos(URL_BASE);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        DoubleSummaryStatistics estadisticasGutendex = datos.resultados().stream()
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        Optional<DatosLibros> libroMaxDescargas = datos.resultados().stream()
                .max(Comparator.comparingDouble(DatosLibros::numeroDescargas));
        Optional<DatosLibros> libroMinDescargas = datos.resultados().stream()
                .min(Comparator.comparingDouble(DatosLibros::numeroDescargas));

        System.out.println("Libro con más descargas: " + libroMaxDescargas.map(DatosLibros::titulo).orElse("N/A"));
        System.out.println("Número de descargas: " + estadisticasGutendex.getMax());
        System.out.println("Libro con menos descargas: " + libroMinDescargas.map(DatosLibros::titulo).orElse("N/A"));
        System.out.println("Número de descargas: " + estadisticasGutendex.getMin());
        System.out.println("Promedio de descargas: " + estadisticasGutendex.getAverage());
        System.out.println();

//        System.out.println("----- ESTADÍSTICAS DE DESCARGAS EN LA BASE DE DATOS -----");
        List<Libros> libros = librosRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
            return;
        }
        DoubleSummaryStatistics estadisticasBaseDatos = libros.stream()
                .collect(Collectors.summarizingDouble(Libros::getNumeroDescargas));
        Optional<Libros> libroMaxDescargas2 = libros.stream()
                .max(Comparator.comparingDouble(Libros::getNumeroDescargas));
        Optional<Libros> libroMinDescargas2 = libros.stream()
                .min(Comparator.comparingDouble(Libros::getNumeroDescargas));

        System.out.println("\n----- ESTADÍSTICAS DE DESCARGAS EN LA BASE DE DATOS -----\n");
        System.out.println("Libro con más descargas: " + libroMaxDescargas2.map(Libros::getTitulo).orElse("N/A"));
        System.out.println("Número de descargas: " + estadisticasBaseDatos.getMax());
        System.out.println("Libro con menos descargas: " + libroMinDescargas2.map(Libros::getTitulo).orElse("N/A"));
        System.out.println("Número de descargas: " + estadisticasBaseDatos.getMin());
        System.out.println("Promedio de descargas: " + estadisticasBaseDatos.getAverage());
        System.out.println();
    }
}


