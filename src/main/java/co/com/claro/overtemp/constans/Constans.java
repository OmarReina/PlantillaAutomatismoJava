/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.overtemp.constans;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian Betancourt</a>
 */
public class Constans {

    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HHMM = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
    public static final DateTimeFormatter FORMATTER_YYYYMMDD_HHMM = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
    public static final DateTimeFormatter FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Constante que define el nombre de las propiedades de log4j
     */
    public static final String LOG4J_INICIO_METODO = "-- INICIO TRANSACCION METODO [ %s ]";
    public static final String LOG4J_TERMINA_METODO = "-- TERMINA TRANSACCION METODO [ %s ]";
    public static final String LOG4J_REQUEST = "-- REQUEST -- : %s";
    public static final String LOG4J_WSDL = "-- WSDL -- : %s";
    public static final String LOG4J_METODO = "-- METODO -- : %s";
    public static final String LOG4J_NAME_WS = "-- NAME WS -- : %s";
    public static final String LOG4J_RESPONSE = "-- RESPONSE -- : %s";
    public static final String LOG4J_TIMEOUT = "-- TIMEOUT -- : %s Segundos";
    public static final String LOG4J_TIEMPO_TRANSACCION = "-- TIEMPO DE EJECUCION (ms) -- : %s";
    public static final String LOG4J_PARAMETROS_ENTRADA = "-- PARAMETROS DE ENTRADA -- : %s";
    public static final String LOG4J_PARAMETROS_SALIDA = "-- PARAMETROS DE SALIDA -- : %s";
    public static final String LOG4J_VARIABLE = "-- VARIABLE -- : %s";
    public static final Integer SECONDS_TIMEOUT_SOAP_DEFAULT = 5;

    /**
     * Constantes generales
     */
    public static final String VERSION = "13/AGOSTO/2023";
    public static final String LOG = "Log[REQ2023_106].log";
    public static final String UNDERSCORE = "_";
    public static final String SINGLE_QUOTE = "'";
    public static final String SINGLE_QUOTE_AND_COMMA = "',";
    public static final String EXTENSION_TXT = ".txt";
    public static final String EXTENSION_XLSX = ".xlsx";
    public static final String CASE_1 = "CASE_1";
    public static final String CASE_2 = "CASE_2";
    public static final String VACIO = "";
    public static final String HISTORICO_PATH = "historico";
    public static final String HISTORICO_PATH_DAY = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER_YYYYMMDD);
    public static final String HISTORICO_PATH_YESTERDAY = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    public static final String WEEK = String.format("%02d", LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
    public static final String DAY = String.format("%02d", LocalDate.now().getDayOfMonth());
    public static final String XLSX_FILE_NAME = "tmp/ReportTemperatureThreshold_";
    public static final String XLSX_SHEET_NAME = "Equipments";
    public static final String QUERY_ELEMENT = "<element>";
    public static final String CINAME = "CINAME";
    public static final String REGIONAL = "REGIONAL";
    public static final String REGIONAL_ORIENTE = "ORIENTE";
    public static final String REGIONAL_NORTE = "NORTE";
    public static final String REGIONAL_COSTA = "COSTA";
    public static final String REGIONAL_CENTRO = "CENTRO";
    public static final String REGIONAL_CENTRO_ORIENTE = "CENTRO ORIENTE";
    public static final String REGIONAL_OCCIDENTE = "OCCIDENTE";
    public static final String SIN_REGIONAL = "SIN_REGIONAL";
    public static final String CILOCATION = "CILOCATION";
    public static final String CINUM = "CINUM";

//    public static final String MAIL_COPY_RECIPIENT = "backtransmisionclaro@claro.com.co";
    public static final String MAIL_ELEMENT_TABLE_NAME_1 = "AAC";
    public static final String MAIL_ELEMENT_TABLE_NAME_2 = "AAG";
    public static final String MAIL_ELEMENT_TABLE_NAME_3 = "ACO";
    public static final String MAIL_ELEMENT_TABLE_NAME_4 = "CORE/AN0X/CN0X";
    public static final String MAIL_ELEMENT_TABLE_NAME_VALUE_1 = "<NAME_VALUE_1>";
    public static final String MAIL_ELEMENT_TABLE_NAME_VALUE_2 = "<NAME_VALUE_2>";
    public static final String MAIL_ELEMENT_TABLE_NAME_VALUE_3 = "<NAME_VALUE_3>";
    public static final String MAIL_ELEMENT_TABLE_NAME_VALUE_4 = "<NAME_VALUE_4>";
    public static final String MAIL_ELEMENT_TABLE_VALUE_1 = "<VALUE_1>";
    public static final String MAIL_ELEMENT_TABLE_VALUE_2 = "<VALUE_2>";
    public static final String MAIL_ELEMENT_TABLE_VALUE_3 = "<VALUE_3>";
    public static final String MAIL_ELEMENT_TABLE_VALUE_4 = "<VALUE_4>";
    public static final String MAIL_ELEMENT_MAXIMO_TABLE_VALUE = "<MAXIMO_VALUE>";
    public static final String MAIL_RECIPIENT_REGIONAL = "MAIL_RECIPIENT_REGIONAL_";

    public static final String MAXIMO_ELEMENT_INCIDENT = "INCIDENT";
    public static final String MAXIMO_ELEMENT_WORK_ORDER = "WORK_ORDER";
    public static final String MAXIMO_ELEMENT_TASK = "TASK";

    /**
     * Nombre del archivo historico
     */
    public static final String TXT_NOMBRE_HISTORICO = "HistoricoOverTemp_" + new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER_YYYYMMDD_HHMM);
    public static final String LOG_SOAP = "REQ2023-106 " + new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER_YYYY_MM_DD_HHMM)+".log";
    /**
     * Constante para obtener la fecha del dia.
     */
    public static Date FECHA_ACTUAL = new Date();
    /**
     * Constante que define el nombre del requerimiento
     */
    public static final String REQ_NAME = "REQ2023_106";
    /**
     * Constante que define el nombre consultar propiedades del req
     */
    public static final String TAG_EQUIPMENT_HW_ENVIRONMENT = "equipment.HwEnvironment";

    /**
     * Constantes conexion a servers NSP por SSH
     */
    public static final String SERVER_SSH_NSP18_IP = "server.ssh.NSP18.ip";
    public static final String SERVER_SSH_NSP18_USER = "server.ssh.NSP18.user";
    public static final String SERVER_SSH_NSP18_PASSW = "server.ssh.NSP18.passw";
    public static final String SERVER_SSH_NSP18_PATH = "server.ssh.NSP18.path";

    public static final String SERVER_SSH_NSP21_IP = "server.ssh.NSP21.ip";
    public static final String SERVER_SSH_NSP21_USER = "server.ssh.NSP21.user";
    public static final String SERVER_SSH_NSP21_PASSW = "server.ssh.NSP21.passw";
    public static final String SERVER_SSH_NSP21_PATH = "server.ssh.NSP21.path";

    public static final String SERVER_SSH_HISTORICO_IP = "server.ssh.HISTORICO.ip";
    public static final String SERVER_SSH_HISTORICO_USER = "server.ssh.HISTORICO.user";
    public static final String SERVER_SSH_HISTORICO_PASSW = "server.ssh.HISTORICO.passw";
    public static final String SERVER_SSH_HISTORICO_PATH = "server.ssh.HISTORICO.path";

    public static final String MAIL_SUBJECT = "mail.subject";
    public static final String MAIL_BODY = "mail.body";
    public static final String MAIL_SENDER = "mail.sender";
    public static final String MAIL_COPY_RECIPIENT = "mail.copy.recipient";
    public static final String MAIL_ELEMENT_DAY = "mail.element.day";
    public static final String MAIL_ELEMENT_WEEK = "mail.element.week";
    public static final String MAIL_ELEMENT_REGIONAL = "mail.element.regional";
    public static final String MAIL_RECIPIENT_REGIONAL_ORIENTE = "mail.recipient.regional.oriente";
    public static final String MAIL_RECIPIENT_REGIONAL_NORTE = "mail.recipient.regional.norte";
    public static final String MAIL_RECIPIENT_REGIONAL_COSTA = "mail.recipient.regional.costa";
    public static final String MAIL_RECIPIENT_REGIONAL_CENTRO = "mail.recipient.regional.centro";
    public static final String MAIL_RECIPIENT_REGIONAL_OCCIDENTE = "mail.recipient.regional.occidente";
    public static final String MAIL_RECIPIENT_REGIONAL_SIN_REGIONAL = "mail.recipient.regional.sin.regional";
    public static final String QUERY_FIND_REGIONAL_BY_SITENAME = "query.find.regional.by.sitename";
    public static final String QUERY_FIND_TICKET_ID_BY_SITENAME = "query.find.ticket.id.by.sitename";

    public static final String MAXIMO_USER = "services.maximo.user";
    public static final String MAXIMO_PASSWORD = "services.maximo.password";
    public static final String MAXIMO_URL_CREATE_INCIDENT = "services.maximo.url.crearIncidente";
    public static final String MAXIMO_URL_CREATE_OT = "services.maximo.url.crearOT";
    public static final String MAXIMO_URL_ASSOCIATE_OT = "services.maximo.url.asociarOT";
    public static final String MAXIMO_URL_CREATE_TASK = "services.maximo.url.crearTask";

    public static final String MAXIMO_CREATE_INCIDENT_URGENCY = "maximo.create.incident.urgency";
    public static final String MAXIMO_CREATE_INCIDENT_IMPACT = "maximo.create.incident.impact";
    public static final String MAXIMO_CREATE_INCIDENT_INTERNALPRIORITY = "maximo.create.incident.internalpriority";
    public static final String MAXIMO_CREATE_INCIDENT_CLASS = "maximo.create.incident.class";
    public static final String MAXIMO_CREATE_INCIDENT_OWNERGROUP = "maximo.create.incident.ownergroup";
    public static final String MAXIMO_CREATE_INCIDENT_CLASSSTRUCTUREID = "maximo.create.incident.classstructureid";
    public static final String MAXIMO_CREATE_INCIDENT_DESCRIPTION = "maximo.create.incident.description";

    public static final String MAXIMO_EQUIPMENT = "<EQUIPMENT>";
    public static final String MAXIMO_TEMPERATURE = "<TEMPERATURE>";
    public static final String MAXIMO_REGIONAL = "<REGIONAL>";
    public static final String MAXIMO_NAME_WS_CREATE_INCIDENT = "WS_CL_Incident_Crear";
    public static final String MAXIMO_NAME_WS_CREATE_OT = "WS_cl_create_ot";
    public static final String MAXIMO_NAME_WS_ASSOCIATE_OT = "WS_Cl_WoTicket";
    public static final String MAXIMO_NAME_WS_CREATE_TASK = "WS_CreateTaskIncident";
    public static final String MAXIMO_METHOD_WS_CREATE_INCIDENT = "CreateCL_TICKET_R";
    public static final String MAXIMO_METHOD_WS_CREATE_OT = "CreateCL_CREATE_OT";
    public static final String MAXIMO_METHOD_WS_ASSOCIATE_OT = "SyncCL_WOTICKET";
    public static final String MAXIMO_METHOD_WS_CREATE_TASK = "CreateWOACTIVITY";

}
