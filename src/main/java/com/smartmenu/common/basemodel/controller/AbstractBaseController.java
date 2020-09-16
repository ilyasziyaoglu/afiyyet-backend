package com.smartmenu.common.basemodel.controller;

import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@CrossOrigin(origins = "*", allowedHeaders = "*")
public abstract class AbstractBaseController<
        Request extends BaseRequest,
        Entity extends AbstractBaseEntity,
        Response extends BaseResponse,
        Mapper extends BaseMapper<Request, Entity, Response>,
        Service extends AbstractBaseService<Request, Entity, Response, Mapper>> {

    public final String GUEST = "/guest";
    public final String HEADER_TOKEN = "Authorization";

    protected abstract Service getService();
    protected abstract Mapper getMapper();

    @GetMapping(GUEST + "/{id}")
    public ResponseEntity<Response> get(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
        ServiceResult<Entity> serviceResult = getService().get(token, id);
        return new ResponseEntity<>(getMapper().toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader(HEADER_TOKEN) String token, @Valid @PathVariable Long id) {
        ServiceResult<Boolean> serviceResult = getService().delete(token, id);
        return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
    }

    @PostMapping("/delete-all")
    public ResponseEntity<Boolean> delete(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Set<Long> ids) {
        ServiceResult<Boolean> serviceResult = getService().deleteAll(token, ids);
        return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
    }

    @PutMapping
    public ResponseEntity<Response> update(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Request request) {
        ServiceResult<Entity> serviceResult = getService().update(token, request);
        return new ResponseEntity<>(getMapper().toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
    }

    @PostMapping
    public ResponseEntity<Response> save(@RequestHeader(HEADER_TOKEN) String token, @RequestBody Request request) {
        ServiceResult<Entity> serviceResult = getService().save(token, request);
        return new ResponseEntity<>(getMapper().toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
    }

    @GetMapping(GUEST + "/all")
    public ResponseEntity<List<Response>> getAll(@RequestHeader(HEADER_TOKEN) String token) {
        ServiceResult<List<Entity>> serviceResult = getService().getAll(token);
        return new ResponseEntity<>(getMapper().toResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
    }
}
