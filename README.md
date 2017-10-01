### Curso Vert.x | : Ejemplos: "El nacimiento de un super Saiyan"
--------

![](image/foto_1.jpg)

#### Lib Microservice Common
--------

Librería perteneciente al [ejemplo de microservicios en Vert.x](https://github.com/paradigmadigital/curso-vertx-libs). La fuente original la podéis encontrar [aquí](http://www.sczyh30.com/vertx-blueprint-microservice/index.html#blueprint-common-module).

>Se debe ejecutar la construcción de esta librería ya que los ejemplos hacen uso de ella.

```
 mvn clean install 
```


#### Ejemplos
--------

- [Ejemplo 1](https://github.com/paradigmadigital/curso-vertx-1/tree/master/example1-http-server): Aprendizaje básico de creación de un servidor http.
- [Ejemplo 2](https://github.com/paradigmadigital/curso-vertx-1/tree/master/example2-templates-client): Uso de Módulo web + plantillas + socket server
- [Ejemplo 3](https://github.com/paradigmadigital/curso-vertx-1/tree/master/example3-api-rest): Creación de un REST-API

  
####  Requisitos básicos
--------

- Maven 3.X
- Jdk 1.8
- VisualVM
- Jmeter
- MongoDB

####  Recursos
--------

- Fichero build_and_run.sh
- Jmeter
- Postman

   
####  Configura el arranque en tu IDE de cualquiera de los ejemplos
--------

En eclipse (o el IDE que se desee) ejecutar el Verticle que se quiera del siguiente modo:

 - Seleccionar la clase Starter como "MainClass"
 
 	![Configuración local](image/config1.png)
 
 	
 - Añadir el comando run PAQUETERIA_Y_NOMBRE_DE_VERTICLE -conf ruta_al_fichero_de_configuracion 	 
 - Añadir la variable de entorno de la JVM para los logs  -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -Dhazelcast.logging.type=slf4j 
 - Añadir la variable de entorno de la JVM para el arranque en modo debug  -Dvertx.options.blockedThreadCheckInterval=1000000 

	
![Configuración local](image/config2.png)
   
   
#### Equipo de desarrollo 
--------

 - J.Manuel García Rozas (Arquitecto) jmgrozas@gmail.com 
 - Ernesto Valero (Arquitecto de sistemas) evalero@paradigmadigital.com
 - Teresa Quintano (Front Developer) tquintano@paradigmadigital.com
   
   
   
   
   
   
