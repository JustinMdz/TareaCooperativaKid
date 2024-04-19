package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import cr.ac.una.tareacooperativa.model.RegistroAsociadoCuenta;

import javax.swing.*;

/**
 * Universidad Nacional - Programación II 2024
 * <p>
 * Controlador de la pantalla de transacciones
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class TransaccionesController extends Controller implements Initializable {

    RegistroAsociadoCuenta registroAsociado;
    @javafx.fxml.FXML
    private MFXTextField txtfFolio;
    @javafx.fxml.FXML
    private MFXComboBox comboCuentas;
    @javafx.fxml.FXML
    private MFXTextField txtfSaldo;
    @javafx.fxml.FXML
    private Spinner spnDosmil;
    @javafx.fxml.FXML
    private Spinner spnCincomil;
    @javafx.fxml.FXML
    private Spinner spnMil;
    @javafx.fxml.FXML
    private Spinner spnDiezmil;
    @javafx.fxml.FXML
    private Spinner spnVeintemil;
    @javafx.fxml.FXML
    private Spinner spnDiez;
    @javafx.fxml.FXML
    private Spinner spnCincuenta;
    @javafx.fxml.FXML
    private Spinner spnCinco;
    @javafx.fxml.FXML
    private Spinner spnVeinticinco;
    @javafx.fxml.FXML
    private Spinner spnQuinientos;
    @javafx.fxml.FXML
    private Spinner spnCien;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
        setSpnValueFactories();
    }

    @javafx.fxml.FXML
    public void onActionBtnRetirar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onActionBtnDepositar(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, getTotal());
    }

    @javafx.fxml.FXML
    public void onActionBtnBuscar(ActionEvent actionEvent) {
        //registroAsociado.buscarAsociado((txtfFolio.getText()).toUpperCase());
    }

    private void setSpnValueFactories() {
        SpinnerValueFactory<Integer> valueMil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDosmil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCincomil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDiezmil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueVeintemil = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCinco = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueDiez = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueVeinticinco = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCincuenta = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueCien = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        SpinnerValueFactory<Integer> valueQuinientos = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);

        valueMil.setValue(0);
        valueDosmil.setValue(0);
        valueCincomil.setValue(0);
        valueDiezmil.setValue(0);
        valueVeintemil.setValue(0);
        valueCinco.setValue(0);
        valueDiez.setValue(0);
        valueVeinticinco.setValue(0);
        valueCincuenta.setValue(0);
        valueCien.setValue(0);
        valueQuinientos.setValue(0);

        spnMil.setValueFactory(valueMil);
        spnDosmil.setValueFactory(valueDosmil);
        spnCincomil.setValueFactory(valueCincomil);
        spnDiezmil.setValueFactory(valueDiezmil);
        spnVeintemil.setValueFactory(valueVeintemil);
        spnCinco.setValueFactory(valueCinco);
        spnDiez.setValueFactory(valueDiez);
        spnVeinticinco.setValueFactory(valueVeinticinco);
        spnCincuenta.setValueFactory(valueCincuenta);
        spnCien.setValueFactory(valueCien);
        spnQuinientos.setValueFactory(valueQuinientos);
    }

    private int getTotal() {
        int total = 0;
        total += ((Integer) spnMil.getValue()) * 1000;
        total += ((Integer) spnDosmil.getValue()) * 2000;
        total += ((Integer) spnCincomil.getValue()) * 5000;
        total += ((Integer) spnDiezmil.getValue()) * 10000;
        total += ((Integer) spnVeintemil.getValue()) * 20000;
        total += ((Integer) spnCinco.getValue()) * 5;
        total += ((Integer) spnDiez.getValue()) * 10;
        total += ((Integer) spnVeinticinco.getValue()) * 25;
        total += ((Integer) spnCincuenta.getValue()) * 50;
        total += ((Integer) spnCien.getValue()) * 100;
        total += ((Integer) spnQuinientos.getValue()) * 500;
        return total;
    }
}
