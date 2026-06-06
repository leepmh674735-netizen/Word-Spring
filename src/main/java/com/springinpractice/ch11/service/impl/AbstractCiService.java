package com.springinpractice.ch11.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.validation.Errors;

import com.springinpractice.ch11.model.AbstractCI;
import com.springinpractice.ch11.service.CIService;
import com.springinpractice.ch11.exception.DuplicateCIException;
import com.springinpractice.ch11.exception.NoSuchCIException;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Transactional
public abstract class AbstractCiService<T extends AbstractCI<T>> implements CIService<T> {
    
    @Inject 
    protected Neo4jTemplate neo4jTemplate;
    
    protected abstract Neo4jRepository<T, Long> getRepository();
    
    @Override
    public void create(T ci) { 
        createAddDate(ci); 
    }
    
    @Override
    public void create(T ci, Errors errors) {
        if (!errors.hasErrors()) {
            try {
                createAddDate(ci);
            } catch (DuplicateCIException e) {
                errors.reject("error.duplicateCI");
            }
        }
    }
    
    private void createAddDate(T ci) {
        checkForDuplicate(ci);
        ci.setDateCreated(new Date());
        getRepository().save(ci);
    }
    
    protected void checkForDuplicate(T ci) { }
    
    @Override
    public List<T> findAll() {
        return StreamSupport.stream(getRepository().findAll().spliterator(), false)
                            .sorted()
                            .collect(Collectors.toList());
    }
    
    @Override
    public T findOne(Long id) {
        return getRepository().findById(id)
                             .orElseThrow(NoSuchCIException::new);
    }
    
    @Override
    public void update(T ci) { 
        updateAddDate(ci); 
    }
    
    @Override
    public void update(T ci, Errors errors) {
        if (!errors.hasErrors()) {
            updateAddDate(ci);
        }
    }
    
    private void updateAddDate(T ci) {
        ci.setDateModified(new Date());
        getRepository().save(ci);
    }
    
    @Override
    public void delete(T ci) { 
        getRepository().delete(ci); 
    }
    
    @Override
    public void delete(Long id) { 
        getRepository().deleteById(id); 
    }
}