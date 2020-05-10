package ff.springframework.spring5webfluxrest.controllers;

import ff.springframework.spring5webfluxrest.domain.Category;
import ff.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn
                        (Flux.just(
                                Category.builder().description("Fruits").build(),
                                Category.builder().description("Nuts").build()));
         webTestClient.get().uri(CategoryController.BASE_URL)
                 .exchange()
                 .expectBodyList(Category.class)
                 .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Fruits").build()));

        webTestClient.get().uri(CategoryController.BASE_URL + "/someid")
                .exchange()
                .expectBody(Category.class);
    }
}