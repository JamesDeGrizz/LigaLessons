package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hofftech.liga.lessons.packageloader.config.BillingConfiguration;
import ru.hofftech.liga.lessons.packageloader.mapper.OrderMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BillingConfiguration billingConfiguration;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private BillingService billingService;

    @Test
    void saveOrder_ShouldCalculatePriceCorrectlyForLoadCommand() {
        // Arrange
        BillingConfiguration.Pricing pricing = mock(BillingConfiguration.Pricing.class);
        when(billingConfiguration.pricing()).thenReturn(pricing);
        when(pricing.load()).thenReturn(10);

        List<Parcel> parcels = List.of(
                new Parcel(List.of("XX"), "тип 1", 'C', null),
                new Parcel(List.of("XXX"), "тип 1", 'C', null)
        );

        // Act
        billingService.saveOrder("user123", Command.LOAD_PARCELS, 2, parcels);

        // Assert
        verify(orderMapper).toOrderEntity(argThat(order ->
                order.name().equals("user123") &&
                        order.operation().equals("Погрузка") &&
                        order.trucksCount() == 2 &&
                        order.parcelsCount() == 2 &&
                        order.totalPrice() == (2+3)*10
        ));

        verify(orderRepository).save(any());
    }

    @Test
    void saveOrder_ShouldCalculatePriceCorrectlyForUnloadCommand() {
        // Arrange
        BillingConfiguration.Pricing pricing = mock(BillingConfiguration.Pricing.class);
        when(billingConfiguration.pricing()).thenReturn(pricing);
        when(pricing.unload()).thenReturn(8);

        List<Parcel> parcels = List.of(new Parcel(List.of("XXXXX"), "тип 1", 'C', null));

        // Act
        billingService.saveOrder("user456", Command.UNLOAD_TRUCKS, 1, parcels);

        // Assert
        verify(orderMapper).toOrderEntity(argThat(order ->
                order.totalPrice() == 5*8 && // 5*8 = 40
                        order.operation().equals("Разгрузка")
        ));
    }

    @Test
    void saveOrder_ShouldHandleEmptyParcelsList() {
        // Arrange
        BillingConfiguration.Pricing pricing = mock(BillingConfiguration.Pricing.class);
        when(billingConfiguration.pricing()).thenReturn(pricing);
        when(pricing.load()).thenReturn(15);

        // Act
        billingService.saveOrder("user000", Command.LOAD_PARCELS, 3, List.of());

        // Assert
        verify(orderMapper).toOrderEntity(argThat(order ->
                order.totalPrice() == 0 &&
                        order.parcelsCount() == 0
        ));
    }
}