package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Convert implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;




    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        if(schema == null){
            schema = new Schema();
        }

        if (img == null) {
            throw new IOException("Отсутствует изображение.");
        }

        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();

        if (originalHeight <= 0 || originalWidth <= 0) {
            throw new ArithmeticException("Некорректные размеры изображения.");
        }


        double ratio = (double) originalWidth / originalHeight;
            if (maxRatio > 0 && ratio > maxRatio) {
                throw new BadImageSizeException(maxRatio, ratio);
            }

        //TODO resize
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        if (originalHeight > height || originalWidth > width) {
            double scale = (double) Math.max(img.getHeight(), img.getWidth()) / Math.max(width, height);
            double corHeight = originalHeight / scale;
            double corWidth = originalWidth / scale;
            newHeight = (int) Math.round(corHeight);
            newWidth = (int) Math.round(corWidth);
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder sb = new StringBuilder();
        char[][] symbols = new char[bwRaster.getHeight()][bwRaster.getWidth()];
        int[] arrayPixel = new int[1];
        for (int w = 0; w < bwRaster.getWidth(); w++) {
            for (int h = 0; h < bwRaster.getHeight(); h++) {
                int color = bwRaster.getPixel(w, h, arrayPixel)[0];
                char c = schema.convert(color);
                symbols[h][w] = c;
            }
        }

        for (int i = 0; i < symbols.length; i++) {
            for (int j = 0; j < symbols[i].length; j++) {
                sb.append(symbols[i][j]);
                sb.append(symbols[i][j]);
            }
            sb.append("\n");
        }
        return String.valueOf(sb);
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = new Schema();
    }
}

