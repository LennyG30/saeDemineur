/**
 * Case.java                                                 27/05/2023
 * IUT De Rodez, pas de copyright
 */
package saeDemineur.principale;

/**
 * Classe représentant une case du jeu du Démineur
 * @author walid.kerroumi
 *
 */
public class Case {
	 public boolean mine;
	 private boolean revele;
	 public static int minesVoisines;
	 private boolean drapeau;
	 private boolean pointInterrogation;
	
	 public Case(boolean mine, boolean revele, boolean drapeau, boolean pointInterrogation, int minesVoisines) {
	     this.mine = mine;
	     this.revele = revele;
	     this.drapeau = drapeau;
	     this.pointInterrogation = pointInterrogation;
	     this.minesVoisines = minesVoisines;
	 }
	
	 public boolean estMine() {
	     return mine;
	 }
	
	 public void setMine(boolean mine) {
	     this.mine = mine;
	 }
	
	 public boolean estRevele() {
	     return revele;
	 }
	
	 public void setRevele(boolean revele) {
	     this.revele = revele;
	 }
	 
	 public boolean estDrapeau() {
		 return drapeau;
	 }
	 
	 public void setDrapeau(boolean drapeau) {
		 this.drapeau = drapeau;
	 }
	 
	 public boolean estPointInterrogation() {
		 return pointInterrogation;
	 }
	 
	 public void setPointInterrogation(boolean pointInterrogation) {
		 this.pointInterrogation = pointInterrogation;
	 }
	 
	 public static int getMinesVoisines() {
	     return minesVoisines;
	 }

	public void setMinesVoisines(int minesVoisines) {
		this.minesVoisines = minesVoisines;
		
	}
	
}



