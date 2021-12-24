package com.mapped.astra.configuration;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
public class JanusGraphConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JanusGraphConfiguration.class);

    private static final String JANUSGRAPH_ENV_PATH = "JANUSGRAPH_CONFIG_PATH";
    private static final String JANUSGRAPH_CONFIG_PATH = "janusgraph.config.path";

    @Autowired
    private Environment environment;

    @Bean
    public JanusGraph janusGraph() throws ConfigurationException {
        AbstractEnvironment environment = (AbstractEnvironment) this.environment;
        String janusGraphConfigPath = environment.getProperty(JANUSGRAPH_CONFIG_PATH,
                environment.getProperty(JANUSGRAPH_ENV_PATH, ""));
        if(janusGraphConfigPath.isEmpty()){
            throw new IllegalStateException("JanusGraph configuration isn't provided");
        }

        File janusConfigFile = new File(janusGraphConfigPath);
        if (!janusConfigFile.exists()) {
            throw new IllegalStateException("Could not load JanusGraph config file: " + janusGraphConfigPath);
        }

        LOGGER.info("Loading JanusGraph from {}", janusConfigFile.getAbsolutePath());

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setFile(janusConfigFile));

        JanusGraph janusGraph = JanusGraphFactory.open(builder.getConfiguration());

        LOGGER.info("JanusGraph connection is opened");

        return janusGraph;
    }

}
