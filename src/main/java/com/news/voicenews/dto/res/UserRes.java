package com.news.voicenews.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.news.voicenews.enums.RoleType;
import com.news.voicenews.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRes {

    private Long id;
    private String username;

    @JsonProperty("year_of_birth")
    private Integer yearOfBirth;

    private List<String> roles;

    private List<Category> categories;

}
