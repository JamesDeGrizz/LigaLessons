package ru.hofftech.liga.lessons.packageloader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Представляет посылку с его содержимым, именем, символом и точками размещения.
 * Этот класс предоставляет методы для взаимодействия со свойствами посылки и для её размещения.
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parcel {
    private final List<String> content;
    private final String name;
    private final char symbol;

    private List<PlacingPoint> placingPoints = null;

    /**
     * Возвращает символ в указанной строке и столбце содержимого посылки.
     *
     * @param row    индекс строки
     * @param column индекс столбца
     * @return символ в указанной позиции
     */
    public char charAt(int row, int column) {
        return content.get(row).charAt(column);
    }

    /**
     * Возвращает ширину содержимого посылки.
     * Ширина определяется длиной самой длинной строки в содержимом.
     *
     * @return ширина содержимого посылки
     */
    public int getWidth() {
        if (content.isEmpty()) {
            return 0;
        }
        return content.stream().
                max(Comparator.comparing(String::length))
                .get()
                .length();
    }

    /**
     * Возвращает высоту содержимого посылки.
     * Высота определяется количеством строк в содержимом.
     *
     * @return высота содержимого посылки
     */
    public int getHeight() {
        return content.size();
    }

    /**
     * Возвращает размер посылки.
     *
     * @return размер посылки
     */
    public int getSize() {
        return Optional.of(content.stream()
                .mapToInt(contentString -> contentString.length())
                .sum())
                .orElse(0);
    }

    /**
     * Проверяет, размещена ли посылка.
     *
     * @return true, если посылка размещена, false в противном случае
     */
    public boolean placed() {
        return placingPoints != null;
    }

    /**
     * Размещает посылку в указанных точках размещения.
     *
     * @param placingPoints список точек размещения
     */
    public void placeParcel(List<PlacingPoint> placingPoints) {
        this.placingPoints = placingPoints;
    }

    /**
     * Возвращает строковое представление посылки.
     *
     * @return строковое представление посылки
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("\r\n")
                .append("Name: ")
                .append(name)
                .append("\r\n")
                .append("Form: ")
                .append("\r\n");
        for (var line : content) {
            builder.append(line.replaceAll("\\S", String.valueOf(symbol)))
                    .append("\r\n");
        }
        builder.append("Symbol: ")
                .append(symbol)
                .append("\r\n");
        return builder.toString();
    }
}
