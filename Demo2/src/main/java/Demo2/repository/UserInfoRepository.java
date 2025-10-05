package Demo2.repository;

import Demo2.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> { // [cite: 419-422]
    Optional<UserInfo> findByName(String username);
}