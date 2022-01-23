package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.api.service.ProductService;
import me.lozm.common.dto.PageDto;
import me.lozm.common.dto.SearchDto;
import me.lozm.common.vo.PageVo;
import me.lozm.common.vo.SearchVo;
import me.lozm.product.dto.ProductCreateRequestDto;
import me.lozm.product.dto.ProductCreateResponseDto;
import me.lozm.product.dto.ProductInfoResponseDto;
import me.lozm.product.vo.ProductCreateVo;
import me.lozm.product.vo.ProductInfoVo;
import me.lozm.user.dto.UserInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static me.lozm.utils.MapperUtils.mapStrictly;

@RequestMapping("products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<Page<ProductInfoResponseDto>> getProductList(PageDto pageDto, SearchDto searchDto) {
        final PageVo pageVo = mapStrictly(pageDto, PageVo.class);
        final SearchVo searchVo = mapStrictly(searchDto, SearchVo.class);

        Page<ProductInfoVo> productsPage = productService.getProductList(pageVo, searchVo);

        List<ProductInfoResponseDto> responseList = productsPage.getContent()
                .stream()
                .map(vo -> mapStrictly(vo, ProductInfoResponseDto.class))
                .collect(toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageImpl<>(responseList, productsPage.getPageable(), productsPage.getTotalElements()));
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
