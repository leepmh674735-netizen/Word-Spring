package com.springinpractice.ch11.service.impl;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springinpractice.ch11.model.Module;
import com.springinpractice.ch11.model.Package;
import com.springinpractice.ch11.repository.PackageRepository;
import com.springinpractice.ch11.service.PackageService;
import com.springinpractice.ch11.exception.DuplicateCIException;

import jakarta.inject.Inject;

@Service
@Transactional
public class PackageServiceImpl extends AbstractCiService<Package> implements PackageService {
    
    @Inject 
    private PackageRepository packageRepo;
    
    @Override
    protected Neo4jRepository<Package, Long> getRepository() {
        return packageRepo;
    }
    
    @Override
    protected void checkForDuplicate(Package pkg) {
        Package duplicate = packageRepo.findByModuleAndVersion(pkg.getModule(), pkg.getVersion());
        if (duplicate != null) {
            throw new DuplicateCIException();
        }
    }
    
    @Override
    public List<Package> findByModule(Module module) {
        return packageRepo.findByModule(module);
    }
    
    @Override
    public Package findByModuleAndVersion(Module module, String version) {
        return packageRepo.findByModuleAndVersion(module, version);
    }
}