package ff.springframework.spring5webfluxrest.controllers;

import ff.springframework.spring5webfluxrest.domain.Category;
import ff.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void create() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));
        Mono<Category> savedCategory = Mono.just(Category.builder().description("cat1").build());

        webTestClient.post().uri(CategoryController.BASE_URL)
                .body(savedCategory,Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));
        Mono<Category> updatedCategory = Mono.just(Category.builder().description("cat1").build());

        webTestClient.put().uri(CategoryController.BASE_URL + "/someid")
                .body(updatedCategory, Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void patch() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));
        Mono<Category> updatedCategory = Mono.just(Category.builder().description("some desc").build());

        webTestClient.patch().uri(CategoryController.BASE_URL + "/someid")
                .body(updatedCategory, Category.class)
                .exchange()
                .expectStatus().isOk();

        BDDMockito.verify(categoryRepository).save(any());
    }
}