package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Cooperativa;
import static cr.ac.una.tareacooperativa.model.Cooperativa.ARCHIVO_COOPERATIVA;
import static cr.ac.una.tareacooperativa.model.Cooperativa.DIRECTORY;
import cr.ac.una.tareacooperativa.model.HTMLOpener;
import cr.ac.una.tareacooperativa.util.AppContext;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.util.FlowController;
import cr.ac.una.tareacooperativa.util.Mensaje;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Universidad Nacional de Costa Rica - Programación II 2024
 * <p>
 * Controlador de la pantalla principal de funcionarios<br>
 * Contiene los metodos para mostrar las ventanas de funcionarios
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class FuncionarioController extends Controller implements Initializable {

    private HTMLOpener openerWebView;
    private Cooperativa cooperativa;
    @FXML
    private BorderPane root;
    @FXML
    private ImageView imvLogoCoope;
    @FXML
    private Label lblNombreCoope;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cooperativa = new Cooperativa();
        setCooperativaInfo();
    }

    @Override
    public void initialize() {
    }

    @FXML
    public void onActionAsociadosButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("MantenimientoAsociadosView");
        setCooperativaInfo();
    }

    @FXML
    public void onActionCarnetButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("ImpresionCarnetView");
        setCooperativaInfo();
    }

    @FXML
    public void onActionCuentasButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("CuentasView");
        setCooperativaInfo();
    }

    @FXML
    public void onActionTransaccionesButton(ActionEvent actionEvent) {
        FlowController.getInstance().goView("TransaccionesView");
        setCooperativaInfo();
    }

    @FXML
    private void onActionSalirButton(ActionEvent event) throws IOException {
        FlowController.getInstance().salir();
    }

    @FXML
    public void onActionLinkAcercaDe(ActionEvent actionEvent) {
        openerWebView = new HTMLOpener();
        openerWebView.abrirArchivoHTML();
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

}
