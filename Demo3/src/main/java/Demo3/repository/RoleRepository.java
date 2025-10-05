package Demo3.repository;

import Demo3.entity.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getUserByName(@Param("name") String name);

    Optional<Role> findByName(String name);
}
