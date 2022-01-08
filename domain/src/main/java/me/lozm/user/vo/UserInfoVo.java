package me.lozm.user.vo;

import lombok.Getter;
import lombok.Setter;
import me.lozm.common.vo.BaseVo;

import java.util.List;

@Getter
public class UserInfoVo extends BaseVo {

    private Long id;
    private String email;
    private String name;

    @Setter
    private List<OrderInfoVo> orders;

}
