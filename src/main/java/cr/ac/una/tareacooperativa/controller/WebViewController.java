/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.tareacooperativa.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author USUARIO PZ UNA
 */
public class WebViewController extends Controller implements Initializable {

    @javafx.fxml.FXML
    private AnchorPane root;
    @javafx.fxml.FXML
    private WebView webCooperativa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        //  webCooperativa.getEngine().load("../web/index.html");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtener el WebEngine del WebView
        WebEngine webEngine = webCooperativa.getEngine();

        // Cargar el archivo HTML desde el directorio de recursos
        String archivoHTML = getClass().getResource("src/main/web/index.html").toExternalForm();
        webEngine.load(archivoHTML);
    }
}
