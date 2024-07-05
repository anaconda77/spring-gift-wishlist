package gift.controller;

import gift.auth.LoginMember;
import gift.dto.LoginMemberDto;
import gift.dto.ProductAddRequest;
import gift.dto.ProductResponse;
import gift.dto.WishListAddRequest;
import gift.exception.InputException;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.WishProductDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishListApiController {

    private final WishProductDao wishProductDao;

    public WishListApiController(WishProductDao wishProductDao) {
        this.wishProductDao = wishProductDao;
    }


    @GetMapping("/api/wishlist")
    public ResponseEntity<List<ProductResponse>> getWishList(@LoginMember LoginMemberDto memberDto) {

        List<Product> wishlist = wishProductDao.findAll(memberDto.id());
        if (wishlist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        List<ProductResponse> dtoList = wishlist.stream()
            .map(ProductResponse::new)
            .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


}