package com.smartmenu.client.menu;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.client.category.CategoryResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuResponse extends BaseResponse {
	private List<CategoryResponse> categories;
	private List<CampaignResponse> campaigns;
	private BrandResponse brand;
}
