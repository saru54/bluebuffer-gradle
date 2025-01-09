package com.core.domain.cq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClubAdminOperationCommand {
    private String userId;
    private String clubId;
}
