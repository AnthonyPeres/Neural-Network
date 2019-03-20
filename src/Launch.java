import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Launch {
	
	public static void main(String[] args) throws Exception {
		
		/** Les arguments */
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel est la base de donnée à étudier ? : ");
		String nomDuFichier = sc.nextLine();
		/*************************/


		int NombreAttributs = 0;
		
		ArrayList<String> classe = new ArrayList<String>();
		

		
		BufferedReader br1 = new BufferedReader(new FileReader("BDD/"+nomDuFichier));

		
		ArrayList<String> texte = new ArrayList<String>();
		String ligne;
		int compteurLignes = 0;
		String[] separe;


		/** PARCOURS ET STOCKAGE DU FICHIER, INITIALISATION DES VARIABLES "NOMBREATTRIBUTS" "CLASSE" */
		
		while((ligne = br1.readLine()) != null) {
			separe = ligne.split(",");

			// Des la premiere ligne on va recuperer le nombre d'attributs et la classe
			if(compteurLignes == 0){
				NombreAttributs = separe.length - 1;
				classe.add(separe[separe.length-1]);
			}

			// On va regarder combien de resultats (sorties) nous avons, en les comparants
			if(!classe.contains(separe[separe.length-1])){
				classe.add(separe[separe.length-1]);
			}

			texte.add(ligne);
			compteurLignes++;
		} br1.close();

	
		/** Creation des 2 matrices grace aux informations soutirées dans le texte (nombre lignes, nombre attributs...) */
		float[][] baseDeConnaissances = new float[compteurLignes][NombreAttributs];
		float[][] resultatsAttendus = new float[compteurLignes][classe.size()];

		for(int i = 0; i < compteurLignes; i++){

			ligne = texte.get(i);
			separe = ligne.split(",");

			// On met les attributs dans la base de connaissances
			for(int k = 0; k < NombreAttributs ; k++){
				baseDeConnaissances[i][k] = Float.parseFloat(separe[k]);
			}

			for(int c = 0; c < classe.size(); c++){
				if(classe.get(c).equals(separe[separe.length-1])){
					resultatsAttendus[i][c] = 1;
				} else {
					resultatsAttendus[i][c] = 0;
				} 
			}
		}

		
		
		/************* CREATION DU RESEAU : 4 COUCHES ENTRÉES --> 6 COUCHES CACHEES --> 3 COUCHES SORTIES *************/
		
		Reseau reseau = new Reseau(NombreAttributs,6,classe.size());
			
		/****************************** ACTIVATION DU RÉSEAU ET DONC DE L'APPRENTISSAGE *******************************/
		
        for (int iterations = 0; iterations <= Reseau.ITERATIONS; iterations++) {

            //-- Apprentissage
            for (int i = 0; i < resultatsAttendus.length; i++) {
                reseau.apprentissage(baseDeConnaissances[i], resultatsAttendus[i]);
            }

            //-- Affichage des résultats 
            if(iterations % 10 == 0 || iterations == Reseau.ITERATIONS) {
	            System.out.println("\nPeriode n°" + iterations);
	                
	            for(int i = 0; i < resultatsAttendus.length; i++) {
	            	float[] donnees = baseDeConnaissances[i];
	                float[] sortieCalculee = reseau.calculerTousResultats(donnees);
					
					

					String concatDonnees = "";
					for(int l = 0; l < donnees.length; l++){
						if(l == 0){
							concatDonnees = concatDonnees +donnees[l];  
						} else {
							concatDonnees = concatDonnees +","+ donnees[l];  
						}
					}


					String concatSortie = "";
					for(int m = 0; m < sortieCalculee.length; m++){
						if(m == 0){
							concatSortie = concatSortie + Math.round(sortieCalculee[m]);  
						} else {
							concatSortie = concatSortie +","+ Math.round(sortieCalculee[m]);  
						}
					}


					// On renvoie le resultat 
					System.out.println(concatDonnees +" --> "+ concatSortie);				
				}
            }
		}
	}
}