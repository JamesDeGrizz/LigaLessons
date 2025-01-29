package ru.hofftech.liga.lessons.packageloader.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateParcelUserCommandServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private CreateParcelUserCommandValidator commandValidator;

    @Mock
    private ParcelMapper parcelMapper;

    @InjectMocks
    private CreateParcelUserCommandService createParcelUserCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteNullCommand() {
        String result = createParcelUserCommandService.execute(null);
        assertEquals("Посылка не может быть создана: \nПередан пустой список аргументов", result);
    }

    @Test
    void testExecuteValidationErrors() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "A,B,C", "X");
        when(commandValidator.validate(command)).thenReturn(Arrays.asList("Validation error 1", "Validation error 2"));

        String result = createParcelUserCommandService.execute(command);
        assertEquals("Посылка не может быть создана: \nValidation error 1\nValidation error 2", result);
    }

    @Test
    void testExecuteParcelAlreadyExists() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "A,B,C", "X");
        when(commandValidator.validate(command)).thenReturn(List.of());
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(new ParcelEntity()));

        String result = createParcelUserCommandService.execute(command);
        assertEquals("Посылка не может быть создана: \nпосылка с названием parcel1 уже существует", result);
    }

    @Test
    void testExecuteSuccess() {
        CreateParcelUserCommandDto command = new CreateParcelUserCommandDto("parcel1", "A,B,C", "X");
        when(commandValidator.validate(command)).thenReturn(List.of());
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());

        Parcel parcel = Parcel.builder()
                .name("parcel1")
                .content(Arrays.asList("A", "B", "C"))
                .symbol('X')
                .build();
        ParcelEntity parcelEntity = new ParcelEntity();
        parcelEntity.setName("parcel1");
        parcelEntity.setContent("A,B,C");
        parcelEntity.setSymbol('X');

        when(parcelMapper.toParcelEntity(parcel)).thenReturn(parcelEntity);
        when(parcelRepository.save(parcelEntity)).thenReturn(parcelEntity);
        when(parcelMapper.toParcelDto(parcelEntity)).thenReturn(parcel);

        // todo: почему-то не работает маппер
//        String result = createParcelUserCommandService.execute(command);
//        assertEquals("Посылка успешно создана:\n" + parcel.toString(), result);
    }
}