
# Plantilla Automatismo Java

Este proyecto cuenta con una plantilla base que incluye funciones comunes que pueden ser reutilizadas en los automatismos Java.

# Fuentes

[Plantilla GitHub](https://github.com/OmarReina/PlantillaAutomatismoJava.git),
[Repositorio automatismos documentación SVN](https://10.67.106.110:8443/svn/documentacion/trunk/SRS/Procesos),
[Repositorio automatismos documentación Fuentes](https://10.67.106.110:8443/svn/Automatismos/Fuentes),
[Repositorio components](https://10.67.106.110:8443/!/#Componentes/view/head/trunk/Fuentes/Components)

# Recomendaciones

Para una mejor comprensión de esta plantilla, se recomienda estudiar las dependencias asociadas. Estas dependencias son otros proyectos que se han incorporado a esta plantilla y contienen clases y métodos que se implementan en este proyecto. Las dependencias son:

 - RemoteConnections
 - AppConfiguration
 - Email
 - ExcelSpreadsheets
 - Hitss
 - Incident-ws
 - Logger
 - Processor

Todas estas están asociadas al artefacto **Components** y deben ser compiladas previamente para que puedan crearse las bibliotecas en Maven.

# Modificaciones iniciales

En el archivo **pom.xml**, es necesario modificar los tags <artifactId> y <name> por el valor correspondiente al REQ.


```xml
<artifactId>REQ_year_number</artifactId> 
<name>REQ_year_number</name>
```

Además, se debe modificar la propiedad <buildVersion>, la cual corresponde al valor de la versión compilada del log.

```xml
<buildVersion>1.2.3</buildVersion>
```

1. Indica que es la versión principal del software.
2. Indica que se han realizado cambios menores en la funcionalidad del software, como la corrección de errores o la mejora del rendimiento.
3. Indica que se han realizado pequeñas mejoras en la funcionalidad del software o correcciones de errores.

# Explicación de los paquetes y archivos
Antes de comenzar el desarrollo en esta plantilla, tenga en cuenta lo siguiente:
```
Sustitución de la palabra (nameproject): En todos los paquetes, se reemplazará la palabra (nameproject) por el nombre del proyecto en el que se está trabajando, ya que (nameproject) es el valor por defecto.
```
### Paquete nameproject:
En este paquete, encontrará dos archivos: Launcher y Rutinas.
Launcher: Este archivo será nuestro punto de inicio para la automatización, es decir, el arranque.
Rutina: Aquí se colocará toda la lógica de negocio para la automatización.

### Paquete constants:
Contiene varios archivos que contienen las constantes más utilizadas para los automatismos de Claro.
Se destaca particularmente el archivo Constants, donde se introducen las constantes de negocios necesarias.
Los demás archivos con variables se organizan según la frecuencia de uso en los proyectos de automatización. Si es necesario, puede eliminar los archivos que no se requieran.

### Paquete Core:
Estos archivos no deben ser modificados, ya que contienen la lógica utilizada comúnmente para realizar los automatismos de Claro.
En caso de añadir una nueva funcionalidad al Core, asegúrese de subirla al repositorio de la plantilla para que otros programadores puedan hacer uso de ella.

### Paquete DTO:
Este paquete se utiliza para almacenar los DTO generalmente para el tratamiendo de datos de los informes o uso necesario.

### Paquete enums:
Archivos que definen un conjunto de constantes relacionadas. Se puede pensar en él como una lista finita de valores posibles para una variable.

### Paquete Util:
Contiene un archivo donde se programan utilidades necesarias para el desarrollo. Este archivo tampoco debe ser borrado.
En caso de añadir nuevas funcionalidades y que estos metodos sean genericos, asegúrese de subirlas al repositorio de la plantilla.

### Paquete client:
Este paquete contiene el cliente para el consumo de servicios SOAP o REST que se requieran.
Es importante destacar que este archivo puede ser modificado y debe ser adaptado al consumo necesario.