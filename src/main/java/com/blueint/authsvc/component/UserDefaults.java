package com.blueint.authsvc.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:static.properties")
public class UserDefaults {
   @Value("${default.user.state.locked}")
    private boolean defaultUserStateLocked;

    @Value("${default.user.state.active}")
    private boolean defaultUserStateActive;
}
