package ru.hofftech.liga.lessons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Package {
    private int maxWidth;
    private int maxHeight;
    private String content;

    public boolean valid() {
        return maxWidth > 0 && maxHeight > 0 && content != null && !content.isEmpty() &&
                (maxWidth * maxHeight == content.length() || maxWidth * maxHeight == (content.length() + 1)); // потому что чёртовы семёрки
    }
}
