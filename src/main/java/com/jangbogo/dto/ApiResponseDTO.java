package com.jangbogo.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "response")
public class ApiResponseDTO {
    @XmlElement(name = "item")
    private List<Item> itemList;

    @Data
    public static class Item {
        @XmlElement(name = "item_name")
        private String itemName;

        @XmlElement(name = "itemcode")
        private String itemCode;

        @XmlElement(name = "kind_name")
        private String kindName;

        @XmlElement(name = "kindcode")
        private String kindCode;

        @XmlElement(name = "unit")
        private String unit;

        @XmlElement(name = "day1")
        private String day1;

        @XmlElement(name = "dpr1")
        private String dpr1;
    }
}
