package com.springinpractice.ch11.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Node("Module")
public class Module extends AbstractCI<Module> {
    
    private String name;
    private String shortDescription;
    
    @Property("groupId")
    private String groupId;
    
    @Property("moduleId")
    private String moduleId;
    
    @NotNull
    @Size(max = 80)
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    @Size(max = 200)
    public String getShortDescription() { 
        return shortDescription; 
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    public String getGroupId() { 
        return groupId; 
    }
    
    public void setGroupId(String groupId) { 
        this.groupId = groupId; 
    }
    
    public String getModuleId() { 
        return moduleId; 
    }
    
    public void setModuleId(String moduleId) { 
        this.moduleId = moduleId; 
    }
    
    public String getDisplayName() { 
        return name; 
    }

    @Override
    public String toString() {
        return "Module [id=" + getId() + ", groupId=" + groupId + ", moduleId=" + moduleId + ", name=" + name + "]";
    }
}