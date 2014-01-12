import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 08.03.13
 * Time: 13:09
 */
public interface PageDrawer {

    void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight);

    String getId();

}
