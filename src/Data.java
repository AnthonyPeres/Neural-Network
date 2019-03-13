
public class Data {

	/* Attributs */
	
	public double[] attribut; 	// Tableau contenant les attributs de la donnée 
	public String classe;		// Classe de la donnée
	
	/* Constructeur */
	
	public Data(double a1, double a2, double a3, double a4, String classe) {
		this.classe = classe;	
		this.attribut = new double[] {a1, a2, a3, a4};
	}
	
	/* Méthodes */
	
	/* Le résultat que l'on souhaite obtenir suite au test de la classe : ici 0 ou 1 */
	public int getClasseAttendue() {
		
		if(classe.equals("0")) return 0;
		if(classe.equals("1")) return 1;
		return -1;
	}
	
	/* toString */
	public String toString(){
		return (getAttribut(0)+" "+getAttribut(1)+" "+getAttribut(2)+" "+getAttribut(3)+" "+getClasse());
	}
	
	
	/* Accesseurs */
	
	public double getAttribut(int i) {return this.attribut[i];}
	public String getClasse() {return this.classe;}
	
	
	/* Mutateurs */
	
	public void setAttribut(int i, double a) {this.attribut[i] = a;}
	public void setClasse(String c) {this.classe = c;}
	
	
}
