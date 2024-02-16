# PlantillaAutomatismoJava
Este proyecto contiene una plantilla base con funciones comunes que se podran reutilizar en los automatismo java

# Recomendacion
Para mejor comprencion de esta plantilla se recomienda estudiar las dependenias
las cuales son otros proyectos que se han incorporado a esta plantilla, y contienen
clases y metodos que se implementan en este proyecto, las dependencias son:

    * AppConfiguration
    * Email
    * RemoteConnection
    * Incident-ws
    * Logger 
    * plantillaAutomatismo

# Editar las constantes en el archivos Constans.java, para que el resto de codigo las tome como parametros de configuracion 
# Prestablesidos, .
    VERSION     = "DD/MM/AAAA"
    LOG         = "Log[REQ2023_106].log"
    REQ_NAME    = "REQ2023_106"

# Explicacion de los paquetes y archivos 
Antes de comenzar hacer un desarrollo en esta plantilla temnga en cuenta lo siguiente:

    1. Sustituiremos la palabre: (nameproject) en todos los paquetes, por el nombre del proyecto que trabajaremos, ya que (nameproject), esta por defecto.
    2. Paquete nameproject
        En este paquete tendremos dos archivos el de Launcher y Rutinas:
        Launcher: Sera nuestro archivo que inicie la automatizacion, es el arranque
        Rutina: Aca ira toda la logica de negocio para automatizar
    3. Paquete constants
        Contiene varios ficheros los cuales tienen dentro las constantes mas usadas para loa automatismos de Claro, pero se destina particularmente 
        el archivo Constants, para introducir las constantes de negocios que se requieran usar.
        Los demas archivos con variables estan por la frecuencia de uso de esta variables en los proyectos de automatizacion si considera necesario puede eliminar los archivos que no requiera.
    4. Paquete Core
        Estos archivos no deben ser modificados en ellos se encuentra los archivos que contine la logica que se usa por lo general para hacer los automatismos de claro
        En caso de anexar una nueva funcionalidad al Core, asegurese de subir la misma al repositorio de plantilla para que los demas programadores puedan hacer uso de ella
    5. Paquete DTO
        En caso de necesitarse se tiene esta paquete para poder tener la informacion en local y poderla usar para generar archivos en PDF o Excel, o para otro uso que lo requiera
    6. Paquete enums
        Contiene un archivo en el cual hay codigos para regular las cabeceras de XML de los diferentes servicios. 
        En caso de anexar nuevas informacion de cabeceras asegurese de subir al repositorio de plantillas.
    7. Paquete Util
        Contine un archivo el cual se programan utilidades necesarias para los desarrallos, este archivo tampoco debe ser borrado.
        En caso de anexar nuevas funcionalidades asegurece de subirlas al repositorio de plantilla
    9. Paquete client
        Este paquete contiene archivos para los consumos de SOAP o REST que se requiera consumir.
        cabe destacar que este archivo si puede ser modificado y debe adaptarlo al consumo que se requiera