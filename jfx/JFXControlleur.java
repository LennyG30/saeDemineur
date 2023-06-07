package saeDemineur.jfx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import saeDemineur.model.Modele;
import saeDemineur.options.Options;
import saeDemineur.principale.Case;

public class JFXControlleur {

	@FXML
	public Button boutonJouer;
	
	@FXML
	public Button options;

	@FXML
	public GridPane grilleJeu;

	@FXML
	public Label chrono;
	
	@FXML
	public Label nbreDrapeaux;
	
	@FXML
	public Button relancer;
	
	public boolean finDemandee = false;

	private long dateCommencement = 0L;

	/**
	 * Méthode qui est appellée lors de l'appui sur le bouton Options par JFX
	 */
	@FXML
	public void ouvrirPopupOptions() {
		Stage load = null;
		try {
			// Méthode pour charger le .fxml, et le convertir en Objet "Stage"
			load = 
				FXMLLoader.load(JFXControlleur.class.getResource("/saeDemineur/vue/Options.FXML"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Si le chargement a fonctionné
		if(load != null) {
			// On change quelques propriétés de la fenêtre
			load.setTitle("SAE Demineur");
			load.setResizable(false);
			load.setAlwaysOnTop(true);
			load.show();
		}
	}
	
	@FXML
	public void effectuerActionJouer() {
		// On affiche les lignes entre les cases
		grilleJeu.setGridLinesVisible(true);
		

		// On crée un modèle puis on génère la grille qui va avec
		Modele modele = new Modele(Options.hauteur, Options.largeur, 
				       Options.nombreMines);
		this.generationGrille(modele);
		

		// On stocke la date actuelle pour le chronomètre
		this.dateCommencement = System.currentTimeMillis();
		
		// Création d'un thread pour le chronomètre (thread séparé à cause du while(true)
		new Thread(() -> {
			while (!finDemandee) {

				long dateActuelle = System.currentTimeMillis();

				// JavaFX n'aime pas quand on modifie les élements visuels depuis un thread autre
				// que le sien, donc on appelle la methode runLater pour éxcuter du code sur le thread
				// de javafx.
				Platform.runLater(() -> {
					// On fait la différence entre les deux dates, et on divise par 1000
					// pour passer de millisecondes à secondes
					long duree = dateActuelle - dateCommencement;
					duree /= 1000;
					chrono.setText(String.format("%d secondes",
							       duree));
				});
				
				// On fait attendre le thread 250 millisecondes pour pas trop surcharger le pc
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				

			}
		}).start();
		
	}

	private void generationGrille(Modele mod) {
		
		// On efface les colonnes et lignes de la grille
		grilleJeu.getColumnConstraints().clear();
		grilleJeu.getRowConstraints().clear();
		
		// On définit les tailles de la grille
		grilleJeu.setMinWidth(grilleJeu.getScene().getWidth());
		grilleJeu.setPrefWidth(grilleJeu.getScene().getWidth());
		grilleJeu.setMinHeight(grilleJeu.getScene().getHeight() - 80);
		grilleJeu.setPrefHeight(grilleJeu.getScene().getHeight() - 80);
		
		// On actuailise le message avec le nombre de drapeaux restants
		this.nbreDrapeaux.setText(String.format("🚩 : %d",
								  mod.mines.size() - mod.drapeaux.size()));


		// On crée la grille qui va être affichée à l'écran
		for (int x = 0; x < mod.getHauteur(); x++) {
			for (int y = 0; y < mod.getLargeur(); y++) {
				Button bouton = new Button("  ");

				// Largeur d'un bouton = largeur de la fenêtre divisé par le nombre de cases en longeur
				bouton.setPrefWidth(grilleJeu.getScene().getWidth() 
						/ (double) mod.getLargeur());
				// Pareil pour la hauteur
				bouton.setPrefHeight((grilleJeu.getScene().getHeight() - 80)
						/ (double) mod.getHauteur());

				// Utile pour plus tard, permet de stocker LIGNE et COLONNE dans le bouton sans avoir à l'afficher à l'écran
				bouton.setId(String.format("%d-%d", x, y));
				
				// On récupère la case actuelle
				Case caseActuelle = mod.grille[x][y];
				if (caseActuelle.mine && caseActuelle.estRevele()) {
					bouton.setStyle("-fx-background-color: red;");
					bouton.setText("💣");
				} else if (caseActuelle.estDrapeau()
						&& !caseActuelle.estRevele()) {
					bouton.setText("🚩");
				} else if (caseActuelle.estRevele()) {
					bouton.setStyle("-fx-background-color: white;");
					// affiche le nombre de mines voisines sur les cases
					bouton.setText(String.valueOf(mod.compterMinesVoisines(x, y)));
				} 
				
				// On ajoute un listener pour les appui sur les boutons de la grille
				bouton.setOnMouseClicked((event) -> {
					
					boolean clicGauche = 
							event.getButton() == MouseButton.PRIMARY;
					
										
					// Si le joueur a perdu	
					if (caseActuelle.estMine() && clicGauche) {
						// arret du chronometre
						finDemandee = true;
						for (int hauteur = 0; hauteur < mod.getHauteur(); hauteur++) {
			                for (int largeur = 0; largeur < mod.getLargeur(); largeur++) {
			                	
								if (mod.grille[hauteur][largeur].estMine()) {
									mod.grille[hauteur][largeur].setRevele(true);
									bouton.setStyle("-fx-background-color: black;");
								}
			                }
						}
						Stage load = null;
						try {
							// Méthode pour charger le .fxml, et le convertir en Objet "Stage"
							load = FXMLLoader.load(JFXControlleur.class.getResource("/saeDemineur/vue/Perdu.FXML"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						// Si le chargement a fonctionné
						if(load != null) {
							// On change quelques propriétés de la fenêtre
							load.setTitle("SAE Demineur");
							load.setResizable(false);
							load.setAlwaysOnTop(true);
							load.show();
						}
						
					}
					
			    	// Si le joueur a gagné
					if(mod.getCompteurCaseRevelee() == (mod.getHauteur()* mod.getLargeur())
							- mod.getNombreMines()) {
						if( !caseActuelle.estRevele() && caseActuelle.estMine()) {
							
							Stage load = null;
							try {
								// Méthode pour charger le .fxml, et le convertir en Objet "Stage"
								load = FXMLLoader.load(JFXControlleur.class.getResource("/saeDemineur/vue/Gagne.FXML"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							// Si le chargement a fonctionné
							if(load != null) {
								// On change quelques propriétés de la fenêtre
								load.setTitle("SAE Demineur");
								load.setResizable(false);
								load.setAlwaysOnTop(true);
								load.show();
							}
						}
							
					} 
					
					
					// On récupère l'id qu'on a donné précedemment
					String id = bouton.getId();
					
					// On coupe au niveau du tiret, la partie à gauche = numéro de ligne, à droite = colonne
					int boutonLigne = Integer.valueOf(id.split("-")[0]);
					int boutonColonne = Integer.valueOf(id.split("-")[1]);

					// Si on peut réveler, on révèle, sinon si on peut poser un drapeau, on pose
					if (clicGauche && !caseActuelle.estRevele() 
							       && !caseActuelle.estDrapeau()) {
						
						mod.revelerCase(boutonLigne, boutonColonne);
						
					} else if (!clicGauche && !caseActuelle.estRevele()) {
						mod.drapeauPose(boutonLigne, boutonColonne);
					}

					// On rappelle la génération de la grille 
					// (qui actuailise aussi les élements comme le nombre de drapeaux etc...)
					generationGrille(mod);
				});
				
				// On ajoute le bouton à la grille 
				grilleJeu.add(bouton, x, y);
				
			}
		}
	}
	
}
