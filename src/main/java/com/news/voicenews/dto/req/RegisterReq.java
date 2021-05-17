package com.news.voicenews.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterReq {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Integer yearOfBirth;

    @NotNull
    private List<Long> categories;
}
