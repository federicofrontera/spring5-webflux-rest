package ff.springframework.spring5webfluxrest.controllers;

import ff.springframework.spring5webfluxrest.domain.Vendor;
import ff.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public final static String BASE_URL = "/api/v1/vendors";
    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Vendor> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        boolean changes = false;
        Vendor foundVendor = vendorRepository.findById(id).block();

        if (!foundVendor.getFirstName().equals(vendor.getFirstName())) {
            foundVendor.setFirstName(vendor.getFirstName());
            changes = true;
        }
        if (!foundVendor.getLastName().equals(vendor.getLastName())) {
            foundVendor.setLastName(vendor.getLastName());
            changes = true;
        }
        if (changes) {
            return vendorRepository.save(foundVendor);
        } else {
            return Mono.just(foundVendor);
        }
    }
}
