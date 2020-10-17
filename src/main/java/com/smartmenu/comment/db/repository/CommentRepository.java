package com.smartmenu.comment.db.repository;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
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
