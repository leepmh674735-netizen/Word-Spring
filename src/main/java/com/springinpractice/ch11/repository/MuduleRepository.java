package com.springinpractice.ch11.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springinpractice.ch11.model.Module;

@Repository
public interface ModuleRepository extends Neo4jRepository<Module, Long> {
    
    @Query("MATCH (module:Module) " +
           "WHERE module.groupId = $groupId AND module.moduleId = $moduleId " +
           "RETURN module")
    Module findByGroupIdAndModuleId(@Param("groupId") String groupId, @Param("moduleId") String moduleId);
}