// C++ program to find minimum
// number of denominatio
#include <stdio.h>
#include <vector>
#include <iostream>     
#include <algorithm> 
using namespace std;
 


 
void findMin(int cambio,int monedas[],size_t n)
{
    
    sort(monedas, monedas + n);
 
    
    vector<int> ans;
 
    //Recorre todo el array desde el mayor hasta el menor
    for (int i = n - 1; i >= 0; i--) {
 
        // Explora desde la mayor moneda y va restando hasta que no pueda restar más
        while (cambio >= monedas[i]) {
            cambio -= monedas[i];
            ans.push_back(monedas[i]);
        }
    }
 
    // Imprime el resultado
    cout<<"la cantidad de monedas son "<<ans.size()<<" y son: "<<endl;
    for (int i = 0; i < ans.size(); i++)
    {
        
        cout << ans[i] << " ";
    }
        
    cout<<endl;
}
 
// Main program
int main()
{
    int monedas[] = { 1, 2, 5, 10, 20,
               50, 100, 500, 1000 };
    
    int cambio = 93;
    
    findMin(cambio,monedas,sizeof(monedas)/sizeof(monedas[0]));
    system("PAUSE");
    return 0;
}