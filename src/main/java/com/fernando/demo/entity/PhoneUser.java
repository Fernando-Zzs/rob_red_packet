package com.fernando.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PhoneUser implements Serializable {
    private String phone;
    private Double fare;

    // 因为要放进map中，需要重写自定义类的这两个方法
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        PhoneUser phoneUser = (PhoneUser) o;
        return phone != null ? phone.equals(phoneUser.phone) : phoneUser.phone == null;
    }

    @Override
    public int hashCode(){
        return phone != null ? phone.hashCode() : 0;
    }
}
