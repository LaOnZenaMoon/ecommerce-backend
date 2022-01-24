package me.lozm.api.controller;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import me.lozm.api.service.OrdersService;
import me.lozm.common.dto.PageDto;
import me.lozm.common.dto.SearchDto;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.order.dto.OrdersCreateRequestDto;
import me.lozm.order.dto.OrdersCreateResponseDto;
import me.lozm.order.dto.OrdersInfoResponseDto;
import me.lozm.order.vo.OrdersCreateRequestVo;
import me.lozm.order.vo.OrdersCreateResponseVo;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;
import me.lozm.user.dto.UserInfoResponseDto;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.utils.JwtUtils.getJwtObject;
import static me.lozm.utils.JwtUtils.getUserIdFromJwt;
import static me.lozm.utils.MapperUtils.mapStrictly;

@RequestMapping("orders")
@RestController
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
    private final Environment environment;


    @GetMapping
    public ResponseEntity<Page<OrdersInfoResponseDto>> getOrdersList(PageDto pageDto, SearchDto searchDto) {
        final PageVo pageVo = mapStrictly(pageDto, PageVo.class);
        final SearchVo searchVo = mapStrictly(searchDto, SearchVo.class);

        Page<OrdersInfoVo> ordersPage = ordersService.getOrdersList(pageVo, searchVo);

        List<OrdersInfoResponseDto> responseList = ordersPage.getContent()
                .stream()
                .map(vo -> mapStrictly(vo, OrdersInfoResponseDto.class))
                .collect(toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageImpl<>(responseList, ordersPage.getPageable(), ordersPage.getTotalElements()));
    }

    @GetMapping("{ordersId}")
    public ResponseEntity getOrdersDetail(@PathVariable("ordersId") Long ordersId) {
        //TODO 주문 상세 조회 기능 개발
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(null);
    }

    @PostMapping
    public ResponseEntity<OrdersCreateResponseDto> createOrders(@RequestHeader("Authorization") String jwt,
                                                                @RequestBody @Validated OrdersCreateRequestDto requestDto) {
        Jwt jwtObject = getJwtObject(jwt, environment.getProperty("jwt-token.secret-key"));
        OrdersCreateResponseVo ordersCreateResponseVo = ordersService.createOrders(new OrdersCreateRequestVo(getUserIdFromJwt(jwtObject), requestDto.getProductId(), requestDto.getQuantity()));
        OrdersCreateResponseDto ordersCreateResponseDto = mapStrictly(ordersCreateResponseVo, OrdersCreateResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordersCreateResponseDto);
    }

}
