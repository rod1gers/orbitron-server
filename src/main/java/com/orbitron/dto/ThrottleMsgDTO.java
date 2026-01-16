package com.orbitron.dto;

import lombok.Data;

@Data
public class ThrottleMsgDTO {
    final int throttlePosition;
    final int engineId;
}
