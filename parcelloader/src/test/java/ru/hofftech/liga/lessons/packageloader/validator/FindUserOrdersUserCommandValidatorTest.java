package ru.hofftech.liga.lessons.packageloader.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindUserOrdersUserCommandValidatorTest {
    private FindUserOrdersUserCommandValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new FindUserOrdersUserCommandValidator();
    }

    @Test
    public void testValidateUserIdPresent() {
        FindUserOrdersUserCommandDto command = new FindUserOrdersUserCommandDto("user1");
        List<String> errors = validator.validate(command);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateUserIdMissing() {
        FindUserOrdersUserCommandDto command = new FindUserOrdersUserCommandDto(null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateUserIdEmpty() {
        FindUserOrdersUserCommandDto command = new FindUserOrdersUserCommandDto("");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }
}