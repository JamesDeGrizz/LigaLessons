package ru.hofftech.liga.lessons.telegramclient.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.telegramclient.model.dto.CreateParcelUserCommandDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateParcelUserCommandValidatorTest {
    private CreateParcelUserCommandValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new CreateParcelUserCommandValidator();
    }

    @Test
    public void testValidateAllFieldsPresent() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "form1", "symbol1");
        List<String> errors = validator.validate(command);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateMissingParcelName() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto(null, "form1", "symbol1");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyParcelName() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("", "form1", "symbol1");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateMissingPackageSymbol() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "form1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"symbol\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyPackageSymbol() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "form1", "");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"symbol\"", errors.get(0));
    }

    @Test
    public void testValidateMissingParcelContent() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", null, "symbol1");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"form\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyParcelContent() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "", "symbol1");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"form\"", errors.get(0));
    }

    @Test
    public void testValidateAllFieldsMissing() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto(null, null, null);
        List<String> errors = validator.validate(command);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("Не хватает аргумента \"name\""));
        assertTrue(errors.contains("Не хватает аргумента \"symbol\""));
        assertTrue(errors.contains("Не хватает аргумента \"form\""));
    }
}