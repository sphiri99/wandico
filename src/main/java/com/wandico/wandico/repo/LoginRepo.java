package com.wandico.wandico.repo;

import com.wandico.wandico.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<UserDetails, Long> {

    UserDetails findByUsername(String username);

}
