package cr.ac.una.tareacooperativa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.util.AppContext;
import cr.ac.una.tareacooperativa.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Universidad Nacional - Programación II 2024
 * <p>
 *     Controlador de la pantalla de inicio de sesión
 * </p>
 * @author Stiward Araya 
 * @author Justin Mendez
 */
public class LoginController extends Controller implements Initializable {


    @FXML
    private Label lblNombreCoope;
    @FXML
    private ImageView imvLogoCoope;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton bntAsociados;
    @FXML
    private MFXButton btnFuncionarios;
    @FXML
    private MFXButton btnProfesores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @Override
    public void initialize() {
        AppContext.getInstance().set("NombreCoope", this.lblNombreCoope);
    }

    @FXML
    public void onClickLabelAcercade(Event event) throws IOException{
        //TODO: abrir webView con la web
    }

    @FXML
    public void onActionBtnFuncionarios(ActionEvent event) {
        FlowController.getInstance().goMain("FuncionarioView");
        ((Stage) btnFuncionarios.getScene().getWindow()).close();
    }

    @FXML
    public void onActionBtnProfesores(ActionEvent event)  {
        FlowController.getInstance().goMain("ProfesorView");
        ((Stage) btnProfesores.getScene().getWindow()).close();
    }


    @FXML
    public void onActionBtnAsociados(ActionEvent event) {
        FlowController.getInstance().goMain("AsociadoView");
        ((Stage) bntAsociados.getScene().getWindow()).close();
    }
}
