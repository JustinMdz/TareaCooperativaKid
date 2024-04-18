package cr.ac.una.tareacooperativa.model;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * Clase controladora de pdf's, contiene todos las funciones <br>
 * para crear y generar un pdf.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class PdfManager {

    private static final String BASE_PDF = "BaseCarnet.pdf";
    private static final String DIRECTORY = "cr/ac/una/tareacooperativa/resources/";
    private static final String DIRECTORY_REGISTRO = "./PdfAsociados/";

    private Image userImage;
    private Image coopeImage;
    private String folioSocio;
    private String nombreSocio;
    private String primerApellido;
    private String segundoApellido;
    private String nombreCooperativa;

    public PdfManager(Asociado asociado, Cooperativa cooperativa) throws IOException {
        this.folioSocio = asociado.getFolio();
        this.nombreSocio = asociado.getNombre();
        this.primerApellido = asociado.getPrimerApellido();
        this.segundoApellido = asociado.getSegundoApellido();
        this.nombreCooperativa = cooperativa.getNombreCooperativa();
        this.coopeImage = cargarImagen(cooperativa.getLogoCooperativa());
        this.userImage = cargarImagen(asociado.getRutaFoto());
    }

    private Image cargarImagen(String rutaImagen) throws IOException {
        Image imagen = null;
        if (rutaImagen != null && !rutaImagen.isEmpty())
        {
            File archivo = new File(rutaImagen);
            if (archivo.exists())
            {
                imagen = new Image(ImageDataFactory.create(rutaImagen));
            } else
            {
                System.err.println("El archivo no existe en la ubicación especificada: " + rutaImagen);
            }
        }
        return imagen;
    }

    private void addText(Document document) throws IOException {

        // Crear una fuente
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        // Agregar texto al documento en las coordenadas especificadas
        Paragraph primerApellidoSocioParagraph = new Paragraph(checkStrings(primerApellido)).setFont(font).setFixedPosition(104, 98, 100);
        Paragraph segundoApellidoSocioParagraph = new Paragraph(checkStrings(segundoApellido)).setFont(font).setFixedPosition(168, 98, 100);
        Paragraph nombreSocioParagraph = new Paragraph(checkStrings(nombreSocio)).setFont(font).setFixedPosition(137, 61, 100);
        Paragraph folioSocioParagraph = new Paragraph(checkStrings(folioSocio)).setFont(font).setFixedPosition(138, 29, 100);
        Paragraph noombreCooperativaParagraph = new Paragraph(checkStrings(nombreCooperativa)).setFont(font).setFixedPosition(24, 24, 100);
        noombreCooperativaParagraph.setFontColor(Color.WHITE);
        noombreCooperativaParagraph.setBold();
        noombreCooperativaParagraph.setFontSize(9);
        folioSocioParagraph.setFontSize(7);
        nombreSocioParagraph.setFontSize(9);
        segundoApellidoSocioParagraph.setFontSize(9);
        primerApellidoSocioParagraph.setFontSize(9);

        document.add(nombreSocioParagraph);
        document.add(primerApellidoSocioParagraph);
        document.add(folioSocioParagraph);
        document.add(segundoApellidoSocioParagraph);
        document.add(noombreCooperativaParagraph);

        if (userImage != null)
        {
            userImage.scaleAbsolute(65, 55);
            userImage.setFixedPosition(23, 50);
            document.add(userImage);
        }

        if (coopeImage != null)
        {
            coopeImage.scaleToFit(20, 20);
            coopeImage.setFixedPosition(211, 19);
            document.add(coopeImage);
        }
    }

    private String checkStrings(String data) {
        if (data.isEmpty() || data == null)
        {
            return "";
        }
        return data;

    }

    public String crearPDF() {

        try
        {
            File directorio = new File(DIRECTORY_REGISTRO);
            if (!directorio.exists())
            {
                directorio.mkdirs();
            }
            // Cargar el documento PDF existente
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(DIRECTORY + BASE_PDF), new PdfWriter(DIRECTORY_REGISTRO + folioSocio + ".pdf"));

            // Obtener la primera página del documento
            PdfPage page = pdfDocument.getFirstPage();

            // Crear un objeto Document para escribir sobre la página
            Document document = new Document(pdfDocument);
            addText(document);
            document.close();

            // Obtener el objeto Desktop
            Desktop desktop = Desktop.getDesktop();

            // Crear un objeto File con el nombre correcto del archivo PDF
            File file = new File(DIRECTORY_REGISTRO + folioSocio + ".pdf");

            // Abrir el archivo con el programa predeterminado
            desktop.open(file);

            return "PDF creado exitosamente.";
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
