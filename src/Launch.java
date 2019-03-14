import java.io.BufferedReader;
import java.io.FileReader;

public class Launch {
	
	
		
	public static void main(String[] args) throws Exception {
		
		int compteur = 0;
		
		float[][] baseDeConnaissances = new float[8][4];
		float[][] resultatsAttendus = new float[8][2];
		
		
		BufferedReader br;
		String s;
		String[] separe;
			
		br = new BufferedReader(new FileReader("src/test.csv"));
		System.out.println("Lecture du fichier : src/test.csv");
			
			
		br.readLine(); // On passe la premiere ligne car c'est celle qui contient les noms des attributs 
			
		/* On lis le fichier pour recuperer les valeurs */
		while((s = br.readLine()) != null) {
				
				/* Le séparateur est une virgule */
				separe = s.split(",");
				
				baseDeConnaissances[compteur][0] = Float.parseFloat(separe[0]);
				baseDeConnaissances[compteur][1] = Float.parseFloat(separe[1]);
				baseDeConnaissances[compteur][2] = Float.parseFloat(separe[2]);
				baseDeConnaissances[compteur][3] = Float.parseFloat(separe[3]);
				
				float classe = Float.parseFloat(separe[4]);
				
				if(classe == 1.0) {
					resultatsAttendus[compteur][0] = 1;
					resultatsAttendus[compteur][1] = 1;
				} else if(classe == 0.0) {
					resultatsAttendus[compteur][0] = 0;
					resultatsAttendus[compteur][1] = 0;
				}
				compteur++;
			}
			br.close();
			System.out.println("Lecture du fichier terminée, la liste data est complétée.");
		
		
		Reseau reseau = new Reseau(4,4,2);
		
	
		/* 
		 * Debut de l'apprentissage
		 * On boucle ITERATIONS fois 
		 * */
        for (int iterations = 0; iterations < Reseau.ITERATIONS; iterations++) {

            //-- Apprentissage
            for (int i = 0; i < resultatsAttendus.length; i++) {
                reseau.apprentissage(baseDeConnaissances[i], resultatsAttendus[i]);
            }

            //-- Affichage des résultats 
            if(iterations % 50 == 0) {
	            System.out.println("\nPeriode n°" + iterations);
	                
	            for (int i = 0; i < resultatsAttendus.length; i++) {
	                    float[] donnees = baseDeConnaissances[i];
	                    float[] sortieCalculee = reseau.calculerTousResultats(donnees);
	                    System.out.println(donnees[0] + ", " + donnees[1] + ", " + donnees[2] + " --> " + sortieCalculee[0] + " - " + sortieCalculee[1]);
	            }
            }
        }	
	}
}