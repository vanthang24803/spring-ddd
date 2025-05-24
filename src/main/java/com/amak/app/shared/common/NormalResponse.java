package com.amak.app.shared.common;

import com.amak.app.shared.constants.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "NormalResponseBuilder")
public class NormalResponse {
    private String message;

    public static class NormalResponseBuilder {
        public NormalResponseBuilder message(Message mess) {
            this.message = mess.getMessage();
            return this;
        }
    }
}

