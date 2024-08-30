package com.dev.superapp.config.http;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/***
 *    ~~   The ClientConnectionPoolManager maintains a pool of ManagedHttpClientConnections and is able to service
 * connection requests from multiple execution threads.
 * The default size of the pool of concurrent connections that can be open by the manager is five for each route
 * or target host and 25 for total open connections
 *
 *    ~~   Pool is used because creating a new HTTP connection and closing is very expensive hence already opened connection threads
 * are used to avoid expensive operations
 */
@Configuration
public class PooledRestTemplate{



    @Bean("scalableRestTemplate")
    public RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create() //set SSL Socket Factory settings
                        .setSslContext(SSLContexts.createSystemDefault())
                        .setTlsVersions(TLS.V_1_3)
                        .build())
                .setDefaultSocketConfig(SocketConfig.custom()                    // set SocketConfig
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)          // pool concurrency policy
                .setConnPoolPolicy(PoolReusePolicy.LIFO)                         // connection pool thread reuse policy
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setSocketTimeout(Timeout.ofSeconds(5))        // Defines max time limit which can be there without any incoming data
                        .setConnectTimeout(Timeout.ofSeconds(5))      // Defines max time limit to wait from start of initiating TCP connection request till there is no successful connection made
                        .setTimeToLive(TimeValue.ofSeconds(10))       // Defines the total span of time connections can be kept alive or execute requests
                        .build())
                .setMaxConnPerRoute(100)                           // Set the total number of concurrent connections to a specific route, which is two by default
                .setMaxConnTotal(100)// Set the maximum number of total open connections
                .build();

        HttpClient client = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(StandardCookieSpec.STRICT)
                        .build())
                .build();

        return new RestTemplateBuilder()
               //  .rootUri("http://service-b-base-url:8080/")
              //    .setConnectTimeout(Duration.ofMillis(5000))
              //  .setReadTimeout(Duration.ofMillis(20000))
                .messageConverters(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter())
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

}


