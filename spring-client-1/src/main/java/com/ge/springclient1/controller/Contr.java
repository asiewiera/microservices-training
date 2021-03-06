package com.ge.springclient1.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties(prefix = "info")
@RestController
public class Contr {

    private String instanceId;
    private String applicationName;
    private String valuesFromConfig;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getValuesFromConfig() {
        return valuesFromConfig;
    }

    public void setValuesFromConfig(String valuesFromConfig) {
        this.valuesFromConfig = valuesFromConfig;
    }

    @GetMapping("/info")
    public String getInfo() {

        return "App name: " + applicationName + " , instance ID: " + instanceId + System.lineSeparator() +
                "Test values: " + valuesFromConfig + System.lineSeparator();
    }


}
