package com.springinpractice.ch11.web.controller.pkg;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch11.model.Module1;
import com.springinpractice.ch11.model.Package1;
import com.springinpractice.ch11.service.CIService;
import com.springinpractice.ch11.service.ModuleService;
import com.springinpractice.ch11.service.PackageService;
import com.springinpractice.ch11.web.controller.AbstractCrudController;
import com.springinpractice.ch11.exception.DuplicateCIException;

import jakarta.inject.Inject;

@Controller
@RequestMapping("/packages")
public class PackageCrudController1 extends AbstractCrudController<Package1> {
    
    @Inject 
    private ModuleService moduleService;
    
    @Inject 
    private PackageService packageService;
    
    @Value("${app.baseUrl:http://localhost:8080}")
    private String appBaseUrl;
    
    @Override
    protected CIService<Package1> getService() { 
        return packageService; 
    }
    
    @RequestMapping(
         value = "",
         method = RequestMethod.POST,
         consumes = "application/xml")
    public ResponseEntity<Void> postPackage(@RequestBody Package1 pkgData) {
        
        Module1 moduleData = pkgData.getModule();
        if (moduleData != null) {
            Module1 persistentModule = moduleService.findByGroupIdAndModuleId(
                moduleData.getGroupId(), moduleData.getModuleId()
            );
            pkgData.setModule(persistentModule);
        }
        
        try { 
            packageService.create(pkgData);
            URI location = URI.create(appBaseUrl + "/packages/" + pkgData.getId());
            return ResponseEntity.created(location).build();
        } catch (DuplicateCIException e) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }   
}