package com.smartmenu.campaign.mapper;

import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface CampaignMapper extends BaseMapper<CampaignRequest, Campaign, CampaignResponse> {
}
