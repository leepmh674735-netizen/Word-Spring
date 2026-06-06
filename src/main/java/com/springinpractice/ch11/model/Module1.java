package com.springinpractice.ch11.model;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Module")
@XmlRootElement(name = "module")
@XmlType(propOrder = { "name", "shortDescription", "groupId", "moduleId" })
public class Module1 extends AbstractCI<Module1> {
    
    private String name;
    private String shortDescription;
    
    @Property("groupId")
    private String groupId;
    
    @Property("moduleId")
    private String moduleId;
    
    @NotNull
    @Size(max = 80)
    @XmlElement
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    @Size(max = 200)
    @XmlElement
    public String getShortDescription() { 
        return shortDescription; 
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    @XmlElement
    public String getGroupId() { 
        return groupId; 
    }
    
    public void setGroupId(String groupId) { 
        this.groupId = groupId; 
    }
    
    @XmlElement
    public String getModuleId() { 
        return moduleId; 
    }
    
    public void setModuleId(String moduleId) { 
        this.moduleId = moduleId; 
    }
    
    @Override
    public String toString() {
        return "Module1 [id=" + getId() + ", groupId=" + groupId + ", moduleId=" + moduleId + ", name=" + name + "]";
    }

    @XmlRootElement(name = "modules")
    public static class ModuleListWrapper {
        
        private List<Module1> list;
        
        @XmlElement(name = "module")
        public List<Module1> getList() { 
            return list; 
        }
        
        public void setList(List<Module1> list) { 
            this.list = list; 
        }
    }
}