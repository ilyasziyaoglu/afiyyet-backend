package com.afiyyet.comment.service;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.client.comment.CommentRequest;
import com.afiyyet.client.comment.CommentResponse;
import com.afiyyet.comment.db.entity.Comment;
import com.afiyyet.comment.db.repository.CommentRepository;
import com.afiyyet.comment.mapper.CommentMapper;
import com.afiyyet.comment.mapper.CommentUpdateMapper;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class CommentService extends AbstractBaseService<CommentRequest, Comment, CommentResponse, CommentMapper> {
	final private CommentRepository repository;
	final private CommentMapper mapper;
	final private CommentUpdateMapper updateMapper;
	final private BrandRepository brandRepository;

	@Override
	public CommentRepository getRepository() {
		return repository;
	}

	@Override
	public CommentMapper getMapper() {
		return mapper;
	}

	@Override
	public CommentUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<Boolean> insertComment(CommentRequest request) {
		try {
			Brand brand = brandRepository.getOne(request.getBrand().getId());
			if (!brand.getFeatures().contains(FeatureType.FEEDBACKS)) {
				return forbiddenBoolean();
			}
			Comment entityToSave = getMapper().toEntity(request);
			entityToSave.setBrand(brand);
			getRepository().save(entityToSave);
			return new ServiceResult<>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<CommentResponse>> getCommentsByBrand(String token) {
		try {
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.FEEDBACKS)) {
				return forbiddenList();
			}
			List<Comment> entityList = getRepository().findAllByBrand(getUser(token).getBrand());
			entityList.sort(Comparator.comparing(Comment::getCreatedDate, Comparator.nullsLast(Comparator.naturalOrder())));
			return new ServiceResult<>(mapper.toResponse(entityList));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
