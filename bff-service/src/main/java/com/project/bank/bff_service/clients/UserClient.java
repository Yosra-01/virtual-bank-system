
package com.project.bank.bff_service.clients;

import com.project.bank.bff_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${svc.user.base-url}")
public interface UserClient {
    @GetMapping("/users/{userId}/profile")
    UserDto getUserProfile(
            @PathVariable("userId") UUID userId,
            @RequestHeader("X-User-Id") String headerUserId
    );
}
