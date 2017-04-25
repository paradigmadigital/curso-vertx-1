# Primer ejemplo de servidor http más configuraciones de plantillas de cliente

La finalidad será crear:

- Servidor http + servir estáticos + uso de templates de servidor
- Servidor http + websockes

# En eclipse Ejecutar el Verticle que se quiera del siguiente modo:

 - Seleccionar la clase Starter
 - Añadir el comando run PAQUETERIA_Y_NOMBRE_DE_VERTICLE -conf ruta_al_fichero_de_configuracion
 - Añadir la variable de entorno de java  -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory
 - Añadir la variable de entorno de java para el debug de -Dvertx.options.blockedThreadCheckInterval=1000000 

# Build and run 

Ejecutar build_and_run.sh
