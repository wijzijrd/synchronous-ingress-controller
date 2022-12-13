package com.qirat.api.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Input {
    private UUID requestId;
    private String message;

}
