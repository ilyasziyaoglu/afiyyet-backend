package com.afiyyet.contactform.db.repository;

import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.contactform.db.entity.ContactForm;
import org.springframework.stereotype.Repository;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ContactFormRepository extends BaseRepository<ContactForm> {
}
