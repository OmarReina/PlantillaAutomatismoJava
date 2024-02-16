/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.nameproject;

import co.com.claro.nameproject.constans.Log4jConstans;
import co.com.claro.nameproject.constans.Constans;
import co.com.claro.nameproject.core.AppFiles;
import co.com.claro.nameproject.core.RoutineProperties;
import co.com.claro.nameproject.core.BuildVersion;
import co.com.claro.nameproject.util.Util;
import com.claro.logger.ClaroLogger;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
/**
 *
 * Clase principal que inicia la aplicación y ejecuta la rutina automatizada.
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        long timeIni = System.currentTimeMillis();
        try {
            Util.deleteFile(Constans.LOG);
            ClaroLogger.infoProgrammerLog(String.format(Log4jConstans.LOG4J_INICIO_METODO, new Object() {
            }.getClass().getEnclosingMethod().getName()));

            /**
             * validar los directorios de la aplicacion
             */
            AppFiles.createFoldersProcess();
            AppFiles.limpiarLogs();
            /**
             * inicializan las propiedades
             */

            if (RoutineProperties.init()) {
                /**
                 * Se registra el evento de ejecucion de la rutina
                 */
                ClaroLogger.infoProgrammerLog("Inicia proceso automatizado");
                Rutina rutina = new Rutina();
//                rutina.loadFiles();
//                rutina.calculateOverTemp();
//                rutina.executeCaseOneOverTemp();
                /**
                 * Se registra el evento de cierre de la rutina
                 */
                ClaroLogger.infoProgrammerLog("Finaliza proceso automatizado");
            } else {
                ClaroLogger.infoProgrammerLog("No se inicializaron las propiedades de la rutina");
            }
        } catch (Exception ex) {
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
        } finally {
            //se eliminan los directorios temporales antes de finalizar la aplicacion
            AppFiles.deteleFoldersProcess();
            ClaroLogger.infoProgrammerLog(String.format(Log4jConstans.LOG4J_TERMINA_METODO, new Object() {
            }.getClass().getEnclosingMethod().getName()));
            ClaroLogger.infoProgrammerLog(String.format(Log4jConstans.LOG4J_TIEMPO_TRANSACCION, (System.currentTimeMillis() - timeIni)));
            BuildVersion.init();
            ClaroLogger.infoProgrammerLog("Finaliza automatizacion version: " + BuildVersion.properties.getProperty("build.version") + " Compilacion: " + BuildVersion.properties.getProperty("build.date"));
            Util.moveLogContent();
            System.exit(1);
        }
    }
}
