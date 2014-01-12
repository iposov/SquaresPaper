import com.itextpdf.text.pdf.PdfContentByte;

/**
 * Created with IntelliJ IDEA.
 * User: ilya
 * Date: 08.03.13
 * Time: 13:49
 */
public class HexagonsPage implements PageDrawer {
    public static final int NO_TRIANGLES = 0;
    public static final int TRIANGLES_MAIN = 1;
    public static final int TRIANGLES_SECONDARY = 2;

    public static final int SECONDARY_COLOR = 0xBB;

    private final int triangles;

    public HexagonsPage() {
        triangles = NO_TRIANGLES;
    }

    public HexagonsPage(int triangles) {
        this.triangles = triangles;
    }

    @Override
    public void drawPage(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        //draw hexagons

        if (triangles == TRIANGLES_MAIN) {
            canvas.saveState();
            canvas.setRGBColorStroke(SECONDARY_COLOR, SECONDARY_COLOR, SECONDARY_COLOR);
        }

        drawHexagons(canvas, dist, pageWidth, pageHeight);

        if (triangles == TRIANGLES_MAIN)
            canvas.restoreState();

        //draw triangles

        if (triangles != NO_TRIANGLES) {
            if (triangles == TRIANGLES_SECONDARY) {
                canvas.saveState();
                canvas.setRGBColorStroke(SECONDARY_COLOR, SECONDARY_COLOR, SECONDARY_COLOR);
            }

            drawTriangles(canvas, dist, pageWidth, pageHeight);

            if (triangles == TRIANGLES_SECONDARY)
                canvas.restoreState();
        }
    }

    private void drawHexagons(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        int steps = (int) Math.ceil(pageHeight / dist);

        float len = (float) (2 * dist / Math.sqrt(3));

        for (int i = 0; i <= steps; i += 2)
            drawLine(canvas, pageWidth, 0, i * dist, len, 0);

        for (int i = 1; i <= steps; i += 2)
            drawLine(canvas, pageWidth, len * 1.5f, i * dist, len, 0);

        for (int i = -2 * steps + 1; i <= 3 * steps; i += 2) {
            drawLine(canvas, pageWidth, -len / 2, i * dist, len / 2, dist);
            drawLine(canvas, pageWidth, -len / 2, i * dist, len / 2, - dist);
        }
    }

    private void drawTriangles(PdfContentByte canvas, float dist, float pageWidth, float pageHeight) {
        int steps = (int) Math.ceil(pageHeight / dist);
        float len = dist * (float) Math.sqrt(3);

        for (int i = 0; i <= steps; i++) {
            float x0 = (i + 1 / 3f) * len;
            canvas.moveTo(x0, 0);
            canvas.lineTo(x0, pageHeight);
            canvas.stroke();
        }

        for (int i = -steps; i <= 2 * steps; i++) {
            float x0 = 1 / 3f * len - 2 * len;
            float y0 = (i + 0.5f) * 2 * dist;
            canvas.moveTo(x0, y0);
            canvas.lineTo(x0 + pageWidth + 2 * len, y0 + (pageWidth + 2 * len) / (float) Math.sqrt(3));
            canvas.stroke();

            canvas.moveTo(x0, y0);
            canvas.lineTo(x0 + pageWidth + 2 * len, y0 - (pageWidth + 2 * len) / (float) Math.sqrt(3));
            canvas.stroke();
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
        switch (triangles) {
            case NO_TRIANGLES:
                return "hex";
            case TRIANGLES_MAIN:
                return "trh";
            case TRIANGLES_SECONDARY:
                return "htr";
        }
        return "?";
    }
}
