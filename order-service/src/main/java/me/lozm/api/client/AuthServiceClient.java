package me.lozm.api.client;

import me.lozm.user.dto.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("auth-service-app")
public interface AuthServiceClient {

    @GetMapping("users/{userId}")
    ResponseEntity<UserInfoResponseDto> getUserDetail(@PathVariable("userId") Long userId);

}
