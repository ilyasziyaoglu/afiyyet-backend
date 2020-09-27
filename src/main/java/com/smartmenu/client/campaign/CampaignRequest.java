package com.smartmenu.client.campaign;

import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.common.basemodel.request.BaseRequest;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CampaignRequest extends BaseRequest {
	private String name;
	private String imgUrl;
	private BigDecimal price;
	private String description;
	private Integer likes;
	private Integer order;
	private BrandRequest brand;
	private Status status;
	private ZonedDateTime expireDate;
	private ZonedDateTime startDate;
}
