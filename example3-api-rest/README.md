### **Curso Vert.x : Ejemplo 3**
--------
Siguiendo el [video de los ejemplos](https://www.youtube.com/watch?v=pVsmdwgaUWE) podrán entender las funcionalidades que se ofrecen en este ejemplo más ampliamente. 


#### **Objetivos**
--------

- Construcción real de API RESTful (RestApiVerticle)
- Comunicación Handler + Verticle
- Bloqueo de código y alternativas
- Verticle síncrono, Workers y Pool del mismo
- Comportamientos de workers y verticles
- Monitorización y rendimiento



#### **Publicación**
--------
Se publicará el API sensors con los verbos de sus operaciones:

- /sensors



#### **Ejecución**
--------
> **Nota:**

> - No olvides construir la librería [microservice common](https://github.com/paradigmadigital/curso-vertx-libs) si no lo has hecho ya.
> - No olvides arrancar un MongoDB en local y configurar el fichero config.json 


>**Mediante script**

```
 build_and_run.sh
```

>**Haciendo uso del IDE**

Para arrancarlo de este modo se deben seguir las instrucciones disponibles en la documentación de la raíz de los ejemplos, siendo el Verticle a ejecutar **com.vertx.example3.Example3MainVerticle**

#### **Equipo de desarrollo** 
--------

 - J.Manuel García Rozas (Arquitecto) jmgrozas@gmail.com 
 - Ernesto Valero (Arquitecto de sistemas) evalero@paradigmadigital.com
 - Teresa Quintano (Front Developer) tquintano@paradigmadigital.com
