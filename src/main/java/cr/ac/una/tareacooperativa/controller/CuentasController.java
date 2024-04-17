package cr.ac.una.tareacooperativa.controller;

import cr.ac.una.tareacooperativa.model.Asociado;
import cr.ac.una.tareacooperativa.model.AsociadoCuenta;
import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.model.RegistroCuenta;
import cr.ac.una.tareacooperativa.util.AppContext;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.tareacooperativa.model.RegistroAsociadoCuenta;
import cr.ac.una.tareacooperativa.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;

/**
 * <p>
 * Controlador de la pantalla de cuentas<br>
 * Contiene los metodos de drag and drop y lo <br>
 * referente a cuentas de asociados.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class CuentasController extends Controller implements Initializable {

    private RegistroAsociadoCuenta registroAsociadoCuenta;
    private RegistroCuenta registroCuenta;
    private RegistroAsociado registroAsociado;
    private ArrayList<String> cuentasDisponibles;
    private ArrayList<String> cuentasAsociado;

    @FXML
    private BorderPane root;
    @FXML
    private MFXListView listViewCuentasCliente;
    @FXML
    private MFXListView listViewCuentasDisponibles;
    @FXML
    private MFXTextField txtfFolio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        cargarRegistros();
        limpiarCampos();
        cargarDragnDrop();
    }

    private void cargarRegistros() {
        cuentasAsociado = new ArrayList<>();
        cuentasDisponibles = new ArrayList<>();
        registroAsociadoCuenta = ((RegistroAsociadoCuenta) AppContext.getInstance().get("asociadosCuentas"));
        registroCuenta = ((RegistroCuenta) AppContext.getInstance().get("cuentas"));
        registroAsociado = ((RegistroAsociado) AppContext.getInstance().get("asociados"));
        registroAsociado.cargarAsociados();
        registroCuenta.cargarCuentas();
        registroAsociadoCuenta.cargarAsociadoCuenta();
    }

    private void limpiarCampos() {
        txtfFolio.setText("");
        listViewCuentasCliente.getItems().clear();
        listViewCuentasDisponibles.getItems().clear();
        txtfFolio.setDisable(false);
    }

    @FXML
    public void onActionBtnGuardar(ActionEvent actionEvent) {
        txtfFolio.setDisable(false);
        printCuentasCliente();
        almacenarAsociadoCuenta();
        eliminarAsociadoCuenta();
        registroAsociadoCuenta.guardarAsociadoCuenta();
        limpiarCampos();
    }

    @FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {

        txtfFolio.setText(txtfFolio.getText().toUpperCase());
        Asociado asociado = registroAsociado.buscarAsociado(txtfFolio.getText());
        if (asociado != null) {
            cargarListasAsociado(asociado);
            txtfFolio.setDisable(true);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Asociado no encontrado");
        }
    }

    @Deprecated
    private void OnDragDetectedCuentas(MouseEvent event) {
        OnDragDetectedListas(event, listViewCuentasDisponibles);
    }

    @Deprecated
    private void OnDragDroppedCuentas(DragEvent event) {
        OnDragDroppedListas(event, listViewCuentasDisponibles);
    }

    @Deprecated
    private void OnDragOverCuentas(DragEvent event) {
        onDragOverListas(event, listViewCuentasDisponibles);
    }

    @Deprecated
    private void OnDragDetectedCuentasCliente(MouseEvent event) {
        OnDragDetectedListas(event, listViewCuentasCliente);
    }

    @Deprecated
    private void OnDragDroppedCuentasCliente(DragEvent event) {
        OnDragDroppedListas(event, listViewCuentasCliente);
    }

    @Deprecated
    private void OnDragOverCuentasCliente(DragEvent event) {
        onDragOverListas(event, listViewCuentasCliente);
    }

    private void OnDragDetectedListas(MouseEvent event, MFXListView<String> lista) {
        String itemToDrag = lista.getSelectionModel().getSelectedValue();
        Dragboard dragboard = lista.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putString(itemToDrag);
        dragboard.setContent(content);

        event.consume();

    }

    private void OnDragDroppedListas(DragEvent event, MFXListView<String> lista) {
        String item = event.getDragboard().getString();
        lista.getItems().add(item);

        MFXListView<?> source = (MFXListView<?>) event.getGestureSource();
        source.getItems().remove(item);

        event.setDropCompleted(true);
        event.consume();

    }

    private void onDragOverListas(DragEvent event, MFXListView<String> lista) {
        if (event.getGestureSource() != lista && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void cargarDragnDrop() {

        listViewCuentasDisponibles.setOnDragDetected(this::OnDragDetectedCuentas);
        listViewCuentasDisponibles.setOnDragOver(this::OnDragOverCuentas);
        listViewCuentasDisponibles.setOnDragDropped(this::OnDragDroppedCuentas);

        listViewCuentasCliente.setOnDragDetected(this::OnDragDetectedCuentasCliente);
        listViewCuentasCliente.setOnDragOver(this::OnDragOverCuentasCliente);
        listViewCuentasCliente.setOnDragDropped(this::OnDragDroppedCuentasCliente);
    }

    private void cargarListasAsociado(Asociado asociado) {
        this.listViewCuentasDisponibles.getItems().clear();
        this.listViewCuentasCliente.getItems().clear();
        this.cuentasAsociado.clear();
        this.cuentasDisponibles.clear();

        toStringCuentasAsociados(asociado.getFolio());
        cargarCuentasDisponibles();

        if (!cuentasDisponibles.isEmpty()) {
            listViewCuentasDisponibles.getItems().addAll(cuentasDisponibles);
        }
        if (!cuentasAsociado.isEmpty()) {
            listViewCuentasCliente.getItems().addAll(cuentasAsociado);
        }
        printCuentasCliente();
    }

    private void cargarCuentasDisponibles() {
        cuentasDisponibles = registroCuenta.toStringCuentas();
        for (String cuentaNombre : cuentasAsociado) {
            if (cuentasDisponibles.contains(cuentaNombre)) {
                cuentasDisponibles.remove(cuentaNombre);
            }

        }
        if (cuentasDisponibles.isEmpty()) {
            System.out.println("La lista está vacía.");
            cuentasDisponibles = new ArrayList<>();
        } else {
            System.out.println("La lista no está vacía. Tiene " + cuentasDisponibles.size() + " elementos.");
        }
    }

    private void printCuentasCliente() {
        System.out.println("Elementos en listViewCuentasCliente:");
        int index = 0;
        for (Object item : listViewCuentasCliente.getItems()) {
            index++;
            System.out.println(item.toString());
        }
        System.out.println("Indice: " + index);
    }

    private void almacenarAsociadoCuenta() {

        if (!txtfFolio.getText().isEmpty()) {
            for (Object item : listViewCuentasCliente.getItems()) {

                if (!cuentasAsociado.contains(item)) {
                    AsociadoCuenta asociadoCuenta = createAsociadoCuentaInstance((String) item, txtfFolio.getText());
                    if (asociadoCuenta != null) {
                        registroAsociadoCuenta.agregarAsociadoCuenta(asociadoCuenta);
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "CuentasInfo", getStage(), "Cuenta" + item + " abierta Correctamente");

                    }
                }
            }

        }
    }

    private void eliminarAsociadoCuenta() {

        if (!txtfFolio.getText().isEmpty()) {
            for (Object item : listViewCuentasDisponibles.getItems()) {

                if (!cuentasDisponibles.contains(item)) {///...
                    Integer cuentaId = registroCuenta.buscarCuentaPorNombre(item.toString());
                    AsociadoCuenta asociadoCuenta = registroAsociadoCuenta.buscarAsociadoCuenta(txtfFolio.getText(), cuentaId);
                    if (asociadoCuenta.getBalanceCuenta() <= 0) {
                        registroAsociadoCuenta.eliminarAsociadoCuenta(txtfFolio.getText(), cuentaId);
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "CuentasInfo", getStage(), "Cuenta" + item + " eliminada correctamente");

                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "CuentasInfo", getStage(), "Cuenta" + item + " no se ha podido eliminar correctamente, el asociado tiene dinero en la cuenta");
                        System.out.println("El balance de la cuenta es: " + asociadoCuenta.getBalanceCuenta());
                    }
                }
            }

        }
    }

    private AsociadoCuenta createAsociadoCuentaInstance(String item, String folioSocio) {
        Integer cuentaId = registroCuenta.buscarCuentaPorNombre(item);

        if (cuentaId != 0 && !folioSocio.isEmpty()) {
            AsociadoCuenta asociadoCuenta = new AsociadoCuenta(folioSocio, cuentaId, 0);
            return asociadoCuenta;
        }
        return null;
    }

    private void toStringCuentasAsociados(String folioAsociado) {
        ArrayList<Integer> idCuentasAsociado = registroAsociadoCuenta.idCuentasByFolio(folioAsociado);

        for (Integer auxId : idCuentasAsociado) {
            cuentasAsociado.add(registroCuenta.getNombreCuentaByID(auxId));
        }
    }

}
