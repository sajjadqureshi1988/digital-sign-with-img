package com.adcb;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class AddSignatureToPDF {
    public static void main(String[] args) {
        try {
            // Source PDF file
            String src = "source.pdf";
            // Output PDF file
            String dest = "signed_output.pdf";
            // Signature image file
            String imgPath = "signature.png";

            // Read the source PDF
            PdfReader reader = new PdfReader(src);
            Field f = reader.getClass().getDeclaredField("encrypted");
            f.setAccessible(true);
            f.set(reader, false);
            // Create the PdfStamper, which will create the new PDF
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

            // Load the image
            Image img = Image.getInstance(imgPath);

            // Set the position of the image (bottom-left corner)
            img.setAbsolutePosition(100, 100); // Change x and y to the desired coordinates
            // Scale the image to a desired size
            img.scaleToFit(100, 50); // Change width and height to the desired size

            int numberOfPages = reader.getNumberOfPages();
            for (int i = 1; i <= numberOfPages; i++) {
                // Set the position of the image (bottom-left corner)
                img.setAbsolutePosition(100, 100); // Change x and y to the desired coordinates

                // Get the content of the current page
                PdfContentByte content = stamper.getOverContent(i);

                // Add the image to the page
                content.addImage(img);
            }

            // Close the stamper
            stamper.close();
            // Close the reader
            reader.close();

            System.out.println("PDF signed successfully.");
        } catch (IOException | DocumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
