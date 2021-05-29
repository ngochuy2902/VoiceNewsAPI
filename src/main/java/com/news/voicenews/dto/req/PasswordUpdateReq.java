package com.news.voicenews.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateReq {

    @NotNull
    @JsonProperty("old_password")
    private String oldPassword;

    @NotNull
    @JsonProperty("new_password")
    private String newPassword;
}
