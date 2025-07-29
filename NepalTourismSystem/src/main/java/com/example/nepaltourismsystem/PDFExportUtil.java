package com.example.nepaltourismsystem;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import java.awt.image.BufferedImage;
import java.io.File;

public class PDFExportUtil {

    public static void exportNodeAsPdf(Node node, String outputPath) {
        try {
            // Take a snapshot of the node
            WritableImage fxImage = node.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);

            // Create PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(new PDRectangle(bufferedImage.getWidth(), bufferedImage.getHeight()));
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(LosslessFactory.createFromImage(document, bufferedImage), 0, 0);
            contentStream.close();

            document.save(new File(outputPath));
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
