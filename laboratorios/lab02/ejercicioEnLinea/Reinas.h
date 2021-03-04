#include <iostream>
#include <stdio.h>
#include <vector>
#pragma once 

using namespace std;

class Reinas {
  public:    // Public access specifier
    int nReinas(int n,vector<pair<int,int>> *restriccion);
    int nReinas(int n);
    void imprimirTablero(vector<string> *lista);   // Public attribute
  private:   // Private access specifier
    int nReinas(int c, int n, int tablero[],vector<pair<int,int>> *restriccion);
    int nReinas(int c, int n, int tablero[]);  
     // Private attribute
};