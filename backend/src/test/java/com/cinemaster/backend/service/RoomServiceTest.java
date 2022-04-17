package com.cinemaster.backend.service;

import com.cinemaster.backend.TestUtils;
import com.cinemaster.backend.data.dto.RoomDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoomServiceTest extends ServiceTest {

    @Test
    public void testRoomService() {
        RoomDto roomDto = TestUtils.createRoomDto("fhg jm,", 1, 1);
        roomService.save(roomDto);

        Optional<RoomDto> optional = roomService.findById(roomDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }
}
