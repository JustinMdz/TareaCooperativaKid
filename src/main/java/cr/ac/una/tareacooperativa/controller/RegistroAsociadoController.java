package cr.ac.una.tareacooperativa.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Justin Mendez && Stiward Araya
 */
public class RegistroAsociadoController extends Controller implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistroAsociadoController.class);
    private Webcam webcam;
    private boolean isRunning;

    private static final String RUTA_FOTOS = "./FotosAsociados/";

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView previewImageView;
    @FXML
    private ImageView fotoCapturada;

    private RegistroAsociado registroAsociado;

    @FXML
    private MFXTextField txtfNombre;
    @FXML
    private MFXTextField txtfApellidoP;
    @FXML
    private MFXTextField txtfEdad;
    @FXML
    private MFXTextField txtfApellidoS;
    @FXML
    private MFXButton btnPowerOn_OffCamera;
    @FXML
    private MFXButton btnTomarFoto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        limpiarCampos();
        controlCam();
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociado.cargarAsociados();
    }

    @FXML
    private void onActionBtnIniciarApagar(ActionEvent event) {
        if (!isRunning)
        {
            startCameraPreview();
            btnTomarFoto.setDisable(false);
        } else
        {
            isRunning = false;
            setNotUsingCameraImage("./src/main/resources/cr/ac/una/tareacooperativa/resources/banWebcam.png", previewImageView);
            webcam.close();
        }
    }

    @FXML
    private void onActionBtnTomarFoto(ActionEvent event) {
        try
        {
            BufferedImage image = webcam.getImage();
            Image javafxImage = SwingFXUtils.toFXImage(image, null);
            fotoCapturada.setImage(javafxImage);
        } catch (Exception e)
        {
            LOGGER.error("Error capturing image: {}", e.getMessage());
        }
    }

    @FXML
    public void onActionBtnNuevo(ActionEvent actionEvent) {
        if (txtfNombre.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir tu nombre");
        } else if (txtfApellidoP.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir tu primer apellido");
        } else if (txtfApellidoS.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir tu segundo apellido");
        } else if (txtfEdad.getText().isEmpty() || !(isNumber(txtfEdad.getText())))
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir tu edad");
        } else if (fotoCapturada.getImage() == null)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes tomarte una foto");
        } else
        {
            almacenarAsociado();
            limpiarCampos();
        }
    }

    @FXML
    public void onActionBtnCancelar(ActionEvent actionEvent) {
        limpiarCampos();
    }

    private void almacenarAsociado() {
        Asociado asociadoNuevo = new Asociado();
        asociadoNuevo.setNombre(txtfNombre.getText());
        asociadoNuevo.setPrimerApellido(txtfApellidoP.getText());
        asociadoNuevo.setSegundoApellido(txtfApellidoS.getText());
        asociadoNuevo.setEdad(Integer.parseInt(txtfEdad.getText()));
        asociadoNuevo.setFolioAsociado(asociadoNuevo.crearFolio());
        asociadoNuevo.setRutaFoto(RUTA_FOTOS + asociadoNuevo.getFolio() + ".jpg");
        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Usuario agregado", getStage(), registroAsociado.agregarAsociado(asociadoNuevo)
                + ", Tu folio es: " + asociadoNuevo.getFolio());
        registroAsociado.guardarAsociados();
        guardarFoto(asociadoNuevo.getRutaFoto());
    }

    private void startCameraPreview() {
        webcam.open();
        isRunning = true;
        Thread previewThread = new Thread(() ->
        {
            try
            {
                while (isRunning)
                {
                    BufferedImage image = webcam.getImage();
                    Image javafxImage = SwingFXUtils.toFXImage(image, null);
                    previewImageView.setImage(javafxImage);
                    Thread.sleep(17);
                }
            } catch (InterruptedException e)
            {
                LOGGER.error("Error in camera preview thread: {}", e.getMessage());
            }
        });
        previewThread.setDaemon(true);
        previewThread.start();
    }

    private void controlCam() {
        try
        {
            webcam = Webcam.getDefault();
            this.webcam.close();
            if (webcam != null)
            {
                webcam.setViewSize(new Dimension(WebcamResolution.VGA.getWidth(), WebcamResolution.VGA.getHeight()));
                isRunning = false;
            } else
            {
                LOGGER.error("No se encontró una cámara disponible en el sistema.");
                btnPowerOn_OffCamera.setDisable(true);
                btnTomarFoto.setDisable(true);
            }
        } catch (Exception e)
        {
            LOGGER.error("Error al inicializar la cámara: {}", e.getMessage());
            btnPowerOn_OffCamera.setDisable(true);
            btnTomarFoto.setDisable(true);
        }
    }

    public void guardarFoto(String folio) {
        try
        {
            Image image = fotoCapturada.getImage();
            File directorio = new File(RUTA_FOTOS);
            if (!directorio.exists())
            {
                directorio.mkdirs();
            }
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            File outputFile = new File(folio);
            ImageIO.write(bufferedImage, "png", outputFile);
            LOGGER.info("Imagen guardada exitosamente en: {}", folio);
        } catch (IOException e)
        {
            LOGGER.error("Error al guardar la imagen: {}", e.getMessage());
        }
    }

    private boolean isNumber(String text) {
        try
        {
            int numero = Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    private void limpiarCampos() {
        txtfNombre.setText("");
        txtfApellidoP.setText("");
        txtfApellidoS.setText("");
        txtfEdad.setText("");
        fotoCapturada.setImage(null);
        previewImageView.setImage(null);
        isRunning = false;
        setNotUsingCameraImage("./src/main/resources/cr/ac/una/tareacooperativa/resources/banWebcam.png", previewImageView);
        btnTomarFoto.setDisable(true);
    }

    private void setNotUsingCameraImage(String rutaFoto, ImageView imageView) {
        try
        {
            File archivo = new File(rutaFoto);
            if (archivo.exists())
            {
                Image imagen = new Image(archivo.toURI().toString());
                imageView.setImage(imagen);
            } else
            {
                imageView.setImage(null);
            }
        } catch (Exception e)
        {
            // Maneja la excepción de forma adecuada, por ejemplo, mostrando un mensaje de error
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            // O simplemente no hagas nada y deja que el ImageView mantenga su estado actual
        }
    }

}
