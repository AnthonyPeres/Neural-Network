
import java.util.Arrays;
import java.util.Random;

/**
  * 
  * Class réseau composée de plusieurs couches  
  * 
  * */

public class Reseau {

   /** Variables */
	
	public final float TAUX_APPRENTISSAGE = 0.4f;			// Taux d'apprentissage 
    public static final int ITERATIONS = 10000;			// Nombre d'itérations
    private Couche[] _couches; 							// Les couches du réseau 

    
    /** Constructeur du réseau de neurones
     * @param neuronesEntrees 	(int) : Entier du nombre de neurones en entrée
     * @param neuronesCaches 	(int) : Entier du nombre de neurones cachés
     * @param neuronesSorties 	(int) : Entier du nombre de neurones en sortie */
    public Reseau(int neuronesEntrees, int neuronesCaches, int neuronesSorties) {
    	_couches = new Couche[2];
    	_couches[0] = new Couche(neuronesEntrees, neuronesCaches);
    	_couches[1] = new Couche(neuronesCaches, neuronesSorties);
    }

    
    /** Méthodes */
   
    /** Processus d'apprentissage du réseau de neurones : 
     * 	--> On calcule les résultats auxquels on applique la fonction sigmoide
     *  --> On compare les résultats obtenus à ce que l'on devait obtenir : Erreur quadratique
     *  --> On calcule le gradient
     *  --> On modifie les poids
     *  --> On rétropopage sur tout le réseau ( sortie --> entrée ) 
     * @param baseDeConnnaissances 	(float[]) : La base de connaissance utilisée (les entrées)
     * @param resultatsAttendus 	(float[]) : Liste des valeurs attendues (les y°) */
    public void apprentissage(float[] baseDeConnnaissances, float[] resultatsAttendus) {
        
    	/* On calcule les résultats pour toutes les couches du réseau : resultatsCalculees[] contient la liste des sorties APRES la fonction sigmoide */
    	float[] resultatsCalculees = calculerTousResultats(baseDeConnnaissances);
    	
    	
    	/* On calcule les erreurs : resultatsAttendus - resultatsCalculees */
    	float[] erreurs = new float[resultatsCalculees.length];
    	for (int i = 0; i < erreurs.length; i++) {
    		erreurs[i] = (resultatsAttendus[i] - resultatsCalculees[i]);
        }
    	
    	
    	/* On appelle la fonction apprentissage sur chaques couches pour :
    	 * --> Calculer le gradient
    	 * --> Modifier les poids
    	 * --> Retropopager le gradient et mettre à jours les poids 
    	 * 
    	 *  <---------------------------- RETROPOPAGATION DE LA DROITE VERS LA GAUCHE DU RÉSEAU (i--)
    	 * 
    	 * 	e1--->O----->O----->O------>O s1
    	 * 		 	/   \		 /
    	 * 	e2--->O----->O----->O------>O s2
    	 *      |      |     |       |
    	 *  en- - - - - - - - - - -- etc
    	 * 
    	 * */
        for (int i = _couches.length - 1; i >= 0; i--) {
            erreurs = _couches[i].apprentissage(erreurs, this.TAUX_APPRENTISSAGE);
        }
    }


    /** On demande le calcule des résultat y pour tout les exemples et pour toutes les couches du réseau
     * @param input 		(float[]) : La base de connaissances
     * @return resultats 	(float[]) : La liste des résultats APRES la fonction sigmoide sur ceux ci pour toutes les couches */
    public float[] calculerTousResultats(float[] baseDeConnnaissances) {
    	
    	float[] exemples = baseDeConnnaissances;
        
    	/* Pour toutes les couches on fait appel au calcul des résultats */
    	for (int i = 0; i < _couches.length; i++) {
    		exemples = _couches[i].calculerResultats(exemples);
        }
        
        return exemples;
    }
    
    /** Fonction qui retourne une couche de connexions
     * @param index 	(int) : Entier de l'index de la couche de connexions
     * @return 			(Couche) : La couche de connexions */
    public Couche getLayer(int index) { return _couches[index]; }
    
    
    
    
    /**
     * 
     * Couche du 
     * Reseau 
     * 
     * */
    public class Couche {
    	
    	/** Variables */
    	
        private float[] _s;		// Neurones en sortie
        private float[] _e;		// Neurones en entree
        private float[] _w;		// Poids
        private float[] poidsDelta;
        private Random nbAleat;	// Generateur de nombres aléatoires 
        

        
        /** Constructeur de la couche du réseau 
         * @param nbE 	(int) : Nombre de neurones en entrée
         * @param nbS 	(int) : Nombre de neurones en sortie */
        public Couche(int nbE, int nbS) {
        	
        	/* On initialise les tailles des couches de neurones */
        	_s = new float[nbS];		// On creer "nbS" neurones en sortie
        	_e = new float[nbE + 1];	// On creer "nbE" neurones en entrée + le biais (Neurone qui émet un signal d'intensité 1)

        	/* On initialise les poids */
            _w = new float[_e.length * _s.length];	// Il y a autant de connexions que de neurones en entrée * neurones en sortie
            initialiserPoids();
            
            poidsDelta = new float[_w.length];
            
        }

        /** Méthodes */
        
        /** Fonction qui initialise les poids avec des valeurs comprises
         * entre -0.1 et 0.1 */
        public void initialiserPoids() {
        	this.nbAleat = new Random();
            for (int i = 0; i < _w.length; i++) {
            	_w[i] = (nbAleat.nextFloat() - 0.5f) * 4f;
            }
        }
        
        
        /** Lance les calculs : s[i] = e[i] * w[i] Puis appelle la fonction sigmoïde sur s[i]
         * @param exemples	(float[]) : Liste des valeurs en entrée
         * @return 			(float[]) : Copie de la liste des valeurs calculées APRES la fonction sigmoide sur ceux-ci  */
        public float[] calculerResultats(float[] exemples) {
           
        	/* On copie la liste des données (cf. Les entrées) dans la liste des entrées de la couche. */
            System.arraycopy(exemples, 0, _e, 0, exemples.length);
            
            /* Le biais est un neurone qui émet un signal d'intensité 1 donc on lui donne comme valeur 1 */
            _e[_e.length - 1] = 1;
            
            int decalage = 0;
            
            /* On complete toutes les sorties 
             * 
             *				-> s1 
             * 			   /
             * 		- - ->O-----> s2
             * 			   \	
             * 				-> s3
             * */
            for (int i = 0; i < _s.length; i++) {
            
            	/* Pour tout les neurones en entrée : La sortie s = Les entrées * Les poids
            	 * 
            	 * 	 e1 \w1
            	 *    w2 \
            	 * e2 ---->O- - - -
            	 *       /
            	 *   e3 /w3
            	 * 
            	 * */
                for (int j = 0; j < _e.length; j++) { 
                	_s[i] += _w[decalage + j] * _e[j]; 
                }		
                
                
                /* Apres avoir calculer ces résultats, on calcule la fonction sigmoïde (fonction d'activation) la sortie i :
                 * 
	             *  		    			  -> s1 
	             * 		   _________________ /
	             * -->O-->|FONCTION SIGMOIDE|--> s2
	             * 		   ----------------- \	
	             * 							  -> s3
                 * */
                _s[i] = Sigmoide(_s[i]);
                decalage += _e.length;
            }
            return Arrays.copyOf(_s, _s.length);
        }
        
        
        /** Fonction qui calcule le gradient et modifie les poids par rétropopagation du gradient.
         * @param erreurs 				(float[]) : Liste des erreurs
         * @param tauxApprentissage 	(float)   : Taux d'apprentissage que l'on a donné dans la class Réseau */
        public float[] apprentissage(float[] erreurs, float tauxApprentissage) {
        	
        	int decalage = 0;
        	float[] erreurSuivante = new float[_e.length];
        	
        	// On parcourt le réseau 
        	for (int i = 0; i < _s.length; i++) {
        		for (int j = 0; j < _e.length; j++) {
                
                	int poidsPrecedent = decalage + j;
                	
                	erreurSuivante[j] += + _w[poidsPrecedent] * erreurs[i] * DeriveeSigmoide(_s[i]);
                	
                	// Gradient (Delta) = taux apprentissage * erreurs (resultat attendu - resultat obtenu) * derivée de la sigmoide * l'entrée 
                	float gradient = tauxApprentissage * erreurs[i] * (float) DeriveeSigmoide(_s[i]) * _e[j];
                	
                	// On rétropopage en changent les poids grace au gradient 
                	_w[poidsPrecedent] += gradient * tauxApprentissage + 0.6 * poidsDelta[poidsPrecedent];
                    poidsDelta[poidsPrecedent] = gradient;
                }
                decalage += _e.length;
            }
            return erreurSuivante;
        }
        
        
       
        
	    /** Fonction qui calcule la fonction sigmoide
	     * @param x 	(float) : Valeur
	     * @return 		(float) : Valeur de la sigmoide de x */
	    public float Sigmoide(float x) { return ((float) (1 / (1 + Math.exp(-x)))); }
	
	    
	    /** Fonction qui calcule la dérivée de la fonction sigmoide
	     * @param x 	(float) : Valeur
	     * @return 		(float) : Valeur de la dérivée de la sigmoide de x */
	    public float DeriveeSigmoide(float x) { return (x * (1 - x)); }
    }
}