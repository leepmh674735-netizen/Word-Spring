package com.springinpractice.ch11.web.controller;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springinpractice.ch11.model.AbstractCI;
import com.springinpractice.ch11.service.CIService;

public abstract class AbstractCrudController<T extends AbstractCI<T>> {
    
    private final Class<T> ciClass;
    
    @SuppressWarnings("unchecked")
    public AbstractCrudController() {
        ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
        this.ciClass = (Class<T>) paramType.getActualTypeArguments()[0];
    }
    
    protected abstract CIService<T> getService();
    
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            params = "format=json",
            produces = "application/json")
    @ResponseBody
    public List<T> getListAsJson() { 
        return getSortedList();
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            params = "format=xml",
            produces = "application/xml")
    @ResponseBody
    public Object getListAsXml() throws Exception {
        String wrapperClassName = ciClass.getName() + "$" + ciClass.getSimpleName() + "ListWrapper";
        Class<?> wrapperClass = Class.forName(wrapperClassName);
        Object wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        
        wrapperClass.getMethod("setList", List.class).invoke(wrapper, getSortedList());
        return wrapper;
    }
    
    private List<T> getSortedList() { 
        return getService().findAll(); 
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            params = "format=json",
            produces = "application/json")
    @ResponseBody
    public T getDetailsAsJson(@PathVariable("id") Long id) {
        return getDetails(id);
    }
    
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            params = "format=xml",
            produces = "application/xml")
    @ResponseBody
    public T getDetailsAsXml(@PathVariable("id") Long id) {
        return getDetails(id);
    } 
    
    private T getDetails(Long id) { 
        return getService().findOne(id); 
    }
}