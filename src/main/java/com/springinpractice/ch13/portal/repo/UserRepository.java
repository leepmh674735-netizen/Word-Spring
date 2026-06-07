package com.springinpractice.ch13.portal.repo;

import java.util.Collection;
import java.util.List;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Long>{
	
	@RestResource(path = "find-by-username")
	User findByUsername(@Param ("username") String username);
	
	@RestResource(path = "find-by-username-in")
	List<User> findByUsernameIn(
	     @Param("username") Collection<String> usernames);

}
