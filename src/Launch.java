import java.io.BufferedReader;
import java.io.FileReader;

public class Launch {
	
	public static void main(String[] args) throws Exception {
		
		
		/***************************** LECTURE DU FICHIER POUR REMPLISSAGE DES 2 MATRICES *****************************/
		
		float[][] baseDeConnaissances = new float[150][4];
		float[][] resultatsAttendus = new float[150][3];
		
		int compteur = 0;
		String s;
		String[] separe;
		BufferedReader br = new BufferedReader(new FileReader("src/iris.data.txt"));
		
		br.readLine(); 
		while((s = br.readLine()) != null) {
			separe = s.split(",");
			
			baseDeConnaissances[compteur][0] = Float.parseFloat(separe[0]);
			baseDeConnaissances[compteur][1] = Float.parseFloat(separe[1]);
			baseDeConnaissances[compteur][2] = Float.parseFloat(separe[2]);
			baseDeConnaissances[compteur][3] = Float.parseFloat(separe[3]);
			
			String classe = separe[4];
			
			if(classe.equals("Iris-setosa")) {
				resultatsAttendus[compteur][0] = 1;
				resultatsAttendus[compteur][1] = 0;
				resultatsAttendus[compteur][2] = 0;
			} else if(classe.equals("Iris-versicolor")) {
				resultatsAttendus[compteur][0] = 0;
				resultatsAttendus[compteur][1] = 1;
				resultatsAttendus[compteur][2] = 0;
			} else if(classe.equals("Iris-virginica")) {
				resultatsAttendus[compteur][0] = 0;
				resultatsAttendus[compteur][1] = 0;
				resultatsAttendus[compteur][2] = 1;
			}
			compteur++;
		}
		br.close();
			
		
		/************* CREATION DU RESEAU : 4 COUCHES ENTRÉES --> 6 COUCHES CACHEES --> 3 COUCHES SORTIES *************/
		
		Reseau reseau = new Reseau(4,6,3);
			
		/****************************** ACTIVATION DU RÉSEAU ET DONC DE L'APPRENTISSAGE *******************************/
		
        for (int iterations = 0; iterations < Reseau.ITERATIONS; iterations++) {

            //-- Apprentissage
            for (int i = 0; i < resultatsAttendus.length; i++) {
                reseau.apprentissage(baseDeConnaissances[i], resultatsAttendus[i]);
            }

            //-- Affichage des résultats 
            if((iterations +1 ) % 1000 == 0 ||iterations == Reseau.ITERATIONS) {
	            System.out.println("\nPeriode n°" + iterations);
	                
	            for(int i = 0; i < resultatsAttendus.length; i++) {
	            	float[] donnees = baseDeConnaissances[i];
	                float[] sortieCalculee = reseau.calculerTousResultats(donnees);
	                System.out.println(donnees[0]+","+donnees[1]+","+donnees[2]+","+ donnees[3]+" - s -> "+Math.round(sortieCalculee[0])+" - "+Math.round(sortieCalculee[1])+" - "+Math.round(sortieCalculee[2]));
	            }
            }
        }	
	}
}