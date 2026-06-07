package com.springinpractice.ch11.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.springframework.web.client.RestTemplate;
import com.springinpractice.ch11.client.ZkybaseClient;

/**
 * @goal package
 */
public class PackageMojo extends AbstractMojo {
    
    /**
     * @parameter expression="${zkybaseUrl}"
     */
    private String zkybaseUrl;
    
    /**
     * @parameter expression="${package.module}"
     */
    private String module;
    
    /**
     * @parameter expression="${package.version}"
     */
    private String version;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        ZkybaseClient client = new ZkybaseClient(new RestTemplate(), zkybaseUrl);
        client.createPackage(new Package(module, version));
    }
}