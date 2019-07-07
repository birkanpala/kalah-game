package com.backbase.kalahgame.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {

    private String id;

    private String uri;

    private Map<String, String> status;
}
