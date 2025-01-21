package ru.hofftech.liga.lessons.packageloader.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParcelRepositoryTest {
    private ParcelRepository parcelRepository;

    @BeforeEach
    public void setUp() {
        parcelRepository = new ParcelRepository();
    }

    @Test
    public void testAdd() {
        Parcel newParcel = new Parcel(List.of("10"), "тип 10", 'A');
        boolean result = parcelRepository.add(newParcel);
        assertTrue(result);
        assertEquals(10, parcelRepository.findAll().size());
    }

    @Test
    public void testAddDuplicate() {
        Parcel duplicateParcel = new Parcel(List.of("тип 1"), "тип 1", '1');
        boolean result = parcelRepository.add(duplicateParcel);
        assertFalse(result);
        assertEquals(9, parcelRepository.findAll().size());
    }

    @Test
    public void testFind() {
        Optional<Parcel> parcel = parcelRepository.find("тип 1");
        assertTrue(parcel.isPresent());
        assertEquals("тип 1", parcel.get().getName());
    }

    @Test
    public void testFindNotFound() {
        Optional<Parcel> parcel = parcelRepository.find("тип 10");
        assertNull(parcel);
    }

    @Test
    public void testFindAll() {
        List<Parcel> parcels = parcelRepository.findAll();
        assertEquals(9, parcels.size());
    }

    @Test
    public void testUpdate() {
        Parcel updatedParcel = new Parcel(List.of("100"), "тип 1", 'A');
        boolean result = parcelRepository.update("тип 1", updatedParcel);
        assertTrue(result);
        Optional<Parcel> parcel = parcelRepository.find("тип 1");
        assertTrue(parcel.isPresent());
        assertEquals("тип 1", parcel.get().getName());
        assertEquals('A', parcel.get().getSymbol());
    }

    @Test
    public void testUpdateNotFound() {
        Parcel updatedParcel = new Parcel(List.of("100"), "тип 10", 'A');
        boolean result = parcelRepository.update("тип 10", updatedParcel);
        assertFalse(result);
    }

    @Test
    public void testDelete() {
        boolean result = parcelRepository.delete("тип 1");
        assertTrue(result);
        Optional<Parcel> parcel = parcelRepository.find("тип 1");
        assertNull(parcel);
    }

    @Test
    public void testDeleteNotFound() {
        boolean result = parcelRepository.delete("тип 10");
        assertFalse(result);
    }
}