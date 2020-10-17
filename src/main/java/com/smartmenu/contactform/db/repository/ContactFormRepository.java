package com.smartmenu.contactform.db.repository;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.common.basemodel.db.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ContactFormRepository extends BaseRepository<ContactForm> {
}
