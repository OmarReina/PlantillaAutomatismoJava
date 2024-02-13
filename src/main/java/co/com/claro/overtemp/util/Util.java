package co.com.claro.overtemp.util;

import co.com.claro.overtemp.constans.Constans;
import co.com.claro.overtemp.core.SSHConnector;
import com.claro.dbProperties.DBProperties;
import com.claro.dbProperties.Property;
import com.claro.logger.ClaroLogger;
import com.claro.sendMessages.mail.MailMessage;
import com.claro.sendMessages.mail.SessionFactory;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
public class Util {

    /**
     *
     * Lee un archivo XML ubicado en la URL especificada y lo convierte en una
     * lista de objetos DTO.
     *
     * @param <T> Parámetro genérico DTO
     * @param xml Archivo XML para leer.
     * @param tag El nombre de la etiqueta que se buscará y se agrupara dentro
     * del documento XML.
     * @param dto La clase que representa el DTO (objeto de transferencia de
     * datos) para los datos XML.
     * @param fields Una matriz de campos que representan las propiedades de la
     * clase DTO.
     * @return Una lista de objetos DTO rellenados con datos del archivo XML.
     * @throws Exception si se produce algún error durante el análisis XML o el
     * proceso de creación de instancias de DTO.
     *
     *
     */
    public static <T> List<T> readXMLToListDto(Document xml, String tag, Class<T> dto, Field[] fields) throws Exception {
        try {
            List<T> listDto = new ArrayList<>();
            NodeList nodeList = xml.getElementsByTagName(tag);
            // Iterar sobre los elementos con la etiqueta especificada en el documento XML
            for (int i = 0; i < nodeList.getLength(); i++) {
                T obj = dto.newInstance();
                Element element = (Element) nodeList.item(i);

                // Establecer los valores de campo del objeto DTO
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (((Element) element.getElementsByTagName(field.getName()).item(0)) != null) {
                        setFieldValue(obj, field.getName(), ((Element) element.getElementsByTagName(field.getName()).item(0)).getFirstChild().getNodeValue());
                    }
                }

                // Agregue el objeto DTO poblado a la lista
                listDto.add(obj);
            }
            return listDto;
        } catch (Exception ex) {
            // Lanzar una excepción general con información adicional
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            };
            return null;
        }
    }

    /**
     *
     * Establece el valor de un campo en un objeto DTO usando la reflexión.
     *
     * @param <T> Parámetro genérico DTO
     * @param dto El objeto DTO a modificar.
     * @param fieldName El nombre del campo a configurar.
     * @param value El valor a asignar al campo.
     * @throws Exception si ocurre algún error durante el proceso de
     * modificación del campo.
     */
    public static <T> void setFieldValue(T dto, String fieldName, Object value) throws Exception {
        Class<?> dtoClass = dto.getClass();

        // Obtener el campo correspondiente basado en el nombre proporcionado
        Field field = dtoClass.getDeclaredField(fieldName);
        // Generar el nombre del método setter
        String setterName = "set" + capitalize(fieldName);
        // Obtener el método setter correspondiente al campo
        Method setterMethod = dtoClass.getMethod(setterName, field.getType());
        // Invocar el método setter para establecer el valor en el DTO segun su tipo de dato
        if (field.getType() == String.class) {
            setterMethod.invoke(dto, value);
        } else if (field.getType() == Integer.class || field.getType() == int.class) {
            setterMethod.invoke(dto, Integer.parseInt(value.toString()));
        } else if (field.getType() == Double.class || field.getType() == double.class) {
            setterMethod.invoke(dto, Double.parseDouble(value.toString()));
        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            setterMethod.invoke(dto, Boolean.parseBoolean(value.toString()));
        } else if (field.getType() == Long.class || field.getType() == long.class) {
            setterMethod.invoke(dto, Long.parseLong(value.toString()));
        } else {
            ClaroLogger.infoProgrammerLog("Tipo de dato no soportado: " + field.getType().getName());
        }
    }

    /**
     *
     * Pone en mayúscula el primer carácter de una cadena.
     *
     * @param str La cadena a escribir en mayúsculas.
     * @return La cadena en mayúsculas.
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     *
     * Recupera un documento XML desde un servidor remoto usando SSH.
     *
     * @param host El nombre de host o la dirección IP del servidor remoto.
     * @param user El nombre de usuario para autenticarse con el servidor
     * remoto.
     * @param password La contraseña para autenticarse con el servidor remoto.
     * @param path La ruta al archivo XML en el servidor remoto.
     * @return El documento XML obtenido del servidor remoto.
     * @throws Exception si se produce algún error durante la conexión SSH, la
     * recuperación de archivos o el proceso de análisis XML.
     */
    public static Document getXmlOfSSH(String host, String user, String password, String path) throws Exception {
        SSHConnector server = new SSHConnector();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        try {
            server.connect(user, password, host, 22);
            ClaroLogger.infoProgrammerLog("Conexion establecida al servidor: " + host);
            Document document = builder.parse(server.getFile(path));
            return document;
        } catch (Exception ex) {
            if (ex.getMessage().equals("No such file")) {
                ClaroLogger.infoProgrammerLog("No se encuentra el archivo en la ruta indicada: " + host + path);
                // Cree una fábrica de creación de documentos y analice el archivo XML del servidor
                return builder.newDocument();
            } else {
                // Lanzar una excepción general con información adicional
                ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                    ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                    ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
                };
            }
            return null;
        } finally {
            // Desconectar la sesión SSH
            server.disconnectSession();
        }
    }

    /**
     * Recupera un documento XML del sistema de archivos local.
     *
     * @param path La ruta del archivo del archivo XML.
     * @return El documento XML obtenido del archivo local.
     * @throws Exception si se produce algún error durante la recuperación del
     * archivo o el proceso de análisis XML.
     */
    public static Document getXmlOfLocal(String path) throws Exception {
        try {
            // Cree una fábrica de creación de documentos y analice el archivo XML del sistema de archivos local
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(path));
        } catch (Exception ex) {
            if (ex.getMessage().equals("No such file")) {
                ClaroLogger.infoProgrammerLog("No se encuentra el archivo en la ruta indicada: " + path);
                // Cree una fábrica de creación de documentos y analice el archivo XML del servidor
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                return builder.newDocument();
            } else {
                // Lanzar una excepción general con información adicional
                ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                    ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                    ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
                };
                return null;
            }
        }
    }

    /**
     *
     * Establece un archivo de texto en un servidor remoto a través de SSH y
     * escribe el contenido de una lista de objetos en el archivo.
     *
     * @param <T>
     * @param host el host del servidor remoto.
     * @param user el nombre de usuario para la conexión SSH.
     * @param password la contraseña para la conexión SSH.
     * @param path la ruta en el servidor remoto donde se ubicará el archivo.
     * @param dto la lista de objetos que se escribirán en el archivo.
     * @param fileName el nombre del archivo.
     * @throws Exception si ocurre algún error durante la ejecución.
     */
    public static <T> void setFileTxtOfSSH(String host, String user, String password, String path, List<T> dto, String fileName) throws Exception {
        // Utilizar try-with-resources para garantizar que el BufferedWriter se cierre automáticamente
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tmp/" + fileName, true))) {
            // Recorrer la lista de objetos y escribir sus campos en el archivo
            for (T data : dto) {
                Field[] fields = data.getClass().getDeclaredFields();
                StringBuilder linea = new StringBuilder();
                // Construir una línea de texto con los campos y valores del objeto
                for (Field field : fields) {
                    field.setAccessible(true);
                    String nombreCampo = field.getName();
                    Object valorCampo = field.get(data) == null ? "" : field.get(data);
                    linea.append(nombreCampo).append(": ").append(valorCampo.toString()).append(" ");
                }
                // Escribir la línea en el archivo
                writer.write(linea.toString().trim());
                writer.newLine();
            }

            ClaroLogger.infoProgrammerLog("Los elementos se han agregado " + dto.size() + " registros al archivo de texto.");

            // Conectar al servidor SSH
            ClaroLogger.infoProgrammerLog("Conexion establecida al servidor: " + host);
            SSHConnector server = new SSHConnector();
            server.connect(user, password, host, 22);
            ClaroLogger.infoProgrammerLog("inicia cargue de archivo de texto: " + fileName);
            server.setFile(path, fileName);

            // Desconectar la sesión SSH
            server.disconnectSession();
            ClaroLogger.infoProgrammerLog("Desconexion al servidor: " + host);
        } catch (Exception ex) {
            if (ex.getMessage().equals("No such file")) {
                ClaroLogger.infoProgrammerLog("No se encuentra el archivo en la ruta indicada: " + host + path);
            } else {
                // Lanzar una excepción general con información adicional
                ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
                StackTraceElement[] stackTrace = ex.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                    ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                    ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
                };
            }
        }
    }

    /**
     *
     * Elimina un archivo en la ruta especificada.
     *
     * @param path la ruta del archivo a eliminar.
     * @throws Exception si ocurre algún error durante la eliminación del
     * archivo.
     */
    public static void deleteFile(String path) throws Exception {
        try {
            File archivo = new File(path);
            FileInputStream inputStream = new FileInputStream(archivo);
            inputStream.close();
            if (archivo.delete()) {
                ClaroLogger.infoProgrammerLog("El archivo ha sido eliminado correctamente.");
            } else {
                ClaroLogger.infoProgrammerLog("No se pudo eliminar el archivo.");
            }
        } catch (Exception ex) {
            ClaroLogger.infoProgrammerLog("No se encuentra el archivo en la ruta indicada: " + path);
        }
    }

    /**
     *
     * Crea un informe en formato Excel a partir de una lista de objetos DTO.
     *
     * @param dto la lista de objetos DTO.
     * @param fileName el nombre del archivo de Excel a crear.
     * @param sheetName el nombre de la hoja de cálculo en el archivo de Excel.
     * @throws Exception si ocurre algún error durante la creación del informe.
     */
    public static <T> void createReportExcel(List<T> dto, String fileName, String sheetName) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(sheetName);
            int rowIndex = 0;

            // Crear la primera fila para los nombres de los campos
            XSSFRow headerRow = sheet.createRow(rowIndex);
            Field[] fields = dto.get(0).getClass().getDeclaredFields();
            int columnIndex = 0;

            for (Field field : fields) {
                field.setAccessible(true); // Hacer el campo accesible

                XSSFCell headerCell = headerRow.createCell(columnIndex);
                headerCell.setCellValue(field.getName());

                columnIndex++;
            }

            // Incrementar el índice de la fila para los datos
            rowIndex++;

            // Insertar los datos en las columnas correspondientes
            for (T data : dto) {
                XSSFRow dataRow = sheet.createRow(rowIndex);
                columnIndex = 0;

                for (Field field : fields) {
                    field.setAccessible(true); // Hacer el campo accesible
                    Object value = field.get(data);

                    XSSFCell dataCell = dataRow.createCell(columnIndex);
                    if (value != null) {
                        if (value instanceof String) {
                            dataCell.setCellValue(((String) value).trim());
                        } else if (value instanceof Number) {
                            dataCell.setCellValue(((Number) value).doubleValue());
                        } else if (value instanceof Boolean) {
                            dataCell.setCellValue((Boolean) value);
                        } else if (value instanceof Date) {
                            dataCell.setCellValue((Date) value);
                        } else {
                            dataCell.setCellValue(value.toString());
                        }
                    }
                    columnIndex++;
                }

                rowIndex++;
            }
            // Ajustar el ancho de las columnas según el contenido
            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream(fileName + Constans.EXTENSION_XLSX);
            workbook.write(fileOut);

        } catch (Exception ex) {
            // Lanzar una excepción general con información adicional
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            };
        }
    }

    /**
     *
     * Envía un correo electrónico con adjuntos.
     *
     * @param sender el remitente del correo electrónico.
     * @param subject el asunto del correo electrónico.
     * @param recipient el destinatario principal del correo electrónico.
     * @param copyRecipient el destinatario en copia del correo electrónico.
     * @param body el cuerpo del correo electrónico.
     * @param pathFile la ruta del archivo adjunto.
     * @throws Exception si ocurre algún error durante el envío del correo
     * electrónico.
     */
    public static void sendEmailWithAttachments(String sender, String subject, String recipient, String copyRecipient, String body, String pathFile) throws Exception {
        try {
            Property mailHost = DBProperties.get("email.host");
            Session session = SessionFactory.getDefaultSession(mailHost.getValue());
            MailMessage email = new MailMessage(session);
            email.setFrom(sender);
            email.setTo(recipient);
            email.setCc(copyRecipient);
            email.setSubject(subject);
            email.setText(body);
            email.attachFile(pathFile);
            email.sendMessage();
            ClaroLogger.infoProgrammerLog("Envia correo de la rutina ejecutada");
        } catch (Exception ex) {
            // Lanzar una excepción general con información adicional
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            };
        }
    }

    /**
     * Convierte un objeto a su representación JSON.
     *
     * @param object el objeto a ser convertido a JSON.
     * @return la representación JSON del objeto.
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     *
     * Extrae el texto que se encuentra entre dos cadenas específicas dentro de
     * un texto dado.
     *
     * @param texto el texto del cual se desea extraer el contenido.
     * @param cadenaInicial la cadena que marca el inicio del contenido a
     * extraer.
     * @param cadenaFinal la cadena que marca el final del contenido a extraer.
     * @return el texto contenido entre las cadenas inicial y final, o null si
     * no se encuentra el contenido.
     */
    public static String extractTextBetweenStrings(String texto, String cadenaInicial, String cadenaFinal) {
        int indiceInicial = texto.indexOf(cadenaInicial);
        int indiceFinal = texto.indexOf(cadenaFinal);

        if (indiceInicial != -1 && indiceFinal != -1) {
            // Agregamos la longitud de la cadena inicial para obtener la posición del primer carácter después de ella
            indiceInicial += cadenaInicial.length();

            if (indiceInicial < indiceFinal) {
                return texto.substring(indiceInicial, indiceFinal);
            }
        }

        return null; // Si no se encuentra la cadena inicial o final, o si el índice inicial es mayor o igual al índice final, retornamos una cadena vacía
    }

    /**
     * Este método se encarga de mover el contenido de un archivo de registro
     * (log) desde una ubicación de origen a una de destino. Si el archivo de
     * origen no existe, registra un mensaje de error y sale del método. Si la
     * ubicación de destino no existe, crea los directorios necesarios. Después,
     * copia el archivo de origen al destino y registra un mensaje de éxito. Si
     * ocurre alguna excepción durante el proceso, registra un mensaje de error
     * y la pila de llamadas (stack trace) para diagnóstico.
     *
     * @throws Exception si ocurre un error durante la ejecución del método.
     */
    public static void moveLogContent() throws Exception {

        // Ruta del archivo de origen (log)
        Path sourcePath = Paths.get(Constans.LOG);

        // Ruta del archivo de destino para el log movido
        Path destinationPath = Paths.get("logs/" + Constans.LOG_SOAP);

        try {
            // Verificar si el archivo de origen existe
            if (!Files.exists(sourcePath)) {
                ClaroLogger.errorProgrammerLog("El archivo de origen no existe");
                return;
            }

            // Verificar si la ubicación de destino existe, si no, crearla
            if (!Files.exists(destinationPath.getParent())) {
                Files.createDirectories(destinationPath.getParent());
            }

            // Copiar el archivo de origen al destino (reemplazándolo si ya existe)
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Registrar un mensaje de éxito
            ClaroLogger.infoProgrammerLog("Se crea " + destinationPath);

        } catch (Exception ex) {
            // En caso de excepción, registrar un mensaje de error y la pila de llamadas (stack trace)
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            };
        }
    }
}
