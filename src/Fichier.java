import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fichier {

	/* Attributs */
	
	public ArrayList<Data> data;
	
	
	/* Constructeur */
	public Fichier() {
		
		this.data = new ArrayList<Data>();
		
		try {
			data = lectureFichier("src/test.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Méthodes */
	
	public ArrayList<Data> lectureFichier(String fichier) throws IOException {
		
		BufferedReader br;
		String s;
		String[] separe;
		
		br = new BufferedReader(new FileReader(fichier));
		System.out.println("Lecture du fichier"+fichier);
		
		
		br.readLine(); // On passe la premiere ligne car c'est celle qui contient les noms des attributs 
		
		/* On lis le fichier pour recuperer les valeurs */
		while((s = br.readLine()) != null) {
			
			/* Le séparateur est une virgule */
			separe = s.split(",");
			
			/* On stock les valeurs */
			double val1 = Double.parseDouble(separe[0]);
			double val2 = Double.parseDouble(separe[1]);
			double val3 = Double.parseDouble(separe[2]);
			//double val4 = Double.parseDouble(separe[3]);
			
			String classe = separe[3];
			
			//Data d = new Data(val1, val2, val3, val4, classe);
			Data d = new Data(val1, val2, val3, classe);
			
			this.data.add(d);	
		}
		br.close();
		System.out.println("Lecture du fichier terminée, la liste data est complétée.");
		return data;
	}
	
	
	/* Accesseurs */
	public ArrayList<Data> getData(){
		return this.data;
	}
	
}
