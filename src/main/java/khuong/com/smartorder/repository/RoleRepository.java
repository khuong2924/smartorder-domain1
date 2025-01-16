package khuong.com.smartorder.repository;

import khuong.com.smartorder.entity.ERole;
import khuong.com.smartorder.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}