### **Curso Vert.x : Ejemplo 1**
--------
Siguiendo el [video de los ejemplos](https://www.youtube.com/watch?v=pVsmdwgaUWE) podrán entender las funcionalidades que se ofrecen en este ejemplo más ampliamente. 


#### **Objetivos**
--------
- Librería common de blueprints
- Organización común en todos los ejemplos
- Event Loop
- Arrancar Vertx Interfaz (Alternativa a un main directo), comando de vert.x + vert.x options
- Crear un servidor http
- Crear rutas, comportamiento anidado de las rutas
- LocalData y SharedData en el Bus
- Workers - Event Loop -> asignaciones
- Monitorización mediante JMX (VisualVM, Jconsole)
- Medición de rendimiento (Jmeter)


#### **Publicación**
--------
Se publicarán varios endpoints:

- /test
- /test/test1
- /ping
- /add/:counter 

#### **Ejecución**
--------

>**Mediante script**

```
 build_and_run.sh
```

>**Haciendo uso del IDE**

Para arrancarlo de este modo se deben seguir las instrucciones disponibles en la documentación de la raíz de los ejemplos, siendo el Verticle a ejecutar **com.vertx.example1.Example1MainVerticle**

#### **Equipo de desarrollo** 
--------

 - J.Manuel García Rozas (Arquitecto) jmgrozas@gmail.com 
 - Ernesto Valero (Arquitecto de sistemas) evalero@paradigmadigital.com
 - Teresa Quintano (Front Developer) tquintano@paradigmadigital.com

