/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.nameproject.core;

import co.com.claro.nameproject.util.Util;
import com.claro.logger.ClaroLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Sergio Alejandro
 */
public class AppFiles {

    /*
     * Constante de ruta local donde se almacenan los archivos descargados.
     */
    public static final String REPORT_PATH = "report/";


    /*
     * Constante de ruta local donde reposan los archivos LOG.
     */
    public static final String LOG_PATH = "logs/";
    public static final String TMP_PATH = "tmp/";

    public static void createFoldersProcess() throws IOException {
        //deteleFolder(FILES_PATH);
        createFolder(REPORT_PATH);
        createFolder(TMP_PATH);
        createFolder(LOG_PATH);
        System.out.println("Directorios temporales para el flujo de la aplicacion creados");
    }

    public static void deteleFoldersProcess() throws Exception {
        deteleFolder(REPORT_PATH);
        deteleFolder(TMP_PATH);
    }

    public static void limpiarLogs() throws IOException {
        File logFolder = new File(LOG_PATH);
        File[] archivosLog = logFolder.listFiles((File pathname) -> {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -15);
            long oldFecha = cal.getTime().getTime();
            return pathname.isFile() && pathname.lastModified() <= oldFecha;
        });

        if (archivosLog.length > 0) {
            System.out.println("Limpia los log que lleven mas de un mes generados");
            for (File archivosLog1 : archivosLog) {
                archivosLog1.delete();
            }
        }
    }

    public static void createFolder(String pathElement) throws IOException {
        File file = new File(pathElement);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void deteleFolder(String path) throws Exception {
        File file = new File(path);
        try {
            if (file.exists()) {
                if (file.isDirectory() && file.list().length > 0) {
                    // Si no es un directorio, simplemente elimina el archivo
                    for (String name : file.list()) {
                        File archivo = new File(path + name);
                        InputStream inputStream = new FileInputStream(archivo);
                        inputStream.close();
                        if (archivo.delete()) {
                            ClaroLogger.infoProgrammerLog("El archivo " + name + "  ha sido eliminado correctamente.");
                        } else {
                            ClaroLogger.infoProgrammerLog("No se pudo eliminar el archivo " + name);
                        }
                    }
                    ClaroLogger.infoProgrammerLog("Se elimina el archivo contenido en la carpeta: " + path);
                } else {
                    FileUtils.deleteDirectory(file); // Utiliza la biblioteca Apache Commons IO para eliminar la carpeta y su contenido
                    ClaroLogger.infoProgrammerLog("Se elimina la carpeta: " + path + " y su contenido");

                }
            }
        } catch (Exception ex) {
            ClaroLogger.errorProgrammerLog("No se pudo eliminar la carpeta: " + path + " ni su contenido", ex);
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
            }
        }
    }

    private static void deleteDirectory(Path directory) throws IOException {
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    /**
     * <pre>
     * Checks if a string is a valid path.Null safe.Calling examples:
     * isValidPath("c:/test");      //returns true
     * isValidPath("c:/te:t");      //returns false
     * isValidPath("c:/te?t");      //returns false
     * isValidPath("c/te*t");       //returns false
     * isValidPath("good.txt");     //returns true
     * isValidPath("not|good.txt"); //returns false
     * isValidPath("not:good.txt"); //returns false</pre>
     *
     * @param path
     * @return TRUE/FALSE
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
