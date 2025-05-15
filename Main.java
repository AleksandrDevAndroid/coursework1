package ru.netology.graphics;

import ru.netology.graphics.image.Convert;
import ru.netology.graphics.image.Schema;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter convert = new Convert();
        convert.setMaxRatio(4);
        convert.setMaxWidth(300);
        convert.setMaxHeight(200);


        GServer server = new GServer(convert);
        server.start();

       /* String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
        String imgTxt = convert.convert(url);
        System.out.println(imgTxt);
        */
    }
}
