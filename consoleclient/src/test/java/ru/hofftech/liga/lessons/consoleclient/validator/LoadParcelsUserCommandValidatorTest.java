package ru.hofftech.liga.lessons.consoleclient.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsUserCommandDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadParcelsUserCommandValidatorTest {
    private LoadParcelsUserCommandValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new LoadParcelsUserCommandValidator();
    }

    @Test
    public void testValidateAllFieldsPresent() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateMissingUserId() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", null, "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"user-id\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyUserId() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", "", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"user-id\"", errors.get(0));
    }

    @Test
    public void testValidateMissingOut() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto(null, "1", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"out\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyOut() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("", "1", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"out\"", errors.get(0));
    }

    @Test
    public void testValidateInvalidOutType() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("invalid-type", "1", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Неправильное значение типа аргумента \"out\". Доступно \"json-file\" и \"text\"", errors.get(0));
    }

    @Test
    public void testValidateMissingReportFileName() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", "user1", null, "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"out-filename\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyReportFileName() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", "user1", "", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"out-filename\"", errors.get(0));
    }

    @Test
    public void testValidateMissingPackages() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20", "user1", "report.json", null, null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Должен быть указан 1 из параметров: \"parcels-text\" или \"parcels-file\"", errors.get(0));
    }

    @Test
    public void testValidateMissingPlacingAlgorithm() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", null, "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"type\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyPlacingAlgorithm() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"type\"", errors.get(0));
    }

    @Test
    public void testValidateInvalidPlacingAlgorithm() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "3", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Неправильное значение типа алгоритма: 3", errors.get(0));
    }

    @Test
    public void testValidateNonNumericPlacingAlgorithm() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "abc", "10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Введённое значение нельзя привести к числу: abc", errors.get(0));
    }

    @Test
    public void testValidateMissingTruckSizes() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", null, "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"trucks\"", errors.get(0));
    }

    @Test
    public void testValidateEmptyTruckSizes() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Не хватает аргумента \"trucks\"", errors.get(0));
    }

    @Test
    public void testValidateInvalidTruckSizeFormat() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x20x30", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Неправильный формат размера кузова грузовика: 10x20x30", errors.get(0));
    }

    @Test
    public void testValidateInvalidTruckWidth() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "-10x20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Неправильное значение ширины грузовика: -10", errors.get(0));
    }

    @Test
    public void testValidateInvalidTruckHeight() {
        LoadParcelsUserCommandDto command = new LoadParcelsUserCommandDto("json-file", "1", "10x-20", "user1", "report.json", "parcel1", null);
        List<String> errors = validator.validate(command);
        assertEquals(1, errors.size());
        assertEquals("Неправильное значение высоты грузовика: -20", errors.get(0));
    }
}