import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 08.03.13
 * Time: 13:49
 */
public class HexagonsPageDrawer implements PageDrawer {
    @Override
    public void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        int steps = (int) Math.ceil(pageHeight / dist);

        float len = (float) (2 * dist / Math.sqrt(3));

        for (int i = 0; i <= steps; i += 2)
            drawLine(canvas, pageWidth, 0, i * dist, len, 0);

        for (int i = 1; i <= steps; i += 2)
            drawLine(canvas, pageWidth, len * 1.5f, i * dist, len, 0);

        for (int i = -2 * steps + 1; i <= 3 * steps; i += 2) {
            drawLine(canvas, pageWidth, -len / 2, i * dist, len / 2, dist);
            drawLine(canvas, pageWidth, -len / 2, i * dist, len / 2, - dist);

//            float len = (float) (Math.sqrt(3) * pageWidth);
//
//            canvas.moveTo(0, i * dist);
//            canvas.lineTo(pageWidth, i * dist + len);
//            canvas.stroke();
//
//            canvas.moveTo(0, i * dist);
//            canvas.lineTo(pageWidth, i * dist - len);
//            canvas.stroke();
        }
    }

    private void drawLine(PdfContentByte canvas, float pageWidth, float x0, float y0, float dx, float dy) {
        for (int i = 0; ; i += 3) {
            float x = x0 + i * dx;
            float y = y0 + i * dy;

            if (x > pageWidth)
                break;

            canvas.moveTo(x, y);
            canvas.lineTo(x + dx, y + dy);
            canvas.stroke();
        }
    }

    @Override
    public String getId() {
        return "hex";
    }
}
