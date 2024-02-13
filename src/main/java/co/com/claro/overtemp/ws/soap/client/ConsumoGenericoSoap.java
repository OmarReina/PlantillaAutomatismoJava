package co.com.claro.overtemp.ws.soap.client;

import co.com.claro.overtemp.constans.Constans;
import co.com.claro.overtemp.util.Util;
import com.eviware.soapui.impl.support.definition.support.InvalidDefinitionException;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import java.net.SocketTimeoutException;
import org.apache.commons.lang.StringEscapeUtils;
import com.claro.logger.ClaroLogger;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian
 * Betancourt</a>
 */
public class ConsumoGenericoSoap {

    private static final String MSJ_ERROR = "No se encontro metodo SOAP";

    /**
     *
     * Consume un servicio SOAP de Maximo.
     *
     * @param nameWS El nombre del servicio SOAP.
     * @param wsdl La URL de WSDL para el servicio SOAP.
     * @param request El mensaje de solicitud SOAP.
     * @param metodo El método a invocar en el servicio SOAP.
     * @param timeOut El valor de tiempo de espera para la solicitud SOAP en
     * segundos.
     * @param username de usuario El nombre de usuario para la autenticación
     * (opcional).
     * @param password La contraseña para la autenticación (opcional).
     * @return El mensaje de respuesta SOAP.
     * @throws Exception si se produce algún error durante el proceso de
     * solicitud o respuesta SOAP.
     */
    public String consumirServicioMaximoSOAP(String nameWS, String wsdl, String request,
            String metodo, Integer timeOut, String username, String password) throws Exception {

        long timeIni = System.currentTimeMillis();
        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_INICIO_METODO, new Object() {
        }.getClass().getEnclosingMethod().getName()));
        String response = null;
        Integer readTimeout = timeOut * 1000;
        try {
            try {
                WsdlInterface wsdlInterface = WsdlImporter.importWsdl(new WsdlProject(), wsdl)[0];
                for (Operation operation : wsdlInterface.getOperationList()) {
                    WsdlOperation wsdlOperation = (WsdlOperation) operation;
                    if (wsdlOperation.getName().equals(metodo)) {
                        WsdlRequest wsdlRequest = wsdlOperation.addNewRequest(request);
                        wsdlRequest.setRequestContent(request);
                        wsdlRequest.setTimeout(readTimeout.toString());
                        if (username != null && password != null) {
                            wsdlRequest.setUsername(username);
                            wsdlRequest.setPassword(password);
                        }
                        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_WSDL, Util.toJson(wsdl)));
                        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_NAME_WS, Util.toJson(nameWS)));
                        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_METODO, Util.toJson(metodo)));
                        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TIMEOUT, Util.toJson(timeOut)));
                        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_REQUEST, StringEscapeUtils.unescapeJava(wsdlRequest.getRequestContent().replaceAll("\\n|\\r", ""))));
                        WsdlSubmit submit = (WsdlSubmit) wsdlRequest.submit(new WsdlSubmitContext(wsdlRequest), false);
                        if (submit.getError() != null && submit.getError() instanceof SocketTimeoutException) {
                            throw submit.getError();
                        }
                        response = submit.getResponse().getContentAsString();
                        break;
                    }
                }

                if (response != null) {
                    ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_RESPONSE, StringEscapeUtils.unescapeJava(response).replaceAll("\\n|\\r", "")));
                    if (response == null || !response.isEmpty()) {
                        switch (metodo) {
                            case Constans.MAXIMO_METHOD_WS_CREATE_INCIDENT:
                                response = Util.extractTextBetweenStrings(response, "<TICKETID>", "</TICKETID>");
                                break;
                            case Constans.MAXIMO_METHOD_WS_CREATE_OT:
                                response = Util.extractTextBetweenStrings(response, "<WONUM>", "</WONUM>");
                                break;
                            case Constans.MAXIMO_METHOD_WS_CREATE_TASK:
                                response = Util.extractTextBetweenStrings(response, "<WONUM>", "</WONUM>");
                                break;
                        }
                    }
                } else {
                    ClaroLogger.infoProgrammerLog(MSJ_ERROR);
                }
                wsdlInterface = null;
            } catch (InvalidDefinitionException exWSDL) {
                ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TERMINA_METODO, new Object() {
                }.getClass().getEnclosingMethod().getName()));
                ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TIEMPO_TRANSACCION, (System.currentTimeMillis() - timeIni)));
                ClaroLogger.infoProgrammerLog(exWSDL.getMessage() == null ? exWSDL.getDetailedMessage() : exWSDL.getMessage());
            }
        } catch (Exception ex) {
            String description = null;
            ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TERMINA_METODO, new Object() {
            }.getClass().getEnclosingMethod().getName()));
            ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TIEMPO_TRANSACCION, (System.currentTimeMillis() - timeIni)));
            ClaroLogger.errorProgrammerLog(ex.getMessage(), ex);
        }
        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TERMINA_METODO, new Object() {
        }.getClass().getEnclosingMethod().getName()));
        ClaroLogger.infoProgrammerLog(String.format(Constans.LOG4J_TIEMPO_TRANSACCION, (System.currentTimeMillis() - timeIni)));

        return response;
    }

}
