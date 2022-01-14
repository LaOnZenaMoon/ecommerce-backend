package me.lozm.api.controller;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import me.lozm.api.service.OrdersService;
import me.lozm.order.dto.OrdersCreateRequestDto;
import me.lozm.order.dto.OrdersCreateResponseDto;
import me.lozm.order.dto.OrdersInfoResponseDto;
import me.lozm.order.vo.OrdersCreateRequestVo;
import me.lozm.order.vo.OrdersCreateResponseVo;
import me.lozm.order.vo.OrdersInfoVo;
import me.lozm.order.vo.OrdersSearchVo;
import org.springframework.core.env.Environment;
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
    public ResponseEntity<List<OrdersInfoResponseDto>> getOrderList() {
        //TODO 주문 목록 조회 조건 기능 개발
        List<OrdersInfoVo> ordersList = ordersService.getOrdersList(new OrdersSearchVo());
        List<OrdersInfoResponseDto> responseList = ordersList.stream()
                .map(vo -> mapStrictly(vo, OrdersInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
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
