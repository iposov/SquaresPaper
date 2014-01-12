import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 08.03.13
 */
public class SquaresPage implements PageDrawer {
    @Override
    public void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        for (int i = 0; i <= Math.ceil(pageWidth / dist); i++) {
            canvas.moveTo(i * dist, 0);
            canvas.lineTo(i * dist, pageHeight);
            canvas.stroke();
        }

        for (int i = 0; i <= Math.ceil(pageHeight / dist); i++) {
            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist);
            canvas.stroke();
        }
    }

    @Override
    public String getId() {
        return "sq";
    }
}
