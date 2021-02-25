/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Jose Manuel Fonseca, Santiago Puerta
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
public class GrafosMatriz extends Grafo{

	/**
	* Constructor para el grafo dirigido
	* @param size el numero de vertices que tendra el grafo dirigido
	*
	*/
	Double[][] mat;
	public GrafosMatriz(int size, HashMap<Integer,Vertice> claves) {
        super(size, claves);
        
		mat = new Double[size+1][size+1];
		for(int i=1; i<=size; i++){
            
            mat[i][0] = (double)claves.get(i).getCodigo();
            mat[0][i] = (double)claves.get(i).getCodigo();
        }
	}

	/**
	* Metodo para añadir un arco nuevo, donde se representa cada nodo con un entero
	* y se le asigna un peso a la longitud entre un nodo fuente y uno destino	
	* @param source desde donde se hara el arco
	* @param destination hacia donde va el arco
	* @param weight el peso de la longitud entre source y destination
	*/
	public void addArc(int source, int destination, Peso weight) {
		
		mat[source][destination] = weight.getDistancia();
	}

	/**
	* Metodo para obtener una lista de hijos desde un nodo, es decir todos los nodos
	* asociados al nodo pasado como argumento
	* @param vertex nodo al cual se le busca los asociados o hijos
	* @return todos los asociados o hijos del nodo vertex, listados en una ArrayList
	*/
	public ArrayList<Integer> getSuccessors(int vertex,HashMap<Integer,Vertice> mapa) {
		ArrayList<Integer> np= new ArrayList<>();
     	for(int i=1; i<=size; i++){
        	if(mat[vertex][i]!=  (double)0 ){
         		np.add(mapa.get(i).getCodigo());
        	}
      	}	
     	return np; 
	}

	/**
	* Metodo para obtener el peso o longitud entre dos nodos
	* 
	* @param source desde donde inicia el arco
	* @param destination  donde termina el arco
	* @return un entero con dicho peso
	*/	
	public double getWeight(int source, int destination) {
		return mat[source][destination];
	}
	public void imprimirGrafo()
	{
		System.out.println(Arrays.deepToString(mat));
	}
}