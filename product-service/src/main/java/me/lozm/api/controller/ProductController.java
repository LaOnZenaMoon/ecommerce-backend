package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.api.service.ProductService;
import me.lozm.product.dto.ProductCreateRequestDto;
import me.lozm.product.dto.ProductCreateResponseDto;
import me.lozm.product.dto.ProductInfoResponseDto;
import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.utils.MapperUtils.mapStrictly;

@RequestMapping("products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductInfoResponseDto>> getProductList() {
        List<ProductInfoVo> productList = productService.getProductList();

        List<ProductInfoResponseDto> responseList = productList.stream()
                .map(vo -> mapStrictly(vo, ProductInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductInfoResponseDto> getProductDetail(@PathVariable("productId") Long productId) {
        ProductInfoVo productInfoVo = productService.getProductDetail(productId);

        ProductInfoResponseDto productInfoResponseDto = mapStrictly(productInfoVo, ProductInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productInfoResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponseDto> createProduct(@RequestBody @Validated ProductCreateRequestDto requestDto) {
        ProductCreateVo productCreateVo = mapStrictly(requestDto, ProductCreateVo.class);
        ProductCreateVo responseProductCreateVo = productService.createProduct(productCreateVo);

        ProductCreateResponseDto productCreateResponseDto = mapStrictly(responseProductCreateVo, ProductCreateResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCreateResponseDto);
    }

}
