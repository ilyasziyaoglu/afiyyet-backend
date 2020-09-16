package com.smartmenu.common.user.db.repository;

import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import com.smartmenu.common.user.db.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface UserRepository extends BaseRepository<User> {
	Optional<User> findByUsername(String username);
}
