package com.mapped.astra;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

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

        long vertexId = System.currentTimeMillis();

        ArrayList<Long> timesCollection = new ArrayList<>();

        for(int k = 0; k<20; k++){
            long start = System.currentTimeMillis();

            for(int j=0; j<10; j++){
                GraphTraversalSource traversalSource = janusGraph.traversal();

                for(int i=0; i<1000; i++){
                    ++vertexId;
                    Vertex vertex1 = traversalSource.addV().property("graphId", "ASTRA_DELETE_TEST")
                            .property("id", "id_"+vertexId)
                            .property("name", "Vertex name "+vertexId).next();
                    Vertex vertex2 = traversalSource.addV().property("graphId", "ASTRA_DELETE_TEST")
                            .property("id", "id_"+vertexId)
                            .property("name", "Vertex name "+vertexId).next();
                    vertex1.addEdge("hasPart", vertex2);
                }

                janusGraph.tx().commit();
            }

            long timeTook = System.currentTimeMillis()-start;

            LOGGER.info("Write took: "+timeTook+" ms");

            timesCollection.add(timeTook);
        }

        long sum = 0;

        for(Long timeTook : timesCollection){
            sum+=timeTook;
        }

        LOGGER.info("Average execution time for a test is: "+(sum/timesCollection.size())+" ms; All tests took: "+sum+" ms.");

        janusGraph.tx().rollback();

        Thread.sleep(10000);

        LOGGER.info("Start removing data.");

        long start = System.currentTimeMillis();

        GraphTraversalSource graphTraversalSource = janusGraph.traversal();

        while (graphTraversalSource.V().has("graphId", "ASTRA_DELETE_TEST").barrier(1).limit(1).hasNext()){
            graphTraversalSource.V().has("graphId","ASTRA_DELETE_TEST").barrier(1000).limit(1000).drop().iterate();
            graphTraversalSource.tx().commit();
        }

        graphTraversalSource.tx().commit();

        LOGGER.info("Time took to remove data: "+(System.currentTimeMillis()-start));

    }

}
