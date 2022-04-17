package com.cinemaster.backend.controller.user;

import com.cinemaster.backend.core.exception.ShowNotFoundException;
import com.cinemaster.backend.data.dto.ShowDto;
import com.cinemaster.backend.data.service.CategoryService;
import com.cinemaster.backend.data.service.ShowService;
import com.cinemaster.backend.data.specification.ShowSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/shows")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity showList() {
        return ResponseEntity.ok(showService.findAll());
    }


    @GetMapping("/search")
    public ResponseEntity showListByName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "coming-soon", required = false) Boolean comingSoon,
            @RequestParam(value = "highlighted", required = false) Boolean highlighted,
            @RequestParam(value = "category", required = false) String[] categories) {
        return ResponseEntity.ok(showService.findAllByFilter(new ShowSpecification.Filter(name, comingSoon, highlighted, categories)));
    }

    @GetMapping("/details")
    public ResponseEntity showDetails(
            @RequestParam(value = "id", required = true) Long id) {
        Optional<ShowDto> optional = showService.findById(id);
        if (optional.isPresent()) {
            ShowDto show = optional.get();
            return ResponseEntity.ok(show);
        } else {
            throw new ShowNotFoundException();
        }
    }

    @GetMapping("/next-week")
    public ResponseEntity nextShowList() {
        return ResponseEntity.ok(showService.findAllByEventBeforeNextWeek());
    }

    @GetMapping("/categories")
    public ResponseEntity categoryList() {
        return ResponseEntity.ok(categoryService.findAll());
    }

}
