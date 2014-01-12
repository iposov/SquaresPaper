import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 29.04.13
 * Time: 20:59
 */
public class RhombusPage implements PageDrawer {
    @Override
    public void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        dist *= Math.sqrt(2);

        int times = (int) Math.ceil(pageHeight / dist);

        for (int i = -times; i <= 2 * times; i++) {
            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist + pageWidth);
            canvas.stroke();

            canvas.moveTo(0, i * dist);
            canvas.lineTo(pageWidth, i * dist - pageWidth);
            canvas.stroke();
        }
    }

    @Override
    public String getId() {
        return "rh";
    }
}
