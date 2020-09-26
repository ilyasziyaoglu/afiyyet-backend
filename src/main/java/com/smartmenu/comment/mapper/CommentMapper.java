package com.smartmenu.comment.mapper;

import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.client.comment.CommentResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<CommentRequest, Comment, CommentResponse> {
}
