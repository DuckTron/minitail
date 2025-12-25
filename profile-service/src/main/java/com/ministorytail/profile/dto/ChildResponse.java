package com.ministorytail.profile.dto;

import java.util.List;

import com.ministorytail.profile.model.Child;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildResponse {

    private Long childId;
    private Long parentUserId;
    private String name;
    private Integer age;
    private List<String> interests;

    // Factory method para converter do domínio
    public static ChildResponse fromDomain(Child child) {
        return new ChildResponse(
                child.getId(),
                child.getParentUserId(),
                child.getName(),
                child.getAge(),
                child.getInterests());
    }
}