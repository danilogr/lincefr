/*
     Funções de multplicação e comparação de matrizes que não utilizam
     as vantagens de instruções SIMD
*/


#include "stdafx.h"
#include "matrix.h"

/*
     Multiplica duas matrizes A e B, onde B deve ser a transposta
     da matriz que desejamos multiplicar.
*/
void multiplyMatrix(int **result, int **a, int **b, int order)
{
     // result = A*B
     int register soma = 0;


     for (int register i1 = 0; i1 < order ; i1++)              //para cada linha da matriz A
     {
          
          for (int register i2 = 0; i2 < order; i2++)        //para cada linha da matriz B
          {
               soma = 0;
               for( int register j = 0; j < order; j++)
               {
                 soma += a[i1][j] * b[i2][j];                   //multiplicacao
               }
               result[i1][i2] = soma;
          }

          
     }


}


bool equalxMatrixes(int **a, int **b, int order)
{
     int register j = order;
     j*=j;

     

     for (int register i = 0; i < j; i++)
               if( *(a[0] + i) != *(b[0] + i ))
                         return false;

     return true;
}

bool equalMatrixes(int **a, int **b, int order)
{
     for (int register i = 0; i < order; i++)
          for(int register j = 0; j < order; j++)
               if( a[i][j] != b [i][j])
                         return false;

     return true;
}