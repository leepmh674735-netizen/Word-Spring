package com.springinpractice.ch11.model;

import java.util.Objects;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractCI<T extends AbstractCI<T>> implements CI<T>, Comparable<T> {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @XmlAttribute
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCI<?> that = (AbstractCI<?>) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public int compareTo(T o) {
        if (this == o) return 0;
        if (o == null) return 1;
        if (this.id == null && o.getId() == null) return 0;
        if (this.id == null) return -1;
        if (o.getId() == null) return 1;
        return this.id.compareTo(o.getId());
    }
}