package saeDemineur.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class PrincipalJavaFX extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Stage load = FXMLLoader.load(PrincipalJavaFX.class.getResource("/saeDemineur/vue/Vue.FXML"));
		load.setTitle("SAE Demineur");
		load.setWidth(800);
		load.setHeight(800);
		load.setResizable(false);
		load.show();
	}

}
