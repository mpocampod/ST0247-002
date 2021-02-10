#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>
#include "Reinas.h"
#include <sstream> 

bool ponerReina(int tablero[], int r,int c,int n){


    
    
    int x=0;
    for(int i=0;i<c;i++)
    {
        x=c-i;
        if(tablero[i]==r+x||tablero[i]==r-x)
        {
            return false;
        }
    }
    return true;
}

void imprimirTablero(vector<string> *lista);
void PermutacionSinRepConRecursionAUX(string loQueLlevo, string loQueMeFalta, vector<string> *list2, int n)
{
    
    if(loQueMeFalta.length()==0)
    {
        list2->push_back(loQueLlevo);
        return;
    }
    int tablero[n]={0};
    for(int i =0;i<loQueMeFalta.length(); i++)
    {   
        
        int j=0;
        
        
        for(char c: loQueLlevo)
        {
            
            string juan = loQueLlevo.substr(j,1);
            stringstream pablo(juan); 
            int r = 0; 
            pablo >> r; 
            tablero[j]=r;
            j++;
        }
        
        string reina = loQueMeFalta.substr(i,1);
        stringstream geek(reina); 
        int r = 0; 
        geek >> r; 
        if(ponerReina(tablero,r,loQueLlevo.size(),n ))
        {
            
            PermutacionSinRepConRecursionAUX(loQueLlevo+loQueMeFalta.at(i),loQueMeFalta.substr(0,i)+loQueMeFalta.substr(i+1),list2,n);
        }
        

    } 
}

int Reinas::nReinas(int c, int n, int tablero[]){

    string opciones= "";
    for(int i=1 ;i<=n;i++)
    {
        string p= to_string(i);
        opciones= opciones+p;
    }
    vector<string> *list= new vector<string>();

    PermutacionSinRepConRecursionAUX("", opciones, list,n);
    imprimirTablero(list);
    return list->size();

}





int Reinas::nReinas(int n){
    int tablero[n];
    
    return nReinas(0, n, tablero);
}


void Reinas::imprimirTablero(vector<string> *lista){
    for(string s : *lista)
    {
        
        cout<<s<<" "<<","<<" ";
    }
    cout<<" ]"<<endl;
}

int main(int argc, char const *argv[])
{
    Reinas *prueba= new Reinas();
    int x;
    cout<<"Con cuantas reinas quiere intentar el problema?"<<endl;
    cin>> x;
    cout<<prueba->nReinas(x)<<endl;
    system("PAUSE");
    return 0;
}

