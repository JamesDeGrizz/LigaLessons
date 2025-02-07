package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {
    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private ParcelMapper parcelMapper;

    @InjectMocks
    private ParcelService parcelService;

    private ParcelDto validDto;
    private ParcelEntity validEntity;

    @BeforeEach
    public void setUp() {
        validDto = ParcelDto.builder()
                .name("parcel1")
                .form("A,B,C")
                .symbol("X")
                .build();

        validEntity = ParcelEntity.builder()
                .id(1L)
                .name("parcel1")
                .content("A,B,C")
                .symbol('X')
                .build();
    }

    @Test
    public void testCreate_Success() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());
        when(parcelMapper.toParcelEntity(validDto)).thenReturn(validEntity);
        when(parcelRepository.save(any(ParcelEntity.class))).thenReturn(validEntity);
        when(parcelMapper.toParcelDto(validEntity)).thenReturn(validDto);

        ParcelDto result = parcelService.create(validDto);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("parcel1");
        verify(parcelRepository, times(1)).save(any(ParcelEntity.class));
    }

    @Test
    public void testCreate_ParcelAlreadyExists_ThrowsException() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(validEntity));

        assertThatThrownBy(() -> parcelService.create(validDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка с названием parcel1 уже существует");
    }

    @Test
    public void testDelete_Success() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(validEntity));

        parcelService.delete("parcel1");

        verify(parcelRepository, times(1)).delete(validEntity);
    }

    @Test
    public void testDelete_ParcelNotFound_ThrowsException() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parcelService.delete("parcel1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка с названием parcel1 не найдена");
    }

    @Test
    public void testEdit_Success() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(validEntity));
        when(parcelRepository.save(any(ParcelEntity.class))).thenReturn(validEntity);

        parcelService.edit("parcel1", validDto);

        verify(parcelRepository, times(1)).save(any(ParcelEntity.class));
        assertThat(validEntity.getName()).isEqualTo("parcel1");
        assertThat(validEntity.getContent()).isEqualTo("A,B,C");
        assertThat(validEntity.getSymbol()).isEqualTo('X');
    }

    @Test
    public void testEdit_ParcelNotFound_ThrowsException() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parcelService.edit("parcel1", validDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка с названием parcel1 не существует");
    }

    @Test
    public void testFindAll_Success() {
        List<ParcelEntity> mockEntities = List.of(validEntity);
        var pageable = PageRequest.of(0, 10);
        Page<ParcelEntity> page = new PageImpl(mockEntities);
        when(parcelRepository.findAll(pageable)).thenReturn(page);
        when(parcelMapper.toParcelDto(validEntity)).thenReturn(validDto);

        List<ParcelDto> result = parcelService.findAll(0, 10);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("parcel1");
        verify(parcelRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFind_Success() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.of(validEntity));
        when(parcelMapper.toParcelDto(validEntity)).thenReturn(validDto);

        ParcelDto result = parcelService.find("parcel1");

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("parcel1");
        verify(parcelRepository, times(1)).findByName("parcel1");
    }

    @Test
    public void testFind_ParcelNotFound_ThrowsException() {
        when(parcelRepository.findByName("parcel1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parcelService.find("parcel1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка с названием parcel1 не существует");
    }
}