
public class Reseau {
	
	/* Attributs */
	
	public static final float COEFFICIENT_APPRENTISSAGE = (float) 0.6;		// Coefficient d'apprentissage
	public static final int ITERATIONS = 1000;								// Nombres d'itérations souhaitées
	public static final float INERTIE = (float) 0.6;						// Terme dit "d'inertie" afin d'echapper aux minimums locaux
	private Couche[] couches;								// Ce tableau est la liste des connexions entres les neurones des différentes couches : Entrés / Cachés / Sortie
	
	
	/* Constructeur 
	 * Il prend en parametres :
	 * *** Un nombre de neurones en entrées --> ici 4 car 4 attributs
	 * *** Un nombre de neurones cachés 	--> on met ce que l'on souhaite (ex : 6)
	 * *** Un nombre de neurones en sorties	--> ici 2 car 2 sorties possibles : 0 1 ou 1 0 */
	public Reseau(int neuronesEntree, int neuronesCaches, int neuronesSortie) {
		
		/* Nous initalisons le tableau de couches avec une taille de 2 car il y a uniquement une connexion entre : 
		 * neurones en entrées -->  neurones cachés 
		 * 							neurones cachés --> neurones en sortie  */
		this.couches = new Couche[2];
		this.couches[0] = new Couche(neuronesEntree, neuronesCaches);
		this.couches[1] = new Couche(neuronesCaches, neuronesSortie);
	}

	
	/* Méthodes */
	
	
	/** Processus d'apprentissage avec rétropopagation du gradient qui repousse l'activité neuronale sortie --> entrée afin d'adapter les connexions. 
	 * @param baseDeConnaissances (float[]) : listes des valeurs en entrée
	 * @param resultatsAttendus (float[]) : les des résultats attendus 
	 * @param coefficientApprentissage (float) : Celui initialisé dans les Attributs (doit être compris entre 0.1 et 0.3)
	 * @param inertie (float) : Sert à sortir des minimums locaux
	 * */
	public void apprentissage(float[] baseDeConnaissances, float[] resultatsAttendus, float coefficientApprentissage, float inertie) {
		
		// On fait appel à la fonction run afin de calculer les resultats en sortie depuis la baseDeConnaissances
		float[] resultatEnSortie = run(baseDeConnaissances);
		
		float[] erreur = new float[resultatEnSortie.length];
		
		for(int i = 0; i < erreur.length; i++) {
			erreur[i] = resultatsAttendus[i] - resultatEnSortie[i];		// On compare le resultat souhaite - le resultat que l'on a 
		}
		
		// Retropopagation, obtenir les erreurs des valeurs calculees 
		for(int i = this.couches.length - 1; i >= 0; i--) {
			erreur = couches[i].apprend(erreur, coefficientApprentissage, inertie);
		}
	}
	
	
	/* Calculer les résultats en sortie depuis la base de connaissances */
	public float[] run(float[] baseDeConnaissances) {
		
		float[] valeursResultantes = baseDeConnaissances;
		
		/* Pour toutes les couches on va utiliser la fonction run avec les valeurs de la base de connaissances */
		for(int i = 0; i < this.couches.length; i++) {
			valeursResultantes = couches[i].run(valeursResultantes);
		}
		return valeursResultantes;
	}

	

	public static float sigmoide(float x) {
		// TODO Auto-generated method stub
		return (float) (1 / (1 + Math.exp(-x)));
	}
	
	public static float deriveSigmoide(float x) {
		// TODO Auto-generated method stub
		return (x * (1-x));
	}
	
	
	
	
	


	public float getCoefficientApprentissage() {
		return COEFFICIENT_APPRENTISSAGE;
	}
	public int getIterations() {
		return ITERATIONS;
	}
	public float getInertie() {
		return INERTIE;
	}
	public Couche[] getCouches() {
		return couches;
	}

	public void setCouches(Couche[] couches) {
		this.couches = couches;
	}




	




	

	
}
