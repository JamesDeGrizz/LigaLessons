package ru.hofftech.liga.lessons.telegramclient.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ParcelDto(@NotBlank String name,
                        @NotBlank String form,
                        @NotBlank
                        @Size(min = 1)
                        @Size(max = 1)
                        String symbol) {

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Посылка ")
                .append(name)
                .append("\nформа ")
                .append(form)
                .append("\nсимвол ")
                .append(symbol)
                .toString();
    }
}
