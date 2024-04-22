package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Cooperativa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Controlador de la pantalla de mantenimiento de la cooperativa
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class MantenimientoCoopeController extends Controller implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistroAsociadoController.class);
    // private static final String RUTA_FOTO = "./src/main/resources/cr/ac/una/tareacooperativa/resources/";
    private static final String NOMBRE_FOTO = "logo_cooperativa";

    private Cooperativa cooperativa;
    private boolean isImageChanged;

    @FXML
    private ImageView imvLogo;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblNombreCoope;
    @FXML
    private MFXTextField txtfNombreCooperativa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        cooperativa = ((Cooperativa) AppContext.getInstance().get("cooperativa"));
        cooperativa.cargarDatosCooperativa();
        lblNombreCoope.setText(cooperativa.getNombreCooperativa());
    }

    /**
     * <p>
     * Control de evento drag over <br><br>
     * Detecta cuando hay un drag sobre la imagen. <br><br>
     * Crea un objeto dragBoard y valida si el drag <br>
     * contiene una imagen o un archivo. <br><br>
     * Si se valida, acepta las transferencias desde el evento <br>
     * y aplica un modo de transferencia COPY, para copiar el <br>
     * archivo del drag.
     * </p>
     *
     * @param event
     */
    @FXML
    private void onDragOverImageView(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles())
        {
            event.acceptTransferModes(TransferMode.COPY);
        }
    }

    /**
     * <p>
     * Control de evento drag dropped. <br><br>
     * Detecta cuando se suelta un drag sobre la imagen.<br><br>
     * Valida si el drag contiene una imagen o un archivo.<br><br>
     * Intenta settear la imagen de la ventana con la ruta del <br>
     * archivo del drag.<br><br>
     * </p>
     *
     * @param event
     */
    @FXML
    private void onDragDroppedImageView(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles())
        {
            try
            {
                imvLogo.setImage(new Image(new FileInputStream(dragboard.getFiles().get(0))));
                this.isImageChanged = true;
            } catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        if (!(txtfNombreCooperativa.getText().isEmpty()) || isImageChanged)
        {
            saveChanges();
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Usuario agregado", getStage(), "Los cambios se han efectuado existosamente");
            this.isImageChanged = false;
            this.txtfNombreCooperativa.setText("");

        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "No existen cambios que guardar");
        }
    }

    private void guardarFoto() {
        Image image = imvLogo.getImage();

        if (image != null)
        {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            try
            {
                String file = NOMBRE_FOTO + ".jpg";
                File output = new File(file);
                ImageIO.write(bufferedImage, "jpg", output);
                System.out.println("Imagen guardada correctamente en: " + output.getAbsolutePath());
                cooperativa.setLogoCooperativa(file);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        } else
        {
            System.out.println("No hay ninguna imagen para guardar.");
        }
    }

    private void saveName() {
        if (!txtfNombreCooperativa.getText().isEmpty())
        {
            cooperativa.setNombreCooperativa(txtfNombreCooperativa.getText());
        }
    }

    private void saveChanges() {
        guardarFoto();
        saveName();
        boolean cambiosCoope = cooperativa.getChanges();
        cooperativa.setChanges(!cambiosCoope);
        cooperativa.guardarDatosCooperativa();
    }

}
