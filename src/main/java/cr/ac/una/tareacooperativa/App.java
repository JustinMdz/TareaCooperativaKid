package cr.ac.una.tareacooperativa;

import cr.ac.una.tareacooperativa.model.Cooperativa;
import cr.ac.una.tareacooperativa.model.RegistroAsociado;
import cr.ac.una.tareacooperativa.model.RegistroAsociadoCuenta;
import cr.ac.una.tareacooperativa.model.RegistroCuenta;
import cr.ac.una.tareacooperativa.util.AppContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import cr.ac.una.tareacooperativa.util.FlowController;
import java.io.IOException;

/**
 * <p>
 * Clase App, inicia las instancias de las clases registro,<br>
 * guarda las instancias de registro en AppContext.<br><br>
 * Inicializa el flujo de ventanas en el FlowController<br>
 * y muestra la pantalla principal.
 * </p>
 *
 * @author Stiward Araya
 * @author Justin Mendez
 */
public class App extends Application {

    private static String accessParameter = "";
    RegistroAsociado asociados = new RegistroAsociado();
    RegistroCuenta cuentas = new RegistroCuenta();
    RegistroAsociadoCuenta asociadosCuentas = new RegistroAsociadoCuenta();
    Cooperativa cooperativa = new Cooperativa();

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        AppContext.getInstance().set("asociados", asociados);
        AppContext.getInstance().set("cuentas", cuentas);
        AppContext.getInstance().set("asociadosCuentas", asociadosCuentas);
        AppContext.getInstance().set("cooperativa", cooperativa);

        FlowController.getInstance().InitializeFlow(stage, null);
        //goViewAccesParameter(accessParameter);
        FlowController.getInstance().goViewInWindow("LoginView");
    }

    public static void main(String[] args) {
        if (args.length > 0)
        {
            accessParameter = args[0];
        }

        launch();
    }

    private void goViewAccesParameter(String parameter) {
        if (parameter.equals("P"))
        {
            FlowController.getInstance().goMain("ProfesorView");
        }
        if (parameter.equals("F"))
        {
            FlowController.getInstance().goMain("FuncionarioView");
        }
        if (parameter.equals("A"))
        {
            FlowController.getInstance().goMain("AsociadoView");
        }
    }
}
