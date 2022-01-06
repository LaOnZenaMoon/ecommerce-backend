package me.lozm.api.service;

import me.lozm.user.vo.UserCreateVo;
import me.lozm.user.vo.UserInfoVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserCreateVo createUser(UserCreateVo userCreateVo);

    UserInfoVo getUserDetail(String userId);

    List<UserInfoVo> getUserList();

    UserInfoVo findByEmail(String email);

}
