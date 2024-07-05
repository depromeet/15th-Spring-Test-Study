package com.depromeet.yunbeom.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public record UserUpdateRequest(String nickname, String address) {

    @Builder
    public UserUpdateRequest(
        @JsonProperty("nickname") String nickname,
        @JsonProperty("address") String address) {
        this.nickname = nickname;
        this.address = address;
    }
}
