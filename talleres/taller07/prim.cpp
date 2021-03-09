/* Dados un grafo G, sus pesos W y el número de nodos n devuelve el coste del árbol mínimo
*generador usando el algoritmo de Prim
*Tomado de mbalsells
*/
int Prim (vvi& G, vvi& W, int n){
    vector <bool> vis(n, false); //nodos visitados, inicialmente ninguno
    priority_queue <ii> Q; //cola de prioridad de parejas de enteros (-distancia del nodo a F, nodo)
    Q.push({-0,0}); //introducimos un nodo cualquiera (a distancia 0 de él mismo)
    int answer = 0; 

    while (not Q.empty()){
        ii arc = Q.top() //arco con menor peso desde F hasta G\F 
        Q.pop(); //lo quitamos de la cola

        int v = arc.second; //vértice de Q a menor distancia de F
        int p = -arc.first; //distancia entre F y v

        if (not vis[v]){ //si no lo hemos visitado
            vis[v] = true;
            answer += p;

            for (int i = 0; i < G[v].size(); ++i){ //miramos sus vecinos
                int u = G[v][i];
                int w = W[v][i];

                Q.push({-w, u}); // añadimos los vecinos conectados con u
            }
        }   
    }

    return answer; // devolvemos el coste
}