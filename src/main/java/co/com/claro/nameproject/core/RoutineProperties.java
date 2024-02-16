/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.nameproject.core;

import co.com.claro.nameproject.constans.Constans;
import com.claro.appconfiguration.AppConfig;
import com.claro.appconfiguration.AppConfigurationRuntimeException;

import com.claro.dbProperties.DBProperties;
import com.claro.dbProperties.Property;
import com.claro.logger.ClaroLogger;
import com.claro.remote.database.DataBaseClientWrapper;
import java.util.Map;

public class RoutineProperties {

    private static Map<String, Property> propertiesMap;

    public static Boolean init() {
        try {
            ClaroLogger.infoProgrammerLog("Inicio consulta propiedades " + Constans.REQ_NAME);
            AppConfig.init(Constans.REQ_NAME);
            propertiesMap = DBProperties.getMap();
        } catch (AppConfigurationRuntimeException e) {
            ClaroLogger.errorProgrammerLog("No fue posible cargar las propiedades de la aplicacion.");
            return Boolean.FALSE;
        }
        if (propertiesMap.size() <= 0) {
            ClaroLogger.infoProgrammerLog("PROPERTIES ERROR: No existen propiedades para este proyecto");
            return Boolean.FALSE;
        }

        //carga la informacion de los elementos de red 
        return Boolean.TRUE;
    }

    public static Map<String, Property> getProperties() {
        return propertiesMap;
    }

    /**
     * Database Connect Contingencia ClaroT
     *
     * @return
     */
    public static DataBaseClientWrapper dbContingenciaClaroT() {
        String userBBDD = propertiesMap.get("clarot.db.user").getValue();
        String passBBDD = propertiesMap.get("clarot.db.pass").getValue();
        String urlBBDD = propertiesMap.get("clarot.db.url").getValue();

        DataBaseClientWrapper db = new DataBaseClientWrapper(urlBBDD, userBBDD, passBBDD, (String) null);
        return db;
    }

    /**
     * Database Connect Domquality
     *
     * @return
     */
    public static DataBaseClientWrapper dbDomqualityINH_NOC() {
        String userBBDD = propertiesMap.get("inh_noc.domquality.db.user").getValue();
        String passBBDD = propertiesMap.get("inh_noc.domquality.db.pass").getValue();
        String urlBBDD = propertiesMap.get("inh_noc.domquality.db.url").getValue();

        DataBaseClientWrapper db = new DataBaseClientWrapper(urlBBDD, userBBDD, passBBDD, (String) null);
        return db;
    }

    /**
     * Database Connect ReMAXIMO
     *
     * @return
     */
    public static DataBaseClientWrapper dbMaximo() {

        String urlBBDD = propertiesMap.get("maximopro.db.url").getValue();
        String userBBDD = propertiesMap.get("maximopro.db.user").getValue();
        String passBBDD = propertiesMap.get("maximopro.db.pass").getValue();

        DataBaseClientWrapper db = new DataBaseClientWrapper(urlBBDD, userBBDD, passBBDD, (String) null);
        return db;
    }
}
