package com.afiyyet.common.basemodel.request.pager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mehmet Kelleci
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtColumnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String data;

    private String name;

    private Boolean searchable;

    private Boolean orderable;

    private DtSearchDto search;
}
