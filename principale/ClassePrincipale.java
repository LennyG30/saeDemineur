package saeDemineur.principale;

import javafx.application.Application;
import saeDemineur.jfx.PrincipalJavaFX;

public class ClassePrincipale {
	
	public static Case caseDemineur = new Case(false, false, false, false, 0);
	
	public static void main(String[] args) {
		Application.launch(PrincipalJavaFX.class);
	}
}