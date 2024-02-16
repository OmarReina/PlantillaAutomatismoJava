/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.nameproject;

import co.com.claro.nameproject.constans.NpsForSshConstans;
import co.com.claro.nameproject.constans.Constans;
import co.com.claro.nameproject.core.RoutineProperties;
import co.com.claro.nameproject.dto.EquipmentHwEnvironmentDto;
import co.com.claro.nameproject.enums.ERequestSOAP;
import co.com.claro.nameproject.util.Util;
import co.com.claro.nameproject.ws.soap.client.ConsumoGenericoSoap;
import com.claro.logger.ClaroLogger;
import com.claro.remote.database.DataBaseClientWrapper;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
public class Rutina {
    
    /**
     * Archivo de ejemplo de como se debe implementar la logica de negocio que se
     * pida, en este archivo podemos ver comentados como extraer info de archivo, 
     * mandar correos entre otras muchas funcionalidades.
     */

//    List<EquipmentHwEnvironmentDto> equipments;
//    List<EquipmentHwEnvironmentDto> equipmentsCase;
//    Map<String, List<EquipmentHwEnvironmentDto>> equipmentMap;

    /**
     * Carga archivos mediante la lectura de datos XML de servidores remotos
     * mediante SSH y completa la lista de "equipos" con objetos DTO. Los
     * archivos XML se obtienen de dos servidores diferentes: NSP18 y NSP21.
     *
     * @throws Exception si ocurre algún error durante el proceso de carga del
     * archivo.
     */
//    public void loadFiles() throws Exception {
//        try {
//            ClaroLogger.infoProgrammerLog("Inicia proceso de carga de archivos xml");
//            Class<?> dtoClass = EquipmentHwEnvironmentDto.class;
//            Field[] fields = dtoClass.getDeclaredFields();
//            equipments = new ArrayList<>();
//            int numberEquipments = 0;
//
//            // Cargar datos XML desde el servidor NSP18
//            equipments.addAll(
//                    Util.readXMLToListDto(
//                            Util.getXmlOfSSH(
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP18_IP).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP18_USER).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP18_PASSW).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP18_PATH).getValue()
//                            ),
//                            Constans.TAG_EQUIPMENT_HW_ENVIRONMENT,
//                            EquipmentHwEnvironmentDto.class,
//                            fields
//                    )
//            );
//            numberEquipments += equipments.size();
//
//            ClaroLogger.infoProgrammerLog("Finaliza proceso de carga de archivos xml en el servidor NSP18 se encontraron " + numberEquipments + " registros");
//
//            // Cargar datos XML desde el servidor NSP21
//            equipments.addAll(
//                    Util.readXMLToListDto(
//                            Util.getXmlOfSSH(
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP21_IP).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP21_USER).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP21_PASSW).getValue(),
//                                    RoutineProperties.getProperties().get(NpsForSshConstans.SERVER_SSH_NSP21_PATH).getValue()
//                            ),
//                            Constans.TAG_EQUIPMENT_HW_ENVIRONMENT,
//                            EquipmentHwEnvironmentDto.class,
//                            fields
//                    )
//            );
//
//            numberEquipments = equipments.size() - numberEquipments;
//
//            ClaroLogger.infoProgrammerLog("Finaliza proceso de carga de archivos xml en el servidor NSP21 se encontraron " + numberEquipments + " registros");
//        } catch (Exception ex) {
//            // Lanzar una excepción general con información adicional
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//        }
//        ClaroLogger.infoProgrammerLog("Finaliza proceso de carga de archivos xml se encontraron " + equipments.size() + " registros");
//    }

    /**
     *
     * Calcula el caso de sobrecalentamiento para los equipos. Si la temperatura
     * de un equipo está dentro del rango [(temperaturaUmbral - 10),
     * (temperaturaUmbral - 6)], se considera como "Caso 1" y se agrega a la
     * lista de equipos correspondiente. Si la temperatura de un equipo es mayor
     * o igual a (temperaturaUmbral - 5), se considera como "Caso 2" y se agrega
     * a la lista de equipos correspondiente.
     *
     * @throws Exception si ocurre algún error durante el cálculo.
     */
//    public void calculateOverTemp() throws Exception {
//        try {
//            ClaroLogger.infoProgrammerLog("Inicia proceso de calculateOverTemp");
//            equipmentsCase = new ArrayList<>();
//
//            for (EquipmentHwEnvironmentDto equipment : equipments) {
//                if (equipment.getTemperature() >= (equipment.getTemperatureThreshold() - 10) && equipment.getTemperature() <= (equipment.getTemperatureThreshold() - 6)) {
//                    equipment.setCaseType(Constans.CASE_1);
//                    equipment.setIncidentNumber(Constans.VACIO);
//                    equipment.setOtNumber(Constans.VACIO);
//                    equipment.setTasNumber(Constans.VACIO);
//                    equipmentsCase.add(equipment);
//                } else if (equipment.getTemperature() >= (equipment.getTemperatureThreshold() - 5)) {
//                    equipment.setCaseType(Constans.CASE_2);
//                    equipmentsCase.add(equipment);
//                }
//            }
//        } catch (Exception ex) {
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//        }
//        ClaroLogger.infoProgrammerLog("Finaliza proceso de calculateOverTemp");
//    }
//
//    public void executeCasesOverTemp() throws Exception {
//
//    }
//
    /**
     *
     * Ejecuta el caso 1 de sobrecalentamiento. Recopila los equipos del caso 1
     * y los organiza por regionales. Genera informes de Excel y los envía por
     * correo electrónico a los destinatarios correspondientes. También se crea
     * un archivo de texto en un servidor remoto.
     *
     * @throws Exception si ocurre algún error durante la ejecución del caso 1
     * de sobrecalentamiento.
     */
//    public void executeCaseOneOverTemp() throws Exception {
//        try {
//            ClaroLogger.infoProgrammerLog("Inicia proceso de executeCaseOverTemp");
//            ClaroLogger.infoProgrammerLog("Inicia proceso de findRegionalBySiteName");
//            equipmentsCase = findRegionalBySiteName(equipmentsCase);
//            ClaroLogger.infoProgrammerLog("Finaliza proceso de findRegionalBySiteName");
//            Map<String, List<EquipmentHwEnvironmentDto>> regionalEquipments = new HashMap<>();
//            regionalEquipments.put(Constans.REGIONAL_ORIENTE, new ArrayList<>());
//            regionalEquipments.put(Constans.REGIONAL_NORTE, new ArrayList<>());
//            regionalEquipments.put(Constans.REGIONAL_COSTA, new ArrayList<>());
//            regionalEquipments.put(Constans.REGIONAL_CENTRO, new ArrayList<>());
//            regionalEquipments.put(Constans.REGIONAL_OCCIDENTE, new ArrayList<>());
//            regionalEquipments.put(Constans.SIN_REGIONAL, new ArrayList<>());
//            // EjecuciÃ³n caso 2
//            executeCasesTwoOverTemp();
//            // Organizar equipos por regionales
//            for (EquipmentHwEnvironmentDto equipment : equipmentsCase) {
//                String regional = equipment.getRegional() == null ? Constans.SIN_REGIONAL : equipment.getRegional().equals(Constans.REGIONAL_CENTRO_ORIENTE) ? Constans.REGIONAL_CENTRO : equipment.getRegional().toUpperCase();
//                regionalEquipments.get(regional).add(equipment);
//            }
//            // Generar informes de Excel y enviar correos electrÃ³nicos
//            for (Map.Entry<String, List<EquipmentHwEnvironmentDto>> entry : regionalEquipments.entrySet()) {
//                if (!entry.getValue().isEmpty()) {
//                    String fileName = Constans.XLSX_FILE_NAME + entry.getKey();
//                    String recipientEmail = RoutineProperties.getProperties().get(Constans.class.getField(Constans.MAIL_RECIPIENT_REGIONAL + entry.getKey()).get(null).toString().replace("_", ".").toLowerCase()).getValue();
//                    Util.createReportExcel(entry.getValue(), fileName, Constans.XLSX_SHEET_NAME);
//
//                    Map<String, Integer> valueCounts = new HashMap<>();
//                    valueCounts.put(Constans.MAIL_ELEMENT_TABLE_NAME_1, 0);
//                    valueCounts.put(Constans.MAIL_ELEMENT_TABLE_NAME_2, 0);
//                    valueCounts.put(Constans.MAIL_ELEMENT_TABLE_NAME_3, 0);
//                    valueCounts.put(Constans.MAIL_ELEMENT_TABLE_NAME_4, 0);
//                    String incidents = "";
//
//                    for (EquipmentHwEnvironmentDto values : entry.getValue()) {
//                        if (values.getCaseType().equalsIgnoreCase(Constans.CASE_1)) {
//                            if (valueCounts.get(values.getSiteName()) == null) {
//                                switch (values.getSiteName().substring(0, 3).toUpperCase()) {
//                                    case Constans.MAIL_ELEMENT_TABLE_NAME_1:
//                                        valueCounts.put(values.getSiteName().substring(0, 3).toUpperCase(), valueCounts.getOrDefault(values.getSiteName().substring(0, 3).toUpperCase(), 0) + 1);
//                                        break;
//                                    case Constans.MAIL_ELEMENT_TABLE_NAME_2:
//                                        valueCounts.put(values.getSiteName().substring(0, 3).toUpperCase(), valueCounts.getOrDefault(values.getSiteName().substring(0, 3).toUpperCase(), 0) + 1);
//                                        break;
//                                    case Constans.MAIL_ELEMENT_TABLE_NAME_3:
//                                        valueCounts.put(values.getSiteName().substring(0, 3).toUpperCase(), valueCounts.getOrDefault(values.getSiteName().substring(0, 3).toUpperCase(), 0) + 1);
//                                        break;
//                                    default:
//                                        valueCounts.put(Constans.MAIL_ELEMENT_TABLE_NAME_4, valueCounts.getOrDefault(Constans.MAIL_ELEMENT_TABLE_NAME_4, 0) + 1);
//                                }
//                                valueCounts.put(values.getSiteName(), 1);
//                            }
//                        } else {
//                            if (valueCounts.get(values.getSiteName()) == null && (values.getAverageTemperature() != null && !values.getAverageTemperature().isEmpty())) {
//                                incidents = incidents + "<tr><th>" + values.getSiteName() + "</th><td>" + values.getIncidentNumber() + "</td><td>" + values.getOtNumber() + "</td></tr>";
//                                valueCounts.put(values.getSiteName(), 2);
//                            }
//                        }
//                    }
//
//                    Util.sendEmailWithAttachments(RoutineProperties.getProperties().get(Constans.MAIL_SENDER).getValue(),
//                            RoutineProperties.getProperties().get(Constans.MAIL_SUBJECT).getValue().replace(RoutineProperties.getProperties().get(Constans.MAIL_ELEMENT_WEEK).getValue(), Constans.WEEK).replace(RoutineProperties.getProperties().get(Constans.MAIL_ELEMENT_REGIONAL).getValue(), entry.getKey()),
//                            recipientEmail,
//                            RoutineProperties.getProperties().get(Constans.MAIL_COPY_RECIPIENT).getValue(),
//                            RoutineProperties.getProperties().get(Constans.MAIL_BODY).getValue().replace(RoutineProperties.getProperties().get(Constans.MAIL_ELEMENT_WEEK).getValue(), Constans.WEEK)
//                                    .replace(RoutineProperties.getProperties().get(Constans.MAIL_ELEMENT_DAY).getValue(), Constans.DAY)
//                                    .replace(RoutineProperties.getProperties().get(Constans.MAIL_ELEMENT_REGIONAL).getValue(), entry.getKey())
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_NAME_VALUE_1, Constans.MAIL_ELEMENT_TABLE_NAME_1)
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_NAME_VALUE_2, Constans.MAIL_ELEMENT_TABLE_NAME_2)
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_NAME_VALUE_3, Constans.MAIL_ELEMENT_TABLE_NAME_3)
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_NAME_VALUE_4, Constans.MAIL_ELEMENT_TABLE_NAME_4)
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_VALUE_1, String.valueOf(valueCounts.get(Constans.MAIL_ELEMENT_TABLE_NAME_1)))
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_VALUE_2, String.valueOf(valueCounts.get(Constans.MAIL_ELEMENT_TABLE_NAME_2)))
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_VALUE_3, String.valueOf(valueCounts.get(Constans.MAIL_ELEMENT_TABLE_NAME_3)))
//                                    .replace(Constans.MAIL_ELEMENT_TABLE_VALUE_4, String.valueOf(valueCounts.get(Constans.MAIL_ELEMENT_TABLE_NAME_4)))
//                                    .replace(Constans.MAIL_ELEMENT_MAXIMO_TABLE_VALUE, incidents),
//                            fileName + Constans.EXTENSION_XLSX);
//                }
//            }
//
//            // Crear archivo de texto en un servidor remoto
//            Util.setFileTxtOfSSH(RoutineProperties.getProperties().get(Constans.SERVER_SSH_HISTORICO_IP).getValue(),
//                    RoutineProperties.getProperties().get(Constans.SERVER_SSH_HISTORICO_USER).getValue(),
//                    RoutineProperties.getProperties().get(Constans.SERVER_SSH_HISTORICO_PASSW).getValue(),
//                    RoutineProperties.getProperties().get(Constans.SERVER_SSH_HISTORICO_PATH).getValue(),
//                    equipmentsCase, Constans.TXT_NOMBRE_HISTORICO + Constans.EXTENSION_TXT);
//        } catch (Exception ex) {
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//        }
//        ClaroLogger.infoProgrammerLog("Finaliza proceso de executeCaseOneOverTemp");
//    }
//
    /**
     *
     * Ejecuta el caso 2 de sobrecalentamiento. Recopila los equipos del caso 2
     * y los organiza por nombre del sitio. Calcula el promedio de temperatura
     * para cada grupo de equipos del mismo sitio. Asigna el número de incidente
     * y otras variables a los equipos según corresponda.
     *
     * @throws Exception si ocurre algún error durante la ejecución del caso 2
     * de sobrecalentamiento.
     */
//    void executeCasesTwoOverTemp() throws Exception {
//        try {
//            ClaroLogger.infoProgrammerLog("Inicia proceso de executeCasesTwoOverTemp");
//            equipmentMap = new HashMap<>();
//            // Organizar equipos por nombre del sitio
//            ClaroLogger.infoProgrammerLog("Inicia proceso de Organizar equipos por nombre del sitio");
//            for (EquipmentHwEnvironmentDto equipment : equipmentsCase) {
//                if (equipment.getCaseType().equalsIgnoreCase(Constans.CASE_2)) {
//                    if (isValidEquipment(equipment)) {
//                        List<EquipmentHwEnvironmentDto> equipmentList = equipmentMap.computeIfAbsent(equipment.getSiteName(), k -> new ArrayList<>());
//                        equipmentList.add(equipment);
//                        equipmentMap.put(equipment.getSiteName(), equipmentList);
//                    }
//                }
//            }
//            // Procesar cada grupo de equipos del mismo sitio
//            ClaroLogger.infoProgrammerLog("Inicia proceso de Procesar cada grupo de equipos del mismo sitio");
//            for (Map.Entry<String, List<EquipmentHwEnvironmentDto>> entry : equipmentMap.entrySet()) {
//                if (!entry.getValue().isEmpty()) {
//                    String regional = entry.getValue().get(0).getRegional();
//                    // Verificar si la regional es vÃ¡lida
//                    if (isRegionalValid(regional)) {
////                         Asignar nÃºmero de incidente si se encuentra un ticket vÃ¡lido
//                        if (findTicketIdBySiteName(entry.getKey()) != null) {
//                            setIncidentNumberForEquipmentList(entry.getValue(), findTicketIdBySiteName(entry.getKey()).get("INCIDENTE"));
//                            setWorkOrderNumberForEquipmentList(entry.getValue(), findTicketIdBySiteName(entry.getKey()).get("OT"));
//                            setTaskNumberForEquipmentList(entry.getValue(), findTicketIdBySiteName(entry.getKey()).get("TASK"));
//                            ClaroLogger.infoProgrammerLog("Se encuentra el siguiente incidente para este equipo: " + entry.getValue().get(0).getSiteName() + " INCIDENTE: " + findTicketIdBySiteName(entry.getKey()).get("INCIDENTE")
//                                    + " OT: " + findTicketIdBySiteName(entry.getKey()).get("OT")
//                                    + " TAS: " + findTicketIdBySiteName(entry.getKey()).get("TASK"));
//                        }
//                    }
//                    // Calcular promedio de temperatura para el grupo de equipos
//                    setAverageTemperatureForEquipmentList(entry.getValue(), calculateAverageTemperature(entry.getValue()));
//
//                    // Crear incidente en Maximo si no se ha asignado un nÃºmero de incidente previamente
//                    if ((entry.getValue().get(0).getIncidentNumber() == null || entry.getValue().get(0).getIncidentNumber().isEmpty())) {
//                        Map<String, String> maximo = createIncidentMaximo(RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_URGENCY).getValue(),
//                                RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_IMPACT).getValue(),
//                                RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_INTERNALPRIORITY).getValue(),
//                                RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_OWNERGROUP).getValue(),
//                                RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_CLASSSTRUCTUREID).getValue(),
//                                RoutineProperties.getProperties().get(Constans.MAXIMO_CREATE_INCIDENT_DESCRIPTION).getValue().replace(Constans.MAXIMO_EQUIPMENT, entry.getKey()).replace(Constans.MAXIMO_TEMPERATURE, entry.getValue().get(0).getAverageTemperature()).replace(Constans.MAXIMO_REGIONAL, regional),
//                                construirStringConSaltosDeLinea(entry.getValue()),
//                                entry.getValue().get(0).getCinum(),
//                                entry.getValue().get(0).getCiLocation());
//                        // Asignar número de incidente, OT y TAS a cada equipo
//                        for (EquipmentHwEnvironmentDto equipment : entry.getValue()) {
//                            equipment.setIncidentNumber(maximo.get(Constans.MAXIMO_ELEMENT_INCIDENT));
//                            equipment.setOtNumber(maximo.get(Constans.MAXIMO_ELEMENT_WORK_ORDER));
//                            equipment.setTasNumber(maximo.get(Constans.MAXIMO_ELEMENT_TASK));
//                        }
//                        ClaroLogger.infoProgrammerLog("Finaliza proceso de en maximo incidente: " + maximo.get(Constans.MAXIMO_ELEMENT_INCIDENT) + " OT: " + maximo.get(Constans.MAXIMO_ELEMENT_WORK_ORDER) + " TAS: " + maximo.get(Constans.MAXIMO_ELEMENT_TASK));
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//        }
//        ClaroLogger.infoProgrammerLog("Finaliza proceso de executeCasesTwoOverTemp");
//    }
//
    /**
     *
     * Verifica si una regional es válida.
     *
     * @param regional la regional a verificar.
     * @return true si la regional no es nula ni está vacía, false de lo
     * contrario.
     */
//    private boolean isRegionalValid(String regional) {
//        return regional != null && !regional.trim().isEmpty();
//    }
//
//    /**
//     *
//     * Calcula la temperatura promedio de una lista de equipos.
//     *
//     * @param equipmentList la lista de equipos.
//     * @return la temperatura promedio como una cadena de texto.
//     */
//    private String calculateAverageTemperature(List<EquipmentHwEnvironmentDto> equipmentList) {
//        int sum = 0;
//        int count = 0;
//        for (EquipmentHwEnvironmentDto dto : equipmentList) {
//            sum += dto.getTemperature();
//            count++;
//        }
//        return String.valueOf(count != 0 ? sum / count : 0);
//    }
//
    /**
     *
     * Asigna el nÃºmero de incidente a una lista de equipos.
     *
     * @param equipmentList la lista de equipos.
     * @param ticket el nÃºmero de incidente a asignar.
     */
//    private void setIncidentNumberForEquipmentList(List<EquipmentHwEnvironmentDto> equipmentList, String ticket) {
//        for (EquipmentHwEnvironmentDto dto : equipmentList) {
//            dto.setIncidentNumber(ticket);
//        }
//    }
//
    /**
     *
     * Asigna el nÃºmero de OT a una lista de equipos.
     *
     * @param equipmentList la lista de equipos.
     * @param ot el nÃºmero de incidente a asignar.
     */
//    private void setWorkOrderNumberForEquipmentList(List<EquipmentHwEnvironmentDto> equipmentList, String ot) {
//        for (EquipmentHwEnvironmentDto dto : equipmentList) {
//            dto.setOtNumber(ot);
//        }
//    }
//
//    /**
//     *
//     * Asigna el nÃºmero de task a una lista de equipos.
//     *
//     * @param equipmentList la lista de equipos.
//     * @param task el nÃºmero de incidente a asignar.
//     */
//    private void setTaskNumberForEquipmentList(List<EquipmentHwEnvironmentDto> equipmentList, String task) {
//        for (EquipmentHwEnvironmentDto dto : equipmentList) {
//            dto.setTasNumber(task);
//        }
//    }
//
//    /**
//     *
//     * Asigna la temperatura promedio a una lista de equipos.
//     *
//     * @param equipmentList la lista de equipos.
//     * @param averageTemp la temperatura promedio a asignar.
//     */
//    private void setAverageTemperatureForEquipmentList(List<EquipmentHwEnvironmentDto> equipmentList, String averageTemp) {
//        for (EquipmentHwEnvironmentDto dto : equipmentList) {
//            dto.setAverageTemperature(averageTemp);
//        }
//    }
//
    /**
     *
     * Construye una cadena de texto con saltos de lÃ­nea que contiene
     * informaciÃ³n de una lista de DTO de equipos.
     *
     * @param listaDTO la lista de DTO de equipos.
     * @return la cadena de texto construida.
     */
//    public static String construirStringConSaltosDeLinea(List<EquipmentHwEnvironmentDto> listaDTO) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (EquipmentHwEnvironmentDto dto : listaDTO) {
//            stringBuilder.append(dto.getContainingParentName()).append(" ")
//                    .append("Temperature: ").append(dto.getTemperature()).append(" ")
//                    .append("Threshold: ").append(dto.getTemperatureThreshold()).append(System.lineSeparator());
//        }
//        return stringBuilder.toString();
//    }
//
//    /**
//     *
//     * Verifica si el equipo es vÃ¡lido.
//     *
//     * @param equipment el DTO de equipo.
//     * @return true si el equipo es vÃ¡lido, false de lo contrario.
//     */
//    private boolean isValidEquipment(EquipmentHwEnvironmentDto equipment) {
//        if ((equipment.getRegional() != null && !equipment.getRegional().trim().isEmpty()) && (equipment.getIncidentNumber() == null || equipment.getIncidentNumber().trim().isEmpty())) {
//            return equipment.getTemperature() > 0 && equipment.getTemperatureThreshold() > 0;
//        }
//        return false;
//    }
//
    /**
     *
     * Busca y asigna la informaciÃ³n regional a los equipos segÃºn el nombre
     * del sitio.
     *
     * @param list la lista de equipos a procesar.
     * @return la lista de equipos con la informaciÃ³n regional asignada.
     * @throws Exception si ocurre algÃºn error durante la ejecuciÃ³n.
     */
//    private List<EquipmentHwEnvironmentDto> findRegionalBySiteName(List<EquipmentHwEnvironmentDto> list) throws Exception {
//        try {
//            DataBaseClientWrapper dataBase = RoutineProperties.dbMaximo();
//            dataBase.connect();
//
//            StringBuilder sb = new StringBuilder();
//            for (EquipmentHwEnvironmentDto dto : list) {
//                String siteName = dto.getSiteName();
//                sb.append(Constans.SINGLE_QUOTE).append(siteName).append(Constans.SINGLE_QUOTE_AND_COMMA);
//            }
//
//            // Eliminar la Ãºltima coma si existe
//            if (sb.length() > 0) {
//                sb.setLength(sb.length() - 1);
//            }
//
//            ResultSet resultSet = dataBase.executeQuery(RoutineProperties.getProperties().get(Constans.QUERY_FIND_REGIONAL_BY_SITENAME).getValue().replace(Constans.QUERY_ELEMENT, sb.toString()));
//            while (resultSet.next()) {
//                for (EquipmentHwEnvironmentDto dto : list) {
//                    if (dto.getSiteName().equalsIgnoreCase(resultSet.getString(Constans.CINAME))) {
//                        dto.setRegional(resultSet.getString(Constans.REGIONAL));
//                        dto.setCiLocation(resultSet.getString(Constans.CILOCATION));
//                        dto.setCinum(resultSet.getString(Constans.CINUM));
//                    }
//                }
//            }
//            dataBase.closeConnDB();
//            return list;
//        } catch (Exception ex) {
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//            return null;
//        }
//    }
//
//    /**
//     *
//     * Busca y devuelve el ID del ticket asociado al nombre del sitio.
//     *
//     * @param siteName el nombre del sitio.
//     * @return el ID del ticket, o null si no se encuentra.
//     * @throws Exception si ocurre algÃºn error durante la ejecuciÃ³n.
//     */
//    private Map<String, String> findTicketIdBySiteName(String siteName) throws Exception {
//        DataBaseClientWrapper dataBase = RoutineProperties.dbMaximo();
//        dataBase.connect();
//        try {
//            ResultSet resultSet = dataBase.executeQuery(RoutineProperties.getProperties().get(Constans.QUERY_FIND_TICKET_ID_BY_SITENAME).getValue().replace(Constans.QUERY_ELEMENT, siteName));
//            Map<String, String> result = new HashMap<>();
//            while (resultSet.next()) {
//                result.put("INCIDENTE", resultSet.getString("INCIDENTE"));
//                result.put("OT", resultSet.getString("OT"));
//                result.put("TASK", resultSet.getString("TASK"));
//                return result;
//            }
//        } catch (Exception ex) {
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//        } finally {
//            dataBase.closeConnDB();
//        }
//        return null;
//    }
//
    /**
     *
     * Crea un incidente en el sistema Maximo y devuelve un mapa con los
     * elementos creados (incidente, orden de trabajo y tarea).
     *
     * @param urgency la urgencia del incidente.
     * @param impact el impacto del incidente.
     * @param internalpriority la prioridad interna del incidente.
     * @param ownergroup el grupo propietario del incidente.
     * @param classstructureid el ID de la estructura de clase del incidente.
     * @param description la descripciÃ³n del incidente.
     * @param longDescription la descripciÃ³n larga del incidente.
     * @param cinum el nÃºmero de CI.
     * @param location la ubicaciÃ³n del incidente.
     * @return un mapa con los elementos creados (incidente, orden de trabajo y
     * tarea).
     * @throws Exception si ocurre algÃºn error durante la ejecuciÃ³n.
     */
//    private Map<String, String> createIncidentMaximo(String urgency, String impact, String internalpriority, String ownergroup, String classstructureid, String description, String longDescription, String cinum, String location) throws Exception {
//        try {
//            ClaroLogger.infoProgrammerLog("Inicia proceso de createIncidentMaximo");
//            ConsumoGenericoSoap consumoGenericoSoap = new ConsumoGenericoSoap();
//            Integer timeOut = Constans.SECONDS_TIMEOUT_SOAP_DEFAULT;
//            Map<String, String> maximoMap = new HashMap<>();
//
//            String request;
//            // Crear incidente Maximo
//            request = String.format(ERequestSOAP.CREATE_INCIDENT_MAXIMO.request(),
//                    urgency, impact, internalpriority, ownergroup, classstructureid, description, description + ", " + longDescription, cinum);
//            maximoMap.put(Constans.MAXIMO_ELEMENT_INCIDENT, consumoGenericoSoap.consumirServicioMaximoSOAP(Constans.MAXIMO_NAME_WS_CREATE_INCIDENT,
//                    RoutineProperties.getProperties().get(Constans.MAXIMO_URL_CREATE_INCIDENT).getValue(),
//                    request, Constans.MAXIMO_METHOD_WS_CREATE_INCIDENT, timeOut, RoutineProperties.getProperties().get(Constans.MAXIMO_USER).getValue(), RoutineProperties.getProperties().get(Constans.MAXIMO_PASSWORD).getValue()));
//            // Crear orden de trabajo Maximo
//            request = String.format(ERequestSOAP.CREATE_OT_MAXIMO.request(), internalpriority, classstructureid, description, cinum);
//            maximoMap.put(Constans.MAXIMO_ELEMENT_WORK_ORDER, consumoGenericoSoap.consumirServicioMaximoSOAP(Constans.MAXIMO_NAME_WS_CREATE_OT,
//                    RoutineProperties.getProperties().get(Constans.MAXIMO_URL_CREATE_OT).getValue(),
//                    request, Constans.MAXIMO_METHOD_WS_CREATE_OT, timeOut, RoutineProperties.getProperties().get(Constans.MAXIMO_USER).getValue(), RoutineProperties.getProperties().get(Constans.MAXIMO_PASSWORD).getValue()));
//            // Asociar orden de trabajo al incidente Maximo
//            request = String.format(ERequestSOAP.ASSOCIATE_OT_MAXIMO.request(), maximoMap.get(Constans.MAXIMO_ELEMENT_INCIDENT), maximoMap.get(Constans.MAXIMO_ELEMENT_WORK_ORDER), description);
//            consumoGenericoSoap.consumirServicioMaximoSOAP(Constans.MAXIMO_NAME_WS_ASSOCIATE_OT,
//                    RoutineProperties.getProperties().get(Constans.MAXIMO_URL_ASSOCIATE_OT).getValue(),
//                    request, Constans.MAXIMO_METHOD_WS_ASSOCIATE_OT, timeOut, RoutineProperties.getProperties().get(Constans.MAXIMO_USER).getValue(), RoutineProperties.getProperties().get(Constans.MAXIMO_PASSWORD).getValue());
//            // Crear tarea Maximo
//            // request = String.format(ERequestSOAP.CREATE_TASK_MAXIMO.request(), location, description, internalpriority, maximoMap.get(Constans.MAXIMO_ELEMENT_INCIDENT), classstructureid, cinum);
//            maximoMap.put(Constans.MAXIMO_ELEMENT_TASK, /*consumoGenericoSoap.consumirServicioMaximoSOAP(Constans.MAXIMO_NAME_WS_CREATE_TASK,
//                    RoutineProperties.getProperties().get(Constans.MAXIMO_URL_CREATE_TASK).getValue(),
//                    request, Constans.MAXIMO_METHOD_WS_CREATE_TASK, timeOut, RoutineProperties.getProperties().get(Constans.MAXIMO_USER).getValue(), RoutineProperties.getProperties().get(Constans.MAXIMO_PASSWORD).getValue())*/"");
//
//            ClaroLogger.infoProgrammerLog("Finaliza proceso de createIncidentMaximo");
//            return maximoMap;
//        } catch (Exception ex) {
//            // Lanzar una excepciÃ³n general con informaciÃ³n adicional
//            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
//            StackTraceElement[] stackTrace = ex.getStackTrace();
//            for (StackTraceElement element : stackTrace) {
//                ClaroLogger.errorProgrammerLog("Class: " + element.getClassName());
//                ClaroLogger.errorProgrammerLog("Method: " + element.getMethodName());
//                ClaroLogger.errorProgrammerLog("Line: " + element.getLineNumber());
//            };
//            return null;
//        }
//    }
}
