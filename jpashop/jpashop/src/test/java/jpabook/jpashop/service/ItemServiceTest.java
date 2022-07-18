package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품_추가() throws Exception {
        //given
        Item book = new Book();
        book.setName("aaa");
        book.setStockQuantity(10);
        book.setPrice(10000);

        //when
        Long savedId = itemService.saveItem(book);

        //then
        assertThat(book).isEqualTo(itemRepository.findOne(savedId));
    }

    @Test
    public void 상품_수량_추가() throws Exception {
        //given
        Item album = new Album();
        album.setName("aaa");
        album.setStockQuantity(10);
        album.setPrice(10000);
        Long savedId = itemService.saveItem(album);

        //when
        album.addStock(90);

        //then
        assertThat(itemService.findOne(savedId).getStockQuantity()).isEqualTo(100);
    }

    @Test
    public void 상품_수량_축소_예외() throws Exception {
        //given
        Item movie = new Movie();
        movie.setName("aaa");
        movie.setStockQuantity(10);
        movie.setPrice(10000);

        //when
        itemService.saveItem(movie);

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            movie.removeStock(20);
        });
    }
}
