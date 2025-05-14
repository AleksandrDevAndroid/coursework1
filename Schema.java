package ru.netology.graphics.image;

public class Schema implements TextColorSchema{
    @Override
    public char convert(int color) {
        char[] symbols = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};
        return symbols[color * symbols.length / 256];
    }
}
