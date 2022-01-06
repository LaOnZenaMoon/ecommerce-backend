package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.api.service.UserService;
import me.lozm.user.dto.UserCreateRequestDto;
import me.lozm.user.dto.UserCreateResponseDto;
import me.lozm.user.dto.UserInfoResponseDto;
import me.lozm.user.vo.UserCreateVo;
import me.lozm.user.vo.UserInfoVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.utils.MapperUtils.mapStrictly;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserInfoResponseDto>> getUserList() {
        List<UserInfoVo> userList = userService.getUserList();

        List<UserInfoResponseDto> responseList = userList.stream()
                .map(userInfoVo -> mapStrictly(userInfoVo, UserInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserInfoResponseDto> getUserDetail(@PathVariable("userId") String userId) {
        UserInfoVo userInfoVo = userService.getUserDetail(userId);

        UserInfoResponseDto userInfoResponseDto = mapStrictly(userInfoVo, UserInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfoResponseDto);
    }

    @PostMapping
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody @Validated UserCreateRequestDto requestDto) {
        UserCreateVo userCreateVo = mapStrictly(requestDto, UserCreateVo.class);
        UserCreateVo responseUserCreateVo = userService.createUser(userCreateVo);

        UserCreateResponseDto userCreateResponseDto = mapStrictly(responseUserCreateVo, UserCreateResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userCreateResponseDto);
    }

}
