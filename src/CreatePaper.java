import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 21.02.13
 * Time: 22:42
 */
public class CreatePaper {

    public static void main(String[] args) throws IOException, DocumentException {
        PageDrawer[] drawers = {
                new SquaresPage(),
                new TrianglesPage(),
                new RhombusPage(),
                new HexagonsPage(),
                new HexagonsPage(HexagonsPage.TRIANGLES_MAIN),
                new HexagonsPage(HexagonsPage.TRIANGLES_SECONDARY)
        };

        for (PageDrawer drawer : drawers)
            for (int mm = 1; mm <= 6; mm++)
                for (int gray = 0x00; gray <= 0xBB; gray += 0x44)
                    createPaper(mm, gray, drawer);
    }

    private static void createPaper(int mms, int gray, PageDrawer drawer) throws DocumentException, IOException {
        Rectangle pageSize = new Rectangle(
                Utilities.millimetersToPoints(210), Utilities.millimetersToPoints(297)
        );
        Document doc = new Document(pageSize, 0, 0, 0, 0);

        Path outputFolder = Path.of("pdfs");
        String fileName = String.format("%s-%dmm-%02Xgray.pdf", drawer.getId(), mms, gray);
        Path outputFile = outputFolder.resolve(fileName);

        Files.createDirectories(outputFolder);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outputFile.toFile()));

        doc.open();

        createPage(mms, gray, pageSize, writer, drawer);

        doc.newPage();

        createPage(mms, gray, pageSize, writer, drawer);

        doc.close();
    }

    private static void createPage(int mms, int gray, Rectangle pageSize, PdfWriter writer, PageDrawer drawer) {
        PdfContentByte canvas = writer.getDirectContent();

        canvas.saveState();

        canvas.setLineWidth(0.05f);
        canvas.setRGBColorStroke(gray, gray, gray);

        float dist = Utilities.millimetersToPoints(mms);

        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();

        drawer.drawPage(canvas, dist, pageWidth, pageHeight);

        canvas.restoreState();
    }

}
