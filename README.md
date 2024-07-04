# LiterAlura
#### Challenge Alura G6, ONE.
Programa en Java, que implementa la API Gutendex, para buscar libros y agregarlos a una base de datos local, visualizar los libros y autores registrados, buscar a un autor activo en determinado año y filtrar los libros por idioma. 
Además este código incluye dos funcionalidades extras; mostrar el top 10 de los libros más descargados y mostrar las estadísticas generales de las descargas de los libros.

Esta aplicación es parte de los Challenges de AluraLatam, G6, que forman parte del programan Oracle Next Education.

## Funcionalidades
La aplicación muestra el siguiente menú de opciones:

![Captura de pantalla 2024-07-04 102743](https://github.com/Avfyra/ChallengeAluraG6LiterAluraByLLCR/assets/159713105/7d6c4d02-01bd-4fc0-b2f2-e68d83d8728b)


Opción 1: Buscar libros por título.
+ Pide al usuario que ingrese el titulo del libro que desea buscar
+ La aplicación realiza la solicitud a la API para mostrar el libro en caso de ser encontrado y le informará, además de adicionarlo a la base de datos. 
+ En caso contrario, es decir, si se ingresa un libro que no se encuentre en la API o se ingrese una frase sin sentido, la app le indicará que verifique sus datos.
+ Por otro lado, si se busca el mismo título dos veces, la aplicación le mostrará los datos del libro solicitado y le informará que ya existe dicho registro.

Opción 2: Mostrar libros registrados.
+ Al abrir el programa por primera vez, la base de datos estará vacía, por lo que al realizar esta solicitud le indicará que no hay registros. (Esto ocurre al ejecutar primero esta opción antes de realizar alguna búsqueda)
+ Una vez que la base de datos contenga algún registro, esta opción le mostrará todos los libros registrados.

Opción 3: Mostrar autores registrados.
+ Al abrir el programa por primera vez, la base de datos estará vacía, por lo que al realizar esta solicitud le indicará que no hay registros. (Esto ocurre al ejecutar primero esta opción antes de realizar alguna búsqueda)
+ Una vez que la base de datos contenga algún registro, esta opción le mostrará todos los autores registrados.

Opción 4: Mostrar autores vivos en un determinado año.
+ Al abrir el programa por primera vez, la base de datos estará vacía, por lo que al realizar esta solicitud le indicará que no hay autores registrados en el año solicitado. (Esto ocurre al ejecutar primero esta opción antes de realizar alguna búsqueda)
  Lo mismo ocurre si la base de datos no posee registros de autores en el año solicitado.
+ Una vez que la base de datos contenga registros, la app realizará la búsqueda y de tener alguna coincidencia con la solicitud, mostrará a todos los autores vivos en el año solicitado.

Opción 5: Mostrar libros por idioma.
+ La app le mostrará un menú secundario en el cual aparecerán las claves, con sus respectivos idiomas, disponibles para realizar la búsqueda, o regresar al menú principal. En este punto se le pedirá al usuario seleccionar uno e introducir la clave para realizar la búsqueda.
+ En caso de que se ejecute primero esta opción, antes de buscar libros para que sean registrados en la base de datos, la app le indicará que no hay libros registrados con el idioma seleccionado, y volverá al menú principal.
  Lo mismo ocurre cuando no tiene registros de libros en el idioma seleccionado. 
+ En caso de ingresar una opción no válida, la app le indicará que dicha opción no es valida y le mostrará nuevamente el menú de los idiomas.
+ Cuando la base de datos contenga registros del idioma que haya seleccionado, le mostrará los libros registrados.

Opción 6: Top 10 libros más descargados.
+ Al ejecutar está opción, al app solicitará la información a la API y a la base de datos ya que mostrará el top 10 de ambas partes.
+ En caso de se ejecute esta opción antes de haber realizado una búsqueda, dado que la base de datos estará vacía, la app le mostrará el top 10 de la API y le indicará que no hay libros registrados.
+ Si la base de datos ya cuenta con registros, la app le mostrará el top 10 tanto de la API como de los libros registrados en la base de datos.
+ Si el número de registros es menor a 10, le mostrará todos los registros disponibles.

Opción 7: Obtener estadísticas.
+ Al ejecutar está opción, al app solicitará la información a la API y a la base de datos ya que mostrará las estadísticas de ambas partes.
+ Si se ejecuta esta opción antes de haber realizado alguna búsqueda, es decir, cuando la base de datos no tiene registros, la app mostrará el título del libro con más descargas y el número de descargas, el título del libro con menos descargas y el número de descargas, y el promedio general de descargas de la API, y en el caso de la base de datos le dirá que no hay registros. 
+ Una vez que la base de datos tengo libros registrados, la app mostrará el título del libro mas descargado y su número de descargas, el título del libro menos descargado y su número de descargas, y el promedio general de descargas tanto de la API como de la base de datos

Opción 0: Salir de la aplicación.

Nota: Las opciones 6 y 7 son un adicional a la propuesta del challenge. 

## Demostración 
Funcionamiento de la aplicación. 
![Grabación de pantalla 2024 07 04 115325](https://github.com/Avfyra/ChallengeAluraG6LiterAluraByLLCR/assets/159713105/7a7232d5-29c8-459d-bda9-b88ecdc48d81)

## Requisitos
Para el correcto funcionamiento de la aplicación se recomienda el uso de:
+ Java Development Kit (JDK) 17 o superior.
+ Conexión a Internet para obtener la información mediante la API.
+ IDE de su preferencia.
+ PostgreSQL
+ Configurar los datos o las variables de entorno para conectar la aplicación a la base de datos.

## Instalación y uso
+ Para acceder a la aplicación puedes descargar directamente el repositorio desde GitHub, o bien clonar el proyecto por medio del comando **git clone**.
+ Recuerda verificar que cumples con los requisitos para correr la aplicación.
+ Configura los datos o las variables de entorno para conectar la aplicación a la base de datos.

## Contribución
Las contribuciones siempre son bienvenidas. Puede seguir estos pasos para colaborar:
+ Realice un fork del repositorio
+ Clone el repositorio
+ Actualice la rama master
+ Crea una rama
+ Haga los cambios
+ Haga un Pull Request

## Consideraciones
Esta es la primera versión de la app, por lo que puede estar sujeta a futuros cambios. 

