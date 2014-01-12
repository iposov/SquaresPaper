import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 21.02.13
 * Time: 22:42
 */
public class CreatePaper {

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
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
                for (int gray = 0x88; gray <= 0x88; gray += 0x22)
                    createPaper(mm, gray, drawer);
    }

    private static void createPaper(int mms, int gray, PageDrawer drawer) throws DocumentException, FileNotFoundException {
        Rectangle pageSize = new Rectangle(
                Utilities.millimetersToPoints(210), Utilities.millimetersToPoints(297)
        );
        Document doc = new Document(pageSize, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(drawer.getId() + "-" + mms + "mm-" + Integer.toHexString(gray) + ".pdf"));

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
