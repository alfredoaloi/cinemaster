package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.DirectorDto;
import com.cinemaster.backend.data.service.DirectorService;
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
public class DirectorServiceTest extends ServiceTest {

    @Test
    public void testDirectorService() {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setName("Ciccio Riccio");
        directorService.save(directorDto);

        Optional<DirectorDto> optional = directorService.findById(directorDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        directorDto = new DirectorDto();
        directorDto.setName("Ciccio Bora");
        directorService.save(directorDto);

        List<DirectorDto> actors = directorService.findAll();
        Assert.assertEquals(2, actors.size());

        directorService.delete(directorDto);

        actors = directorService.findAll();
        Assert.assertEquals(1, actors.size());

        optional = directorService.findById(actors.get(0).getId());
        if (optional.isPresent()) {
            directorDto = optional.get();
        } else {
            Assert.fail();
        }

        directorDto.setName("Alfredo");
        directorService.update(directorDto);

        optional = directorService.findById(directorDto.getId());
        if (optional.isPresent()) {
            Assert.assertEquals(directorDto.getName(), optional.get().getName());
        } else {
            Assert.fail();
        }
    }
}
