package com.springinpractice.ch11.repository;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springinpractice.ch11.model.Module;
import com.springinpractice.ch11.model.Package;

@Repository
public interface PackageRepository extends Neo4jRepository<Package, Long> {
    
    @Query("MATCH (p:Package)-[:FROM_MODULE]->(m:Module) " +
           "WHERE id(m) = $#{#module.id} " +
           "RETURN p")
    List<Package> findByModule(@Param("module") Module module);
     
    @Query("MATCH (p:Package)-[:FROM_MODULE]->(m:Module) " +
           "WHERE id(m) = $#{#module.id} AND p.version = $version " +
           "RETURN p")
    Package findByModuleAndVersion(@Param("module") Module module, @Param("version") String version);
}