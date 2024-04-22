package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.Cooperativa;
import cr.ac.una.tareacooperativa.model.PdfManager;
import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXTextField;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * <p>
 * Controlador de la pantalla para imprimir carnet <br>
 * Contiene los metodos que usan el pdfManager para crear pdf's
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class ImpresionCarnetController extends Controller implements Initializable {

    private Cooperativa cooperativa;
    private RegistroAsociado registroAsociado;
    private PdfManager pdfManager;

    @javafx.fxml.FXML
    private AnchorPane root;

    @javafx.fxml.FXML
    private MFXTextField txtfNombre;

    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @FXML
    private MFXButton btnImprimir;
    @FXML
    private ImageView imvFotoAsociado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        cargarDatosAsociados();
        limpiarCampos();
    }

    private void cargarDatosAsociados() {
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociado.cargarAsociados();
    }

    private void cargarDatosCooperativa() {
        cooperativa = ((Cooperativa) AppContext.getInstance().get("cooperativa"));
        cooperativa.cargarDatosCooperativa();
    }

    private void limpiarCampos() {
        txtfNombre.setEditable(false);
        this.txtfNombre.clear();
        this.txtfFolio.clear();
        this.btnImprimir.setDisable(true);
        cargarImagenSocio("/src/main/resources/cr/ac/una/tareacooperativa/resources/userNotFound.jpg");
    }

    @javafx.fxml.FXML
    public void onActionBtnImprimir(ActionEvent actionEvent) {
        if (pdfManager != null)
        {
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "PDF USER", getStage(), pdfManager.crearPDF());
            this.txtfFolio.clear();
            this.txtfNombre.clear();
            this.btnImprimir.setDisable(true);
            cargarImagenSocio("/src/main/resources/cr/ac/una/tareacooperativa/resources/userNotFound.jpg");
        }
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) throws IOException {
        if (txtfFolio.getText().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Debes escribir un folio");
            this.btnImprimir.setDisable(true);
        } else
        {
            cargarDatosCooperativa();
            Asociado asociadoPdf = registroAsociado.buscarAsociado((txtfFolio.getText()).toUpperCase());
            crearPdfInstance(asociadoPdf, cooperativa);
            if (asociadoPdf != null)
            {
                cargarImagenSocio(asociadoPdf.getRutaFoto());
            }
        }
    }

    private void crearPdfInstance(Asociado socio, Cooperativa coope) throws IOException {
        if (socio != null && coope != null)
        {
            this.pdfManager = new PdfManager(socio, coope);
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Usuario Encontrado", getStage(), "Usuario Encontrado");
            txtfNombre.setText(socio.getNombre());
            this.btnImprimir.setDisable(false);

        } else
        {
            this.btnImprimir.setDisable(true);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Folio incorrecto o asociado inexistente");
        }
    }

    private void cargarImagenSocio(String rutaFoto) {
        try
        {
            if (imvFotoAsociado != null)
            {
                File archivo = new File(rutaFoto);
                if (archivo.exists())
                {
                    Image imagen = new Image(archivo.toURI().toString());
                    this.imvFotoAsociado.setImage(imagen);
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error ,Archivo no encontrado", getStage(), "La imagen no se encuentra en la ruta especificada.");
                    imvFotoAsociado.setImage(null);
                }
            } else
            {
                System.out.println("imvFotoAsociado es nulo");
                imvFotoAsociado.setImage(null);
            }
        } catch (Exception e)
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al cargar la imagen", getStage(), "Ocurrió un error al intentar cargar la imagen.");
        }
    }

}
