package com.springinpractice.ch11.service;

import java.util.List;
import com.springinpractice.ch11.model.Module;
import com.springinpractice.ch11.model.Package;
import com.springinpractice.ch11.repository.CIService;

public interface PackageService extends CIService<Package> {
    
    List<Package> findByModule(Module module);
    
    Package findByModuleAndVersion(Module module, String version);
}