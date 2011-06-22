/*
     Nesse header as funções de multiplicação 
     e comparação de matrizes que não utilizam
     as rotinas SSE2 são declaradas
*/


#pragma once



void multiplyMatrix(int **result, int **a, int **b, int order);

bool equalMatrixes(int **a, int **b, int order);