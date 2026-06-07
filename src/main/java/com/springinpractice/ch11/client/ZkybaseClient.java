package com.springinpractice.ch11.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class ZkybaseClient {
    private static final Logger log = LoggerFactory.getLogger(ZkybaseClient.class);
    
    private RestTemplate template;
    private String zkybaseUrl;
    
    public ZkybaseClient(RestTemplate template, String zkybaseUrl) {
        this.template = template;
        if (!zkybaseUrl.endsWith("/")) { 
            zkybaseUrl += "/"; 
        }
        this.zkybaseUrl = zkybaseUrl;
    }
    
    public RestTemplate getRestTemplate() { 
        return template; 
    }
    
    public String getZkybaseUrl() { 
        return zkybaseUrl; 
    }
    
    public void createPackage(Package pkg) {
        URI location = template.postForLocation(getPackagesUrl(), pkg);
        log.info("Created package: {}", location);
    }
    
    public List<Package> getPackages() {
        PackageListWrapper wrapper = template.getForObject(
                getPackagesUrl() + "?format=xml",
                PackageListWrapper.class);
        return wrapper != null ? wrapper.getList() : null;
    }
    
    private String getPackagesUrl() { 
        return zkybaseUrl + "packages"; 
    }
    
    public static class PackageListWrapper {
        private List<Package> list;
        public List<Package> getList() { return list; }
    }
}