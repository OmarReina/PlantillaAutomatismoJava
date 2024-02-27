/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.nameproject.constans;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
public class Constans {

    /**
     * Constante para obtener la fecha del dia.
     */
    public static Date FECHA_ACTUAL = new Date();

    /**
     * Constantes formato de fechas
     */
    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HHMM = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
    public static final DateTimeFormatter FORMATTER_YYYYMMDD_HHMM = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
    public static final DateTimeFormatter FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Constante que define el nombre y log del requerimiento
     */
    public static final String REQ_NAME = "REQ2024_123";
    public static final String LOG = "Log[" + REQ_NAME + "].log";
    public static final String LOG_SAVE = REQ_NAME + new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER_YYYY_MM_DD_HHMM) + ".log";

    /**
     * Constante que define el nombre consultar propiedades del req
     */
    public static final String TAG_EQUIPMENT_HW_ENVIRONMENT = "equipment.HwEnvironment";

    /**
     * Nombre del archivo historico
     */
    public static final String TXT_NOMBRE_HISTORICO = "HistoricoOverTemp_" + new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER_YYYYMMDD_HHMM);

    public static final String UNDERSCORE = "_";
    public static final String SINGLE_QUOTE = "'";
    public static final String SINGLE_QUOTE_AND_COMMA = "',";
    public static final String VACIO = "";
    public static final String EXTENSION_TXT = ".txt";
    public static final String EXTENSION_XLSX = ".xlsx";
}
