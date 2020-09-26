package com.smartmenu.comment.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.comment.mapper.CommentMapper;
import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class CommentUpdateMapper implements BaseUpdateMapper<CommentRequest, Comment> {

	final private BrandMapper brandMapper;

	@Override
	public Comment toEntityForUpdate(CommentRequest request, Comment entity) {
		if (entity == null) {
			entity = new Comment();
		}

		if (request.getComment() != null) {
			entity.setComment(request.getComment());
		}
		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}

		return entity;
	}
}
