package com.afiyyet.comment.db.repository;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.comment.db.entity.Comment;
import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface CommentRepository extends BaseRepository<Comment> {
	List<Comment> findAllByBrand(Brand brand);
}
