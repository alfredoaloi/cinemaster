package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.ActorDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActorServiceTest extends ServiceTest {

    @Test
    public void testActorService() {
        ActorDto actorDto = new ActorDto();
        actorDto.setName("Ciccio Riccio");
        actorService.save(actorDto);

        Optional<ActorDto> optional = actorService.findById(actorDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        actorDto = new ActorDto();
        actorDto.setName("Ciccio Bora");
        actorService.save(actorDto);

        List<ActorDto> actors = actorService.findAll();
        Assert.assertEquals(2, actors.size());

        actorService.delete(actorDto);

        actors = actorService.findAll();
        Assert.assertEquals(1, actors.size());

        optional = actorService.findById(actors.get(0).getId());
        if (optional.isPresent()) {
            actorDto = optional.get();
        } else {
            Assert.fail();
        }

        actorDto.setName("Alfredo");
        actorService.update(actorDto);

        optional = actorService.findById(actorDto.getId());
        if (optional.isPresent()) {
            Assert.assertEquals(actorDto.getName(), optional.get().getName());
        } else {
            Assert.fail();
        }
    }
}
