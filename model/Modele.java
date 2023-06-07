/**
 * Modele.java						        	27/05/2023
 * IUT de Rodez, pas de copyright
 */
package saeDemineur.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import saeDemineur.principale.Case;

/**
 * Modele du programme du jeu du démineur
 * 
 * @author Kerroumi
 * @author Germain
 */

public class Modele {

	// hauteur de la grille du jeu
	private int hauteur;

	// largeur de la grille du jeu
	private int largeur;
	
	// compteur de cases révélee
	private int compteurCaseRevelee = 0;
	
	// nombre de mines
	public int nombreMines;

	// grille(tableau) du jeu
	public Case[][] grille;
	
	
	//Liste des mines et des tableau (pour les comparer puisque : 
	// 		nombre de drapeaux à poser = nombre de mines
	public List<int[]> mines = new ArrayList<>();
	public List<int[]> drapeaux = new ArrayList<>();

	/**
	 * Constructeur de la classe modele
	 * @param hauteur hauteur de la grille du jeu
	 * @param largeur largeur de la grille du jeu
	 * @param nombreMines nombre de mines
	 */
	public Modele(int hauteur, int largeur, int nombreMines) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.nombreMines = nombreMines;
		this.grille = new Case[hauteur][largeur];

		// Initialisation de la grille avec des cases vides
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				int minesVoisines = 0;
				grille[i][j] = 
						new Case(false, false, false, false, minesVoisines);
				
	
			}
		}
		// Placement des mines aléatoirement
		placerMinesAleatoirement();
		calculerMinesVoisines();
		
	}

	/** hauteur de la grille */
	public int getHauteur() {
		return hauteur;
	}

	/** largeur de la grille */
	public int getLargeur() {
		return largeur;
	}
	
	/** 
	 * création d'un nouveau tableau grille de type Case
	 * @param hauteur hauteur de la grille
	 * @param largeur largeur de la grille
	 * */
	public Case getCase(int hauteur, int largeur) {
		return grille[hauteur][largeur];
	}

	/**
	 * méthode faisant appel a la classe Random 
	 * permettant de placer les mines aléatoirement dans la grille
	 */
	public void placerMinesAleatoirement() {
		
		Random random = new Random();

		int minesPlacees = 0;
		while (minesPlacees < nombreMines) {
			int ligne = random.nextInt(hauteur);
			int colonne = random.nextInt(largeur);

			if (!grille[ligne][colonne].estMine()) {
				grille[ligne][colonne].setMine(true);
				minesPlacees++;
				mines.add(new int[] { ligne, colonne });
			}
		}
		nombreMines = minesPlacees;
	}
	/** nombre de mines dans le jeu */
    public int getNombreMines() {
		return nombreMines;
	}

	/**
	 * calcule le nombre de mines voisines pour chaque case, 
	 * en passant par toutes les lignes voisines (en ligne(hauteur) et en colonne(largeur))
	 * @return le nombre de mines voisines pour chaque case
	 */
	public int calculerMinesVoisines() {
		int minesVoisines = 0;
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				minesVoisines = compterMinesVoisines(i, j);
				
				
				
				
			}
		}
		return minesVoisines;
	}
	
	/**
	 * compte
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public int compterMinesVoisines(int ligne, int colonne) {
		int minesVoisines = 0;
		
		if(!grille[ligne][colonne].estMine()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int voisinLigne = ligne + i;
					int voisinColonne = colonne + j;
					
					if (voisinLigne >= 0 && voisinLigne < hauteur
							        && voisinColonne >= 0 
							        && voisinColonne < largeur) {
						if (grille[voisinLigne][voisinColonne].estMine()) {
							minesVoisines++;
							
						}
					}
					
				}
			}
				
		}
		return minesVoisines;
		
	}
	
	/**
	 * révelation de case 
	 * @param ligne ligne de la grille du jeu
	 * @param colonne colonne de la grille du jeu
	 */
	public void revelerCase(int ligne, int colonne) {
		Case caseActuelle = grille[ligne][colonne];

		// vérifie que les cases ne sont pas déjà révélée
		// et qu'il n'y a pas de drapeau
		if (!caseActuelle.estRevele() && !caseActuelle.estDrapeau()) {
			caseActuelle.setRevele(true);
			compteurCaseRevelee++;
			
			if (compterMinesVoisines(ligne, colonne) == 0) {
	            // La case est vide, effectuer la révélation en cascade
	            revelerCaseEnCascade(ligne, colonne);
	        }

		}
		
	}
	
	/**
	 * Révélation en cascade de la grille du jeu
	 * @param ligne ligne de la grille
	 * @param colonne colonne de la grille
	 */
	public void revelerCaseEnCascade(int ligne, int colonne) {
		
	    // Vérifie les cases voisines dans un carré 3x3 autour de la case actuelle
	    for (int i = ligne - 1; i <= ligne + 1; i++) {
	        for (int j = colonne - 1; j <= colonne + 1; j++) {
	        	
	            // Vérifie que les indices restent dans les limites de la grille
	            if (i >= 0 && i < hauteur && j >= 0 && j < largeur) {
	                Case caseVoisine = grille[i][j];
	                
	                // Vérifie si la case voisine n'est pas déjà révélée et n'est pas une mine
	                if (!caseVoisine.estRevele() && !caseVoisine.estMine()) {
	                    // Révèle la case voisine
	                    caseVoisine.setRevele(true);
	                    compteurCaseRevelee++;
	                    

	                    // Vérifie si la case voisine est vide (sans mines voisines)
	                    if (compterMinesVoisines(i, j) == 0) {
	                        // Effectue la révélation en cascade de la case voisine
	                        revelerCaseEnCascade(i, j);
	                        
	                    }
	                }
	                
	            }
	        }
	    }
	    
	}

	public int getCompteurCaseRevelee() {
		return compteurCaseRevelee;
	}

	/**
	 *  placement de drapeaux si possible
	 * @param ligne lignes de la grille du jeu
	 * @param colonne colonnes de la grille du jeu
	 */
	public void drapeauPose(int ligne, int colonne) {
		Case caseActuelle = grille[ligne][colonne];

		/*
		 * vérifie qu'un point drapeau n'est pas déjà posé, si un drapeau est posé on
		 * l'enlève sinon on pose un drapeau
		 */
		if(!caseActuelle.estDrapeau()) {
			if(this.drapeaux.size() == this.mines.size())return;
		}
		
		caseActuelle.setDrapeau(!caseActuelle.estDrapeau());
		
		if (caseActuelle.estDrapeau())
			this.drapeaux.add(new int[] { ligne, colonne });
		else {
			int iTrouve = -1;
			
			for(int i = 0; i < this.drapeaux.size(); i++) {
				int[] drap = this.drapeaux.get(i);
				if(drap[0] == ligne && drap[1] == colonne) {
					iTrouve = i;
					break;
				}
			}
			
			if(iTrouve != -1)
				this.drapeaux.remove(iTrouve);
		}
	}

	/**
	 * placement des points d'interrogation si possible
	 * @param ligne
	 * @param colonne
	 */
	public void InterrogationPosee(int ligne, int colonne) {
		Case caseActuelle = grille[ligne][colonne];

		/*
		 * vérifie qu'un point d'interrogation n'est pas déjà posé, si un point
		 * d'interrogation est posé on l'enlève sinon on pose un point d'interrogation
		 */
		caseActuelle.setPointInterrogation(!caseActuelle.estPointInterrogation());
	}
	
}
