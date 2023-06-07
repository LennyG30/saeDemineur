package saeDemineur.jfx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import saeDemineur.options.Options;

public class ControlleurOptions {

	@FXML
	public Label largeurLabel, hauteurLabel, minesLabel;
	
	@FXML
	public Slider hauteurSlider, largeurSlider, minesSlider;

	public ControlleurOptions() {
		Platform.runLater(() -> {
			/**
			 * On a besoin de savoir quand la valeur d'un slider change, pour ça, on doit ajouter
			 * un listener à la propriété valeur (Value).
			 * 
			 * La méthode changed sera appellée par JavaFX quand un utilisateur bougera le slider
			 * Il suffit de récuperer le paramètre valeurActuelle pour savoir quelle est la valeur du slider
			 */
			hauteurSlider.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable,
						    Number ancienneValeur, Number valeurActuelle) {
					
					Options.hauteur = valeurActuelle.intValue();
					hauteurLabel.setText("Hauteur: " + valeurActuelle.intValue());
				}
			});
			
			largeurSlider.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number ancienneValeur, Number valeurActuelle) {
					
					Options.largeur = valeurActuelle.intValue();
					largeurLabel.setText("Largeur: " + valeurActuelle.intValue());
				}
			});
			
			minesSlider.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable,
						    Number ancienneValeur, Number valeurActuelle) {
					
					Options.nombreMines = valeurActuelle.intValue();
					minesLabel.setText("Mines: " + valeurActuelle.intValue());
				}
			});
		});
	
	}
}
