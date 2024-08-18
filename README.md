# SuperApp
Just making this to incorporate all Spring boot and related infra learnings into this app


# Redis Sentinel config
At least 3 Sentinel with 3 redis nodes are used in prod for safe failovers.

Actually min insync replicas for sentinel config can be configured in sentinel.conf file so that in case sync among min replicas dont happen then data is considered not saved to master


# Micrometer Tracing
provides distributed tracing to debug multi threaded code where traceId distinguishes requests originating from different sources
Replaces Spring Cloud Sleuth from Spring Boot 3 onwards
https://www.baeldung.com/spring-boot-3-observability

https://spring.io/projects/spring-cloud-sleuth

https://docs.micrometer.io/tracing/reference/  -> replacement of spring cloud sleuth with Micrometer Tracing for tracing purpose





