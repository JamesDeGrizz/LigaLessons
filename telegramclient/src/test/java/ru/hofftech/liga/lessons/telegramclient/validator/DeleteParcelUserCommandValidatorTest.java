package ru.hofftech.liga.lessons.telegramclient.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.telegramclient.model.dto.DeleteParcelUserCommandDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteParcelUserCommandValidatorTest {
    private DeleteParcelUserCommandValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new DeleteParcelUserCommandValidator();
    }

    @Test
    public void testValidateParcelNamePresent() {
        DeleteParcelUserCommandDto command = new DeleteParcelUserCommandDto("parcel1");
        List<String> errors = validator.validate(command);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateParcelNameMissing() {
        DeleteParcelUserCommandDto command = new DeleteParcelUserCommandDto(null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateParcelNameEmpty() {
        DeleteParcelUserCommandDto command = new DeleteParcelUserCommandDto("");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }
}