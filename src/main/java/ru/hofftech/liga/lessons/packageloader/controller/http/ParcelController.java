package ru.hofftech.liga.lessons.packageloader.controller.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AddressController", description = "Контроллер с операциями по адресам клиентов")
@RequestMapping(path = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParcelController {
}
