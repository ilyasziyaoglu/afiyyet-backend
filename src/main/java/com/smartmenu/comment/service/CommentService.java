package com.smartmenu.comment.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.client.comment.CommentResponse;
import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.comment.db.repository.CommentRepository;
import com.smartmenu.comment.mapper.CommentMapper;
import com.smartmenu.comment.mapper.CommentUpdateMapper;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
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
			return new ServiceResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<List<Comment>> getCommentsByBrand(String token) {
		try {
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.FEEDBACKS)) {
				return forbiddenList();
			}
			List<Comment> entityList = getRepository().findAllByBrand(getUser(token).getBrand());
			entityList.sort(Comparator.comparing(Comment::getCreatedDate, Comparator.nullsLast(Comparator.naturalOrder())));
			return new ServiceResult<>(entityList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
