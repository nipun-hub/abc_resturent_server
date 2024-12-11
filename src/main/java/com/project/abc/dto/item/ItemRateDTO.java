package com.project.abc.dto.item;

import com.project.abc.commons.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ItemRateDTO extends BaseRequest {
    private int rate;
}
