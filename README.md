
# SuperApp
Just making this to incorporate all Spring boot and related infra learnings into this app


# JPA 

**@GeneratedValue(strategy= GenerationType.IDENTITY)**   :  prevents bulk insertion as for every new entry jpa requires to know last created id from dataSource
hence even saveAll doesn't do a bulk insert but is still a bit faster as does all inserts in a single transaction 

**@GeneratedValue(strategy= GenerationType.SEQUENCE)**   :  provides bulk insertion as for every new entry jpa fetches id from its maintained table


# Redis Sentinel config
At least 3 Sentinel with 3 redis nodes are used in prod for safe failovers.

Actually min insync replicas for sentinel config can be configured in sentinel.conf file so that in case sync among min replicas dont happen then data is considered not saved to master



# Micrometer Tracing
provides distributed tracing to debug multi threaded code where traceId distinguishes requests originating from different sources
Replaces Spring Cloud Sleuth from Spring Boot 3 onwards
https://www.baeldung.com/spring-boot-3-observability

https://spring.io/projects/spring-cloud-sleuth

https://docs.micrometer.io/tracing/reference/  -> replacement of spring cloud sleuth with Micrometer Tracing for tracing purpose


# RestTemplate config
**Connection Timeout** :  A connection timeout occurs only upon starting the TCP connection and if connection is not established due to any reason within defined time limit.

---

**Socket Timeout** : A socket timeout is dedicated to monitor the continuous incoming data flow. If the data flow is interrupted for the specified time the connection is considered as stalled/broken. 

By setting socket timeout to 1000 (ms) this would require that every second new data is received (assuming that you read the data block wise and the block is large enough).

If only the incoming stream stalls for more than a second you are running into a socket timeout.

---

**Time to Live** : total Time duration from establishing a connection to serving requests

---

url : https://stackoverflow.com/questions/7360520/connectiontimeout-versus-sockettimeout

for config settings : official guide
https://hc.apache.org/httpcomponents-client-5.3.x/migration-guide/migration-to-classic.html

---

# Setter injection 
should be preferred while constructor injection is cleaner(with @RequiredArgsConstructor)

https://stackoverflow.com/questions/28685182/spring-lombok-can-i-have-autowired-setter 
https://projectlombok.org/features/experimental/onX


---



