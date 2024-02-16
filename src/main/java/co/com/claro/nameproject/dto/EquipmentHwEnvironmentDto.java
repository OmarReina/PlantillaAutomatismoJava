/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.overtemp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author <a href="mailto:betancourtks@globalhitss.com">Sebastian Betancourt</a>
 */
@Getter
@Setter
public class EquipmentHwEnvironmentDto {

    private String regional;
    private String ciLocation;
    private String siteId;
    private String siteName;
    private String cinum;
    private String containingParentName;
    private int temperature;
    private int temperatureThreshold;
    private String averageTemperature;
    private String caseType;
    private String incidentNumber;
    private String otNumber;
    private String tasNumber;

    public EquipmentHwEnvironmentDto() {
    }

    public EquipmentHwEnvironmentDto(String regional, String ciLocation, String siteId, String siteName, String cinum, String containingParentName, int temperature, int temperatureThreshold, String averageTemperature, String caseType, String incidentNumber, String otNumber, String tasNumber) {
        this.regional = regional;
        this.ciLocation = ciLocation;
        this.siteId = siteId;
        this.siteName = siteName;
        this.cinum = cinum;
        this.containingParentName = containingParentName;
        this.temperature = temperature;
        this.temperatureThreshold = temperatureThreshold;
        this.averageTemperature = averageTemperature;
        this.caseType = caseType;
        this.incidentNumber = incidentNumber;
        this.otNumber = otNumber;
        this.tasNumber = tasNumber;
    }

}
