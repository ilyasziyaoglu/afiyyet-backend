package com.afiyyet.common.basemodel.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@CrossOrigin(origins = "*", allowedHeaders = "*")
public abstract class AbstractBaseController {

    public final String GUEST = "/guest";
    public final String HEADER_TOKEN = "Authorization";

    public String KAMPANYALAR = "KAMPANYALAR";
    public String MENULER = "MENÜLER";
}
