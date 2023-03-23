package com.jangbogo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRequestDTO {
    private String p_cert_key;
    private String p_cert_id;
    private String p_returntype;
    private String p_product_cls_code;
    private String p_itemcategorycode;
    private String p_country_code;
    private String p_regday;
    private String p_itemcode;
    private String p_convert_kg_yn;
}