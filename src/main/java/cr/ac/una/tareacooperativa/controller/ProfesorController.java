package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Cooperativa;
import static cr.ac.una.tareacooperativa.model.Cooperativa.ARCHIVO_COOPERATIVA;
import static cr.ac.una.tareacooperativa.model.Cooperativa.DIRECTORY;
import cr.ac.una.tareacooperativa.model.HTMLOpener;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.FlowController;
import cr.ac.una.tareacooperativa.util.Mensaje;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Universidad Nacional de Costa Rica - Programación II 2024
 * <p>
 * Controlador de la pantalla de profesores
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class ProfesorController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    private Cooperativa cooperativa;
    private HTMLOpener openerWebView;
    @FXML
    private ImageView imvLogoCoope;
    @FXML
    private Label lblNombreCoope;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cooperativa = new Cooperativa();
        cargarDatosCooperativa();
        if (checkExistentData(DIRECTORY + ARCHIVO_COOPERATIVA))
        {
            setCooperativaInfo();
        }

    }

    @Override
    public void initialize() {
    }

    private void cargarDatosCooperativa() {
        cooperativa = ((Cooperativa) AppContext.getInstance().get("cooperativa"));
        cooperativa.cargarDatosCooperativa();
    }

    @FXML
    private void onActionBtnCuentas(ActionEvent event) {
        setCooperativaInfo();
        FlowController.getInstance().goView("MantenimientoCuentasView");
    }

    @FXML
    private void onActionBtnCooperativa(ActionEvent event) {
        setCooperativaInfo();
        FlowController.getInstance().goView("MantenimientoCopeeView");
    }

    @FXML
    public void onActionLinkAcercaDe(ActionEvent actionEvent) {
        String rutaHtml = "src/main/web/index.html";
        openerWebView = new HTMLOpener();
        openerWebView.abrirArchivoHTML(rutaHtml);
    }

    private void setCooperativaInfo() {
        cargarDatosCooperativa();
        String rutaCoope = cooperativa.getLogoCooperativa();
        String nombreCoope = cooperativa.getNombreCooperativa();
        if (rutaCoope != null)
        {
            cargarImagenCooperativa(rutaCoope);
        }
        if (nombreCoope != null)
        {
            this.lblNombreCoope.setText(nombreCoope);
        }
    }

    private void cargarImagenCooperativa(String ruta) {
        if (checkExistentData(ruta))
        {
            try
            {
                if (imvLogoCoope != null)
                {
                    File archivo = new File(ruta);
                    if (archivo.exists())
                    {
                        Image img = new Image(archivo.toURI().toString());
                        imvLogoCoope.setImage(img);
                    } else
                    {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error ,Archivo no encontrado", getStage(), "Error al cargar la imagen de la cooperativa | Ruta no encontrada");
                        imvLogoCoope.setImage(null);
                    }
                }
            } catch (Exception e)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al cargar la imagen", getStage(), "Ocurrió un error al intentar cargar la imagen de la cooperativa.");
                imvLogoCoope.setImage(null);
            }
        }
    }

    private boolean checkExistentData(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists() || archivo.length() == 0)
        {
            return false;
        } else
        {
            return true;
        }
    }
}
