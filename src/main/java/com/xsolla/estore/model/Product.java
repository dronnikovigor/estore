package com.xsolla.estore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    private Long sku;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Long price;

}
