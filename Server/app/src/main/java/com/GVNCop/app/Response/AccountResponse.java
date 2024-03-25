package com.GVNCop.app.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    private String accessToken;
    private String name;
}
