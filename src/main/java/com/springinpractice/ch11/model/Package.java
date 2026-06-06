package com.springinpractice.ch11.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Package")
public class Package extends AbstractCI<Package> {
    
    @Relationship(type = "FROM_MODULE", direction = Relationship.Direction.OUTGOING)
    private Module module;
    
    @Property("version")
    private String version;
    
    @NotNull
    public Module getModule() { 
        return module; 
    }
    
    public void setModule(Module module) { 
        this.module = module; 
    }
    
    @NotNull
    @Size(min = 1, max = 80)
    public String getVersion() { 
        return version; 
    }
    
    public void setVersion(String version) { 
        this.version = version; 
    }
    
    public String getDisplayName() {
        return "Package";
    }

    @Override
    public String toString() {
        return "Package [id=" + getId() + ", version=" + version + ", module=" + (module != null ? module.getName() : "null") + "]";
    }
}