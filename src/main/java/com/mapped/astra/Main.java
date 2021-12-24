package com.mapped.astra;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "com.mapped.astra")
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @Autowired
    private JanusGraph janusGraph;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void startApplication(){

        LOGGER.info("Application is started");

        GraphTraversalSource traversalSource = janusGraph.traversal();

        traversalSource.V().hasNext();

        LOGGER.info("The test case is finished");

    }

}
