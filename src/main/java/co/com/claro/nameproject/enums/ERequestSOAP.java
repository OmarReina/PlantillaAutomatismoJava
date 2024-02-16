/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.overtemp.enums;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian Betancourt</a>
 */
public enum ERequestSOAP {

    CREATE_INCIDENT_MAXIMO {
        @Override
        public String request() {
            return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                    + "xmlns:max=\"http://www.ibm.com/maximo\"><soapenv:Header/>"
                    + "<soapenv:Body><max:CreateCL_TICKET_R><max:CL_TICKET_RSet><max:INCIDENT>"
                    + "<max:URGENCY>%s</max:URGENCY>"
                    + "<max:IMPACT>%s</max:IMPACT>"
                    + "<max:INTERNALPRIORITY>%s</max:INTERNALPRIORITY>"
                    + "<max:CLASS>INCIDENT</max:CLASS>"
                    + "<max:OWNERGROUP>%s</max:OWNERGROUP>"
                    + "<max:CLASSSTRUCTUREID>%s</max:CLASSSTRUCTUREID>"
                    + "<max:DESCRIPTION>%s</max:DESCRIPTION>"
                    + "<max:DESCRIPTION_LONGDESCRIPTION>%s</max:DESCRIPTION_LONGDESCRIPTION>"
                    + "<max:CINUM>%s</max:CINUM>"
                    + "</max:INCIDENT></max:CL_TICKET_RSet></max:CreateCL_TICKET_R></soapenv:Body></soapenv:Envelope>";
        }

    }, CREATE_OT_MAXIMO {
        @Override
        public String request() {
            return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                    + "xmlns:max=\"http://www.ibm.com/maximo\"><soapenv:Header/>"
                    + "<soapenv:Body><max:CreateCL_CREATE_OT><max:CL_CREATE_OTSet><max:WORKORDER>"
                    + "<max:WOPRIORITY>%s</max:WOPRIORITY>"
                    + "<max:WOCLASS>WORKORDER</max:WOCLASS>"
                    + "<max:CLASSSTRUCTUREID>%s</max:CLASSSTRUCTUREID>"
                    + "<max:DESCRIPTION>%s</max:DESCRIPTION>"
                    + "<max:CINUM>%s</max:CINUM>"
                    + "<max:WFMASSIGNME>true</max:WFMASSIGNME>"
                    + "<max:SITEID>CLAROMOV</max:SITEID>"
                    + "</max:WORKORDER></max:CL_CREATE_OTSet></max:CreateCL_CREATE_OT></soapenv:Body></soapenv:Envelope>";
        }

    }, ASSOCIATE_OT_MAXIMO {
        @Override
        public String request() {
            return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                    + "xmlns:max=\"http://www.ibm.com/maximo\"><soapenv:Header/>"
                    + "<soapenv:Body><max:SyncCL_WOTICKET><max:CL_WOTICKETSet><max:WORKORDER>"
                    + "<max:ORIGRECORDID>%s</max:ORIGRECORDID>"
                    + "<max:WONUM>%s</max:WONUM>"
                    + "<max:DESCRIPTION>%s</max:DESCRIPTION>"
                    + "<max:WORKTYPE>MC</max:WORKTYPE>"
                    + "<max:ORIGRECORDCLASS>INCIDENT</max:ORIGRECORDCLASS>"
                    + "<max:SITEID>CLAROMOV</max:SITEID>"
                    + "</max:WORKORDER></max:CL_WOTICKETSet></max:SyncCL_WOTICKET></soapenv:Body></soapenv:Envelope>";
        }

    }, CREATE_TASK_MAXIMO {
        @Override
        public String request() {
            return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                    + "xmlns:max=\"http://www.ibm.com/maximo\"><soapenv:Header/>"
                    + "<soapenv:Body><max:CreateWOACTIVITY><max:WOACTIVITYSet><max:WOACTIVITY>"
                    + "<max:LOCATION>%s</max:LOCATION>"
                    + "<max:DESCRIPTION>%s</max:DESCRIPTION>"
                    + "<max:WOPRIORITY>%s</max:WOPRIORITY>"
                    + "<max:OWNERGROUP>BACKTXIPRANPLATAFORMA</max:OWNERGROUP>"
                    + "<max:ORIGRECORDID>%s</max:ORIGRECORDID>"
                    + "<max:CLASSSTRUCTUREID>%s</max:CLASSSTRUCTUREID>"
                    + "<max:CINUM>%s</max:CINUM>"
                    + "<max:ORIGRECORDCLASS>INCIDENT</max:ORIGRECORDCLASS>"
                    + "<max:SITEID>CLAROMOV</max:SITEID>"
                    + "</max:WOACTIVITY></max:WOACTIVITYSet></max:CreateWOACTIVITY></soapenv:Body></soapenv:Envelope>";
        }

    };

    public abstract String request();

}
