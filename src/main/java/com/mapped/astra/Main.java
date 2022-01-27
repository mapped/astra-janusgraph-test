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
    public void startApplication() throws InterruptedException {

        LOGGER.info("Application is started");

        LOGGER.info("Start removing data.");

        long start = System.currentTimeMillis();

        GraphTraversalSource graphTraversalSource = janusGraph.traversal();

        int verticesRemoveIteration=0;
        while (graphTraversalSource.V().has("graphId", "ASTRA_TEST").barrier(1).limit(1).hasNext()){
            LOGGER.info("Found some vertices");
            graphTraversalSource.V().has("graphId","ASTRA_TEST").barrier(2000).limit(2000).drop().iterate();
            LOGGER.info("Start removing vertices. Iteration: "+verticesRemoveIteration);
            graphTraversalSource.tx().commit();
            LOGGER.info("Removed some vertices. Iteration: "+verticesRemoveIteration);
        }

        graphTraversalSource.tx().commit();

        LOGGER.info("Time took to remove data: "+(System.currentTimeMillis()-start));

    }

}
