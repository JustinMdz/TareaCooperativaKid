package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import cr.ac.una.tareacooperativa.util.Mensaje;

/**
 * <p>
 * Controlador de la pantalla de mantenimiento de asociados<br>
 * Contiene los metodos para el mantenimiento de asociados
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class MantenimientoAsociadosController extends Controller implements Initializable {

    RegistroAsociado registroAsociado;
    @javafx.fxml.FXML
    private BorderPane root;
    @javafx.fxml.FXML
    private MFXTextField txtfNombre;
    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private MFXButton mbtnModificar;
    @javafx.fxml.FXML
    private ImageView imvFoto;
    @javafx.fxml.FXML
    private MFXButton mbtnEliminar;
    @javafx.fxml.FXML
    private MFXButton mbtnBuscar;
    @javafx.fxml.FXML
    private MFXTextField txtfPApellido;
    @javafx.fxml.FXML
    private MFXTextField txtfSApellido;
    @javafx.fxml.FXML
    private MFXTextField txtfEdad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        limpiarCampos();
        setRegistros();
        setBotones(true);
        setFormFields(true);
    }

    @javafx.fxml.FXML
    public void onActionBtnModificar(ActionEvent actionEvent) {
        if (txtfNombre.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir un nombre");
        } else if (txtfPApellido.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir un primer apellido");
        } else if (txtfSApellido.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir un segundo apellido");
        } else if (txtfEdad.getText().isEmpty() || !(isNumber(txtfEdad.getText())))
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir tu edad");
        } else
        {
            Asociado asociado = new Asociado();
            asociado.setFolioAsociado(txtfFolio.getText());
            asociado.setNombre(txtfNombre.getText());
            asociado.setPrimerApellido(txtfPApellido.getText());
            asociado.setSegundoApellido(txtfSApellido.getText());
            asociado.setEdad(Integer.parseInt(txtfEdad.getText()));
            asociado.setRutaFoto(registroAsociado.buscarAsociado(txtfFolio.getText().toUpperCase()).getRutaFoto());
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Modificar usuario", getStage(), registroAsociado.modificarAsociado(asociado));
            registroAsociado.guardarAsociados();
            onActionBtnLimpiar(actionEvent);
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        txtfFolio.setText(txtfFolio.getText().toUpperCase());
        Asociado asociado = registroAsociado.buscarAsociado(txtfFolio.getText());
        if (asociado != null)
        {
            cargarImagenAsociado(asociado.getRutaFoto());
            txtfNombre.setText(asociado.getNombre());
            txtfPApellido.setText(asociado.getPrimerApellido());
            txtfSApellido.setText(asociado.getSegundoApellido());
            txtfEdad.setText(String.valueOf(asociado.getEdad()));
            setBotones(false);
            setFormFields(false);
        } else
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Asociado no encontrado");
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnEliminar(ActionEvent actionEvent) {
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar usuario", getStage(), registroAsociado.eliminarAsociado(txtfFolio.getText()));
        registroAsociado.guardarAsociados();
        onActionBtnLimpiar(actionEvent);
    }

    @javafx.fxml.FXML
    public void onActionBtnLimpiar(ActionEvent actionEvent) {
        limpiarCampos();
    }

    private void setRegistros() {
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociado.cargarAsociados();
    }

    private void setBotones(boolean value) {
        mbtnEliminar.setDisable(value);
        mbtnModificar.setDisable(value);
        mbtnBuscar.setDisable(!value);
    }

    private void setFormFields(boolean value) {
        txtfNombre.setDisable(value);
        txtfPApellido.setDisable(value);
        txtfSApellido.setDisable(value);
        txtfEdad.setDisable(value);
        txtfFolio.setDisable(!value);
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

    private void cargarImagenAsociado(String ruta) {
        try
        {
            if (imvFoto != null)
            {
                File archivo = new File(ruta);
                if (archivo.exists())
                {
                    Image img = new Image(archivo.toURI().toString());
                    imvFoto.setImage(img);
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error ,Archivo no encontrado", getStage(), "Error al cargar la imagen | Ruta no encontrada");
                    imvFoto.setImage(null);
                }
            }
        } catch (Exception e)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al cargar la imagen", getStage(), "Ocurrió un error al intentar cargar la imagen.");
            imvFoto.setImage(null);
        }
    }

    private void limpiarCampos() {
        txtfFolio.setText("");
        txtfNombre.setText("");
        txtfPApellido.setText("");
        txtfSApellido.setText("");
        txtfEdad.setText("");
        cargarImagenAsociado("./src/main/resources/cr/ac/una/tareacooperativa/resources/userNotFound.jpg");
        setFormFields(true);
        setBotones(true);
    }
}
