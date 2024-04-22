package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Cooperativa;
import static cr.ac.una.tareacooperativa.model.Cooperativa.ARCHIVO_COOPERATIVA;
import static cr.ac.una.tareacooperativa.model.Cooperativa.DIRECTORY;
import cr.ac.una.tareacooperativa.model.HTMLOpener;
import cr.ac.una.tareacooperativa.util.AppContext;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

import cr.ac.una.tareacooperativa.util.FlowController;
import cr.ac.una.tareacooperativa.util.Mensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * Universidad Nacional de Costa Rica - Programación II 2024
 * <p>
 * Controlador de la pantalla principal de asociados<br>
 * Contiene los metodos para mostrar las ventanas de asociados.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class AsociadoController extends Controller implements Initializable {

    private Cooperativa cooperativa;
    private HTMLOpener openerWebView;
    @FXML
    private ImageView imvLogoCoope;
    @FXML
    private javafx.scene.control.Label lblNombreCoope;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        inicioCooperativaInfo();
    }

    @FXML
    public void onActionBtnRegistro(ActionEvent actionEvent) {
        FlowController.getInstance().goView("RegistroAsociadoView");
        setCooperativaInfo();
    }

    @FXML
    private void onActionBtnEstadoCuenta(ActionEvent event) {
        FlowController.getInstance().goView("AsociadoCuentasView");
        setCooperativaInfo();
    }

    @FXML
    private void onActionBtnBuzon(ActionEvent event) {
        FlowController.getInstance().goView("BuzonView");
        setCooperativaInfo();
    }

    @FXML
    public void onActionBtnSalir(ActionEvent actionEvent) {
        FlowController.getInstance().salir();
    }

    @FXML
    public void onActionLinkAcercaDe(ActionEvent actionEvent) throws IOException {
        String rutaHtml = "src/main/web/index.html";
        openerWebView = new HTMLOpener();
        openerWebView.abrirArchivoHTML(rutaHtml);

        //    FlowController.getInstance().goView("webView");
    }

    private void cargarDatosCooperativa() {
        cooperativa = ((Cooperativa) AppContext.getInstance().get("cooperativa"));
        cooperativa.cargarDatosCooperativa();
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
                        javafx.scene.image.Image img = new javafx.scene.image.Image(archivo.toURI().toString());
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

    private void inicioCooperativaInfo() {
        cooperativa = new Cooperativa();
        cargarDatosCooperativa();
        if (checkExistentData(DIRECTORY + ARCHIVO_COOPERATIVA))
        {
            setCooperativaInfo();
        }
    }

}
