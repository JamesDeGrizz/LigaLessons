package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.telegramclient.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.telegramclient.model.enums.Command;

@AllArgsConstructor
@Service
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final ParcelLoaderClient parcelLoaderClientService;
    private final ParcelService parcelService;
    private final BillingService billingService;
    private final LogisticService logisticService;

    public String processRawInput(String userInput) {
        try {
            var command = userCommandParserService.parse(userInput);
            if (command == null) {
                return "Введена нераспознанная команда. Попробуйте ещё раз";
            }

            var args = command.getArgs();
            return switch (command.getCommand()) {
                case Command.CREATE_PARCEL -> parcelService.createParcel(
                        ParcelDto.builder()
                                .name(userCommandParserService.getName(args))
                                .form(userCommandParserService.getForm(args))
                                .symbol(userCommandParserService.getSymbol(args))
                        .build()).toString();
                case Command.FIND_PARCEL -> parcelService.findParcel(userCommandParserService.getName(args));
                case Command.EDIT_PARCEL -> parcelService.updateParcel(
                        userCommandParserService.getTargetParcelName(args),
                        ParcelDto.builder()
                                .name(userCommandParserService.getName(args))
                                .form(userCommandParserService.getForm(args))
                                .symbol(userCommandParserService.getSymbol(args))
                                .build());
                case Command.DELETE_PARCEL -> parcelService.deleteParcel(userCommandParserService.getName(args));
                case Command.LOAD_PARCELS -> logisticService.load(userCommandParserService.getLoadParcelsRequestDto(args));
                case Command.UNLOAD_TRUCKS -> logisticService.unload(userCommandParserService.getUnloadTrucksRequestDto(args));
                case Command.SHOW_ORDERS -> billingService.getOrders(userCommandParserService.getUserId(args));
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
