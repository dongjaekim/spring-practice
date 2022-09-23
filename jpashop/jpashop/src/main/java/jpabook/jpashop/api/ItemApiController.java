package jpabook.jpashop.api;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.CategoryService;
import jpabook.jpashop.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @PostMapping("/api/v1/items")
    public CreateItemResponse saveItemV1(@RequestBody @Valid CreateItemRequest request) {

        Item item = null;

        if ("book".equals(request.getType())) {
            Book book = new Book();
            book.setName(request.getName());
            book.setPrice(request.getPrice());
            book.setStockQuantity(request.getStockQuantity());
            book.setAuthor(request.getAuthor());
            book.setIsbn(request.getIsbn());
            item = book;
        } else if ("album".equals(request.getType())) {
            Album album = new Album();
            album.setName(request.getName());
            album.setPrice(request.getPrice());
            album.setStockQuantity(request.getStockQuantity());
            album.setArtist(request.getArtist());
            album.setEtc(request.getEtc());
            item = album;
        } else if ("movie".equals(request.getType())) {
            Movie movie = new Movie();
            movie.setName(request.getName());
            movie.setPrice(request.getPrice());
            movie.setStockQuantity(request.getStockQuantity());
            movie.setActor(request.getActor());
            movie.setDirector(request.getDirector());
            item = movie;
        }

        for (Long category_id : request.getCategories()) {
            item.addCategory(categoryService.findOne(category_id));
        }

        Long savedId = itemService.saveItem(item);
        return new CreateItemResponse(savedId);
    }

    @Data
    @AllArgsConstructor
    static class CreateItemResponse {
        Long itemId;
    }

    @Data
    static class CreateItemRequest {
        @NotEmpty
        private String type;

        @NotEmpty
        private String name;

        @NotNull
        private int price;

        @NotNull
        private int stockQuantity;

        private List<Long> categories;

        private String artist;
        private String etc;

        private String author;
        private String isbn;

        private String actor;
        private String director;
    }
}
