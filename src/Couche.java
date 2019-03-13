import java.util.Arrays;
import java.util.Random;

public class Couche {

	/* Attributs */
	
	private float[] e;			// Valeurs en entree
	private float[] s; 			// Resultat calculee en sortie
	private float[] w; 			// Le poids des connexions
	private float[] poidsDelta; // La différence pour "éduquer les neurones"
	private Random random;			// Generateur de nombres aléatoires 
	
	/**	Constructeur
	 * @param neuronesEntree (int) : nombre de neurones en entrée
	 * @param neuronesSortie (int) : nombre de neurones en sortie
	 * */
	public Couche(int neuronesEntree, int neuronesSortie) {
	
		this.e = new float[neuronesEntree + 1];		// e prend pour valeur le nombre de neurones en entrée +1 pour le biais (neurone emettant un signal d'intensité 1)
		this.s = new float[neuronesSortie];			// s prend pour valeur le nombre de neurones en sortie uniquement
		
		/* Il y a autant de connexions que le nb de neurones en entree (+ le biais) * le nb de neurones en sortie 
		 * PREMIERE COUCHE : 4 --> 6, DEUXIEME COUCHE 6 --> 2 donc w = 44 
		 * Tous ces poids seront initalisés avec un nombre aléatoire */
		this.w = new float[(neuronesEntree + 1) * neuronesSortie]; 	
		
		this.poidsDelta = new float[w.length];	// Autant de poids delta que poids
		
		this.random = new Random();
		
		/* On initalise les poids */
		initialisationPoids();
	}

	
	/* Méthodes */
	
	/* On initialise tous les poids à 0.1 (comme vu en cours) */
	public void initialisationPoids() {
		for (int i = 0; i < this.w.length; i++) {
            this.w[i] = (random.nextFloat() - 0.5f) * 4f;
        }
	}
	
	
	/* On lance les calculs afin d'obtenir la listes des resultats apres la fonction d'activation */
	public float[] run(float[] dataEntree) {

		// On copie dataEntree dans e
		System.arraycopy(dataEntree, 0, e, 0, dataEntree.length);
		
		// biais
		e[e.length - 1] = 1;

		int decalage = 0;
		
		for(int i = 0; i < s.length ; i++) {
			for(int j = 0; j < e.length; j++) {
				s[i] += (w[decalage + j] * e[j]);		// la sortie est égale a la somme des entrees( --> ) * le poids de ces entrées
			}
			
			s[i] = Reseau.sigmoide(s[i]);
			decalage += e.length;
		}
		return Arrays.copyOf(s, s.length);
	}
	
	
	// Apprentissage par retropropagation
	public float[] apprend(float[] erreur, float coefficientApprentissage, float inertie) {
		// TODO Auto-generated method stub
		
		int decalage = 0;
		
		float[] erreurSuivante = new float[e.length];
		
		for(int i = 0; i < s.length; i++) {
			
			// Si on a qu'une seule couche cachée, le delta ne change pas 
			float delta = erreur[i] * Reseau.deriveSigmoide(s[i]);
			
			for(int j = 0; j < e.length; j++) {
				int indexPoidsConnexion = decalage + j;
				erreurSuivante[j] = erreurSuivante[j] + w[indexPoidsConnexion] * delta;
				
				float gradient = e[j] * delta; 	// On calcule le gradient (la variation)
				
				w[indexPoidsConnexion] += gradient * coefficientApprentissage + inertie * poidsDelta[indexPoidsConnexion];
				poidsDelta[indexPoidsConnexion] = gradient;
			}
			decalage += e.length;
		}
		return erreurSuivante; 		// nom a changer
	}
	
	
	
	
	/* Accesseurs */
	
	public float[] getE() {return e;}
	public float[] getS() {return s;}
	public float[] getW() {return w;}
	public float[] getPoidsDelta() {return poidsDelta;}


	/* Mutateurs */
	
	public void setE(float[] e) {this.e = e;}
	public void setS(float[] s) {this.s = s;}
	public void setW(float[] w) {this.w = w;}
	public void setPoidsDelta(float[] poidsDelta) {this.poidsDelta = poidsDelta;}	
}
