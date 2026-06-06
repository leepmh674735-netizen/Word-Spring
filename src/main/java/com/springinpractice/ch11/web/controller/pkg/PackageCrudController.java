package com.springinpractice.ch11.web.controller.pkg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springinpractice.ch11.model.Package1;
import com.springinpractice.ch11.repository.CIService;
import com.springinpractice.ch11.service.PackageService;
import com.springinpractice.ch11.web.controller.AbstractCrudController;

import jakarta.inject.Inject;

@Controller
@RequestMapping("/packages")
public class PackageCrudController extends AbstractCrudController<Package1> {
    
    @Inject 
    private PackageService packageService;
    
    @Override
    protected CIService<Package1> getService() { 
        return packageService; 
    }
}