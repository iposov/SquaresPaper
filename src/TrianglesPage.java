import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 08.03.13
 * Time: 13:21
 */
public class TrianglesPage implements PageDrawer {
    @Override
    public void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        int steps = (int) Math.ceil(pageHeight / dist);

        for (int i = 0; i <= steps; i++) {
            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist);
            canvas.stroke();
        }

        for (int i = -2 * steps; i <= 3 * steps; i += 2) {
            float v = (float) (Math.sqrt(3) * pageWidth);

            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist + v);
            canvas.stroke();

            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist - v);
            canvas.stroke();
        }
    }

    @Override
    public String getId() {
        return "tr";
    }
}
