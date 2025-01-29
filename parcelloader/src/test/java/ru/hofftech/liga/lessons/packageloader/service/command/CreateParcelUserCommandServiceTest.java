package ru.hofftech.liga.lessons.packageloader.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateParcelUserCommandServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private CreateParcelUserCommandValidator commandValidator;

    @Mock
    private ParcelMapper parcelMapper;

    @InjectMocks
    private CreateParcelUserCommandService createParcelUserCommandService;

    private CreateParcelUserCommandDto validCommand;
    private Parcel parcelEntity;

    @BeforeEach
    public void setUp() {
        validCommand = new CreateParcelUserCommandDto("parcel1", "A,B,C", "X");
        parcelEntity = Parcel.builder()
                .name("parcel1")
                .content(List.of("A", "B", "C"))
                .symbol('X')
                .build();
    }

    @Test
    public void testExecute_ValidCommand_ReturnsSuccessMessage() {
        when(commandValidator.validate(validCommand)).thenReturn(List.of());
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());
        when(parcelMapper.toParcelEntity(any(Parcel.class))).thenReturn(new ParcelEntity());
        when(parcelRepository.save(any())).thenReturn(new ParcelEntity());
        when(parcelMapper.toParcelDto(any())).thenReturn(parcelEntity);

        String result = createParcelUserCommandService.execute(validCommand);

        assertEquals("Посылка успешно создана:\n" + parcelEntity.toString(), result);
        verify(parcelRepository, times(1)).save(any());
    }

    @Test
    public void testExecute_NullCommand_ReturnsErrorMessage() {
        String result = createParcelUserCommandService.execute(null);

        assertEquals("Посылка не может быть создана: \nПередан пустой список аргументов", result);
    }

    @Test
    public void testExecute_ValidationErrors_ReturnsErrorMessage() {
        when(commandValidator.validate(validCommand)).thenReturn(List.of("Error 1", "Error 2"));

        String result = createParcelUserCommandService.execute(validCommand);

        assertEquals("Посылка не может быть создана: \nError 1\nError 2", result);
    }

    @Test
    public void testExecute_ParcelAlreadyExists_ReturnsErrorMessage() {
        when(commandValidator.validate(validCommand)).thenReturn(List.of());
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(new ParcelEntity()));

        String result = createParcelUserCommandService.execute(validCommand);

        assertEquals("Посылка не может быть создана: \nпосылка с названием parcel1 уже существует", result);
    }
}