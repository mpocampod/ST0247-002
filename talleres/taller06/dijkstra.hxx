// autor Jhosimar George Arias Figueroa 
#define MAX 10005 
#define Node pair< int , int > 
#define INF 1<<30 
vector< Node > ady[ MAX ]; 
int distancia[ MAX ];     
int previo[ MAX ];        
priority_queue< Node , vector<Node> , cmp > Q; 
int V;                     

void init(){
    for( int i = 0 ; i <= V ; ++i ){
        distancia[ i ] = INF;  
        visitado[ i ] = false; 
        previo[ i ] = -1;  
    }
}
Q.push( Node( inicial , 0 ) ); 
distancia[ inicial ] = 0;      
while( !Q.empty() ){                  
        actual = Q.top().first;           
        Q.pop();                          
if( visitado[ actual ] ) continue; 
visitado[ actual ] = true;        
for( int i = 0 ; i < ady[ actual ].size() ; ++i ){ 
    adyacente = ady[ actual ][ i ].first;  
    peso = ady[ actual ][ i ].second;        
    if( !visitado[ adyacente ] ){ 
    for( int i = 0 ; i < ady[ actual ].size() ; ++i ){
    adyacente = ady[ actual ][ i ].first;  
    peso = ady[ actual ][ i ].second;      
    if( !visitado[ adyacente ] ){       
        relajacion( actual , adyacente , peso );
    }
}

void relajacion( int actual , int adyacente , int peso ){
    
    if( distancia[ actual ] + peso < distancia[ adyacente ] ){
        distancia[ adyacente ] = distancia[ actual ] + peso;  
        previo[ adyacente ] = actual;                        
        Q.push( Node( adyacente , distancia[ adyacente ] ) ); 
    }
}

void print( int destino ){
    if( previo[ destino ] != -1 )    
        print( previo[ destino ] );  
    printf("%d " , destino );        
}
