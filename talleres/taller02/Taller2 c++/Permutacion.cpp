#include <iostream>
#include <stdio.h>
#include <algorithm>
#include <string>
#include <vector>

using namespace std;

vector<string> *list =new vector<string>();

int PermutacionConRepCiclos() {
   for (int v = 0; v <= 9; v ++)
      for (int x = 0; x <= 9; x ++)
         for (int y = 0; y <= 9; y ++)
          
                  std::cout << v << x << y << "\n";

   return 0;

}

int PermutacionSinRepCiclos() {
   for (int v = 0; v <= 9; v ++)
      for (int x = 0; x <= 9; x ++)
         for (int y = 0; y <= 9; y ++)
                if (v != x && v != y  && x != y )
                  cout << v << x << y << "\n";

   return 0;

}
void PermutacionConRepConRecursionAUX(string loQueLlevo, string loQueMeFalta, vector<string> *list)
{
    
    if(loQueMeFalta.length()==loQueLlevo.length())
    {
        list->push_back(loQueLlevo);
        return;
    }
    for(int i =0;i<loQueMeFalta.length(); i++)
    {
        PermutacionConRepConRecursionAUX(loQueLlevo+loQueMeFalta.at(i),loQueMeFalta,list);

    } 
}
void PermutacionConRepConRecursion(string APermutar)
{

    PermutacionConRepConRecursionAUX("",APermutar,list);
    return;
    
}

void PermutacionSinRepConRecursionAUX(string loQueLlevo, string loQueMeFalta, vector<string> *list2)
{
    
    if(loQueMeFalta.length()==0)
    {
        list2->push_back(loQueLlevo);
        return;
    }
    for(int i =0;i<loQueMeFalta.length(); i++)
    {
        PermutacionSinRepConRecursionAUX(loQueLlevo+loQueMeFalta.at(i),loQueMeFalta.substr(0,i)+loQueMeFalta.substr(i+1),list2);

    } 
}

void PermutacionSinRepConRecursion(string APermutar, vector<string> *list2)
{

    PermutacionSinRepConRecursionAUX("",APermutar,list2);
    return;
    
}

void CombinacionAux(string pre, string pos, vector<string> *list2)
{
    if(pos.length()==0)
    {
        list2->push_back(pre);
        return;
    }
    else{
        CombinacionAux(pre+pos.substr(0,1),pos.substr(1),list2);
        CombinacionAux(pre,pos.substr(1),list2);
    }
}

void Combinacion(string ACombinar, vector<string> *list2)
{
    CombinacionAux("",ACombinar, list2);
}

int main(int argc, char const *argv[])
{
    
    PermutacionConRepConRecursion("abc");
    cout<<"["<<" ";
    for(string s : *list)
    {
        
        cout<<s<<" "<<","<<" ";
    }
    cout<<" ]"<<endl;
    vector<string> *lista= new vector<string>();

    PermutacionSinRepConRecursion("abc",lista);
    cout<<"["<<" ";
    for(string s : *lista)
    {
        
        cout<<s<<" "<<","<<" ";
    }
    cout<<" ]"<<endl;

    vector<string> *lista2= new vector<string>();
    Combinacion("abc",lista2);
      cout<<"["<<" ";
    for(string s : *lista2)
    {
        
        cout<<s<<" "<<","<<" ";
    }
    cout<<" ]"<<endl;
    system("pause");
    return 0;
}
