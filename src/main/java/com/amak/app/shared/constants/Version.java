package com.amak.app.shared.constants;

public enum Version {
    V1("1.0.0");

    private final String versionNumber;

    Version(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }
}
