package ru.hofftech.liga.lessons.packageloader.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditParcelUserCommandValidatorTest {
    private EditParcelUserCommandValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new EditParcelUserCommandValidator();
    }

    @Test
    public void testValidateAllFieldsPresent() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "newId", "form", "symbol");
        List<String> errors = validator.validate(command);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateMissingCurrentParcelId() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto(null, "newId", "form", "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"id\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyCurrentParcelId() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("", "newId", "form", "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"id\"", errors.get(0));
    }

    @Test
    public void testValidateMissingNewParcelId() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", null, "form", "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyNewParcelId() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "", "form", "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"name\"", errors.get(0));
    }

    @Test
    public void testValidateMissingPackageSymbol() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "newId", "form", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"symbol\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyPackageSymbol() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "newId", "form", "");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"symbol\"", errors.get(0));
    }

    @Test
    public void testValidateMissingParcelContent() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "newId", null, "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"form\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyParcelContent() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto("currentId", "newId", "", "symbol");
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"form\"", errors.get(0));
    }

    @Test
    public void testValidateAllFieldsMissing() {
        EditParcelUserCommandDto command = new EditParcelUserCommandDto(null, null, null, null);
        List<String> errors = validator.validate(command);
        assertEquals(4, errors.size());
        assertTrue(errors.contains("Не хватает аргумента \"id\""));
        assertTrue(errors.contains("Не хватает аргумента \"name\""));
        assertTrue(errors.contains("Не хватает аргумента \"symbol\""));
        assertTrue(errors.contains("Не хватает аргумента \"form\""));
    }
}