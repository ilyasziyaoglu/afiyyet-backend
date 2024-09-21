package com.afiyyet.comment.mapper;

import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.client.comment.CommentRequest;
import com.afiyyet.comment.db.entity.Comment;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class CommentUpdateMapper implements BaseUpdateMapper<CommentRequest, Comment> {

	private final BrandMapper brandMapper;

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
