package com.citibridge.sanctionScreening.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseCount {
    private int validationPass;
    private int validationFail;
    private int screeningPass;
    private int screeningFail;
}
