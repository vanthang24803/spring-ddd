package com.amak.app.shared.common;

import com.amak.app.shared.constants.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private String version;
    private String path;
    private String method;
    private String requestId;
    private Instant timestamp;
    private String device;

    public Metadata(String path, String method, String device, String requestId) {
        this.version = Version.V1.getVersionNumber();
        this.path = path;
        this.method = method;
        this.requestId = requestId;
        this.timestamp = Instant.now();
        this.device = device;
    }
}
