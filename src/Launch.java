import java.util.ArrayList;

public class Launch {
	
	
	/* Attributs */
	
	private ArrayList<Data> data;				// Liste de données
	private float[][] baseDeConnaissances;		// Base de connaissance
	private float[][] resultatsAttendus;		// Résultats attendus
	private Fichier f; 							// Class qui permet de retirer les informations d'un fichier 
	private Reseau reseau;						// Le réseau de neurones
	
	
	/* Constructeur */ 
	
	public Launch() {
		
		this.f = new Fichier();
		this.data = this.f.getData();			// On récupere les données du fichier dans la variable data
		
		/* Les 2 matrices basesDeConnaissances et resultats Attendus s'initialisent grace à la fonction initialisation().
		 * La matrice baseDeConnaissances prends comme valeurs tous les attributs du fichier sous la forme (attribut1, attribut2, ..3, ..4)
		 * La matrice resultatsAttendus prends comme valeurs : 1 0 si la classe est 1 ou 0 1 si la classe est 0 */
		this.baseDeConnaissances = new float[data.size()][4];
		this.resultatsAttendus = new float[data.size()][2];
		
		/* Le réseau de neurones dispose de :
		 * *** 4 neurones en entrée car 4 attributs dans le fichier,
		 * *** N neurones cachés (nous pouvons en mettre autant que l'on veut),
		 * *** 2 neurones en sortie car 2 sorties possibles : 0-1 ou 1-0  */
		this.reseau = new Reseau(3,3,2);		
		
		initialisation();
		run();
	}
		
		
	/* Méthodes */
	
	/* Initialisation des matrices */
	private void initialisation() {
		for(int i = 0; i < data.size(); i++) {
			this.baseDeConnaissances[i][0] = (float) data.get(i).getAttribut(0);
			this.baseDeConnaissances[i][1] = (float) data.get(i).getAttribut(1);
			this.baseDeConnaissances[i][2] = (float) data.get(i).getAttribut(2);
			//this.baseDeConnaissances[i][3] = (float) data.get(i).getAttribut(3);
			
			if(data.get(i).getClasse().equals("1")) {
				this.resultatsAttendus[i][0] = 1;
				this.resultatsAttendus[i][1] = 0;
			} else if(data.get(i).getClasse().equals("0")) {
				this.resultatsAttendus[i][0] = 0;
				this.resultatsAttendus[i][1] = 1;
			}
		}
	}
		
	/* Lancement de l'IA */		
	private void run() {
		
		/* On boucle ITERATIONS fois */
        for (int iterations = 0; iterations < Reseau.ITERATIONS; iterations++) {

            //-- Apprentissage
            for (int i = 0; i < resultatsAttendus.length; i++) {
                reseau.apprentissage(baseDeConnaissances[i], resultatsAttendus[i]);
            }

            //-- Affichage des résultats 
            System.out.println("\nPeriode n°" + iterations);
                
            for (int i = 0; i < resultatsAttendus.length; i++) {
                    float[] donnees = baseDeConnaissances[i];
                    float[] sortieCalculee = reseau.calculerTousResultats(donnees);
                   System.out.println(donnees[0] + ", " + donnees[1] + ", " + donnees[2] + " --> " + sortieCalculee[0] + " - " + sortieCalculee[1]);
            }
        }
	}
		
	
	public static void main(String[] args) {
		new Launch();
	}
}