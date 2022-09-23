package jpabook.jpashop.api;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/api/v1/categories")
    public CreateCategoryResponse saveCategoryV1(@RequestBody @Valid CreateCategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());

        if(request.getParent_id() != null)
            category.setParent(categoryService.findOne(request.getParent_id()));

        Long savedId = categoryService.saveCategory(category);
        return new CreateCategoryResponse(savedId);
    }

    @GetMapping("/api/v1/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findCategories().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }

    @Data
    static class CreateCategoryRequest {
        @NotEmpty
        private String name;
        private Long parent_id;
    }

    @Data
    @AllArgsConstructor
    static class CreateCategoryResponse {
        private Long category_id;
    }

    @Data
    @AllArgsConstructor
    static class CategoryDto {
        private Long category_id;
        private String name;
        private Long parent_id;
        private List<Long> childs;

        public CategoryDto(Category category) {
            category_id = category.getId();
            name = category.getName();
            parent_id = category.getParent() != null ? category.getParent().getId() : null;
            childs = category.getChild().stream().map(c -> c.getId()).collect(Collectors.toList());
        }
    }

}

