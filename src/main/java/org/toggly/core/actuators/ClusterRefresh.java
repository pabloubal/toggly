package org.toggly.core.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An actuator for refreshing configuration over a clustered application (POST {serviceurl}/manage/refresh)
 *
 * Overcomes the difficulty of performing a configuration refresh on each node of a clustered application

 * It relies on DiscoveryClient in order to determine target URIs to refresh
 *
 * Configuration:
 *  toggly.endpoints.clustered-refresh.enabled: # true/false - defaults to false
 *  toggly.endpoints.clustered-refresh.serviceName: # Name of the service to discover. Should include mgmt suffix if needed}
 *  toggly.endpoints.clustered-refresh.refreshUrl: # Refresh URL - defaults to /manage/refresh
 *
 * @author Pablo Ubal - pablo.ubal@gmail.com
 */

@Component
@ConditionalOnProperty(name="toggly.endpoints.clustered-refresh.enabled", matchIfMissing = false)
public class ClusterRefresh extends AbstractEndpoint<List<String>> {
    private final static String path = "cluster_refresh";

    @Value("${toggly.endpoints.clustered-refresh.serviceName}")
    private String serviceName;

    @Value("${toggly.endpoints.clustered-refresh.refreshUrl:/manage/refresh}")
    private final String refreshURL = "/manage/refresh";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    @Qualifier("refreshEndpoint")
    private RefreshEndpoint refreshEndpoint;


    public ClusterRefresh() {
        super(path, false);
    }

    @Override
    public List<String> invoke() {
        if(Objects.isNull(serviceName)){
            return null;
        }

        RestTemplate restTemplate;
        List<String> refreshedEndpoints = new ArrayList<>();

        restTemplate = new RestTemplate();

        //For each discovered servicename-management entry: call its refresh actuator.
        List<ServiceInstance> instances = discoveryClient.getInstances(this.serviceName);

        if(instances.size() > 0) {
            List<String> finalRefreshedEndpoints = refreshedEndpoints;
            instances.forEach(i -> {
                String URL = String.format("http://%s:%d%s", i.getHost(), i.getPort(), this.refreshURL);
                ResponseEntity<List> s = restTemplate.exchange(URL, HttpMethod.POST, null, List.class);

                if (s.getStatusCode() == HttpStatus.OK) {
                    finalRefreshedEndpoints.add(i.getHost());
                }
            });
        }
        else {
            //if no clustered instances retrieved, then refresh local endpoint.
            this.refreshEndpoint.refresh();
            refreshedEndpoints = Arrays.asList("Cluster not found. Refreshed localhost only");
        }

        return refreshedEndpoints;
    }
}
