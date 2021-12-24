1) Compile project: `./gradlew clean build`.
2) Start project with JanusGraph configuration:
```
java -Djanusgraph.config.path=/path/to/janusgraph/config -jar build/libs/mapped-astradb-test.jar
```

----

Currently testing the next JanusGraph configuration:
```
storage.backend: cql
storage.hostname: ***secret***
storage.port: 29042
storage.cql.keyspace: janusgraph
storage.username: token
storage.password: ***secret***
storage.cql.local-datacenter: westus2
storage.cql.ssl.enabled: true
storage.cql.ssl.truststore.location: /path/to/trustStore.jks
storage.cql.ssl.truststore.password: ***secret***
storage.cql.ssl.keystore.location: /path/to/identity.jks
storage.cql.ssl.keystore.keypassword: ***secret***
storage.cql.ssl.keystore.storepassword: ***secret***
storage.cql.ssl.client-authentication-enabled: true
gremlin.graph: org.janusgraph.core.JanusGraphFactory
query.batch: true
query.fast-property: false
query.smart-limit: false
storage.batch-loading: false
storage.buffer-size: 10240
storage.cql.batch-statement-size: 200
storage.lock.wait-time: 5000
schema.default: none
storage.connection-timeout: 60000
storage.read-time: 60000
storage.setup-wait: 120000
tx.max-commit-time: 60000
ids.block-size: 1000000
ids.authority.wait-time: 1200
storage.cql.read-consistency-level: ONE
storage.cql.write-consistency-level: ALL
storage.cql.local-max-connections-per-host: 10
storage.cql.executor-service.enabled: false
storage.cql.max-requests-per-connection: 500
storage.cql.request-timeout: 20000
storage.parallel-backend-executor-service.max-shutdown-wait-time: 1000
storage.meta.cql.visibility: false
```