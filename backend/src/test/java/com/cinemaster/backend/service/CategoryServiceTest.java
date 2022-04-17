package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest extends ServiceTest {

    @Test
    public void testCategoryService() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Azione");
        categoryService.save(categoryDto);

        Optional<CategoryDto> optional = categoryService.findById(categoryDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        categoryDto = new CategoryDto();
        categoryDto.setName("Avventura");
        categoryService.save(categoryDto);

        List<CategoryDto> actors = categoryService.findAll();
        Assert.assertEquals(2, actors.size());

        categoryService.delete(categoryDto);

        actors = categoryService.findAll();
        Assert.assertEquals(1, actors.size());

        optional = categoryService.findById(actors.get(0).getId());
        if (optional.isPresent()) {
            categoryDto = optional.get();
        } else {
            Assert.fail();
        }

        categoryDto.setName("Giallo");
        categoryService.update(categoryDto);

        optional = categoryService.findById(categoryDto.getId());
        if (optional.isPresent()) {
            Assert.assertEquals(categoryDto.getName(), optional.get().getName());
        } else {
            Assert.fail();
        }
    }
}
