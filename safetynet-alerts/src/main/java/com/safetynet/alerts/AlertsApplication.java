package com.safetynet.alerts;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.model.SafetyData;
import com.safetynet.alerts.utils.DataSafety;
@Component
@SpringBootApplication
public class AlertsApplication {

	private static final Logger logger = LogManager.getLogger(AlertsApplication.class);

    @Value("${info.data}")
    private String dataFileJSON;


    public static void main(final String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    /**
     * Read JSON Data
     *
     * @return SafetyData data
     * @throws IOException exception
     * @throws NullPointerException 
     */
    @Bean
    public SafetyData jsonFileLoader() throws IOException, NullPointerException {
        logger.debug("Data JSON loaded");
        return DataSafety.readJsonFile(dataFileJSON);
    }


}
