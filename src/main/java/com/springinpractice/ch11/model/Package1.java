package com.springinpractice.ch11.model;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Package")
@XmlRootElement(name = "package")
@XmlType(propOrder = { "module", "version" })
public class Package1 extends AbstractCI<Package1> {
    
    @Relationship(type = "FROM_MODULE", direction = Relationship.Direction.OUTGOING)
    private Module module;
    
    @Property("version")
    private String version;
    
    public Package1() { }
    
    public Package1(Module module, String version) {
        this.module = module;
        this.version = version;
    }
    
    @NotNull
    @XmlElement
    public Module getModule() { 
        return module; 
    }
    
    public void setModule(Module module) { 
        this.module = module; 
    }
    
    @NotNull
    @Size(min = 1, max = 80)
    @XmlElement
    public String getVersion() { 
        return version; 
    }
    
    public void setVersion(String version) { 
        this.version = version; 
    }
    
    @Override
    public String toString() {
        return "Package1 [id=" + getId() + ", version=" + version + ", module=" + (module != null ? module.getName() : "null") + "]";
    }

    @XmlRootElement(name = "packages")
    public static class PackageListWrapper {
        
        private List<Package1> list;
        
        @XmlElement(name = "package")
        public List<Package1> getList() { 
            return list; 
        }
        
        public void setList(List<Package1> list) { 
            this.list = list; 
        }
    }
}