/*
     Nesse header as fun��es de multiplica��o 
     e compara��o de matrizes que n�o utilizam
     as rotinas SSE2 s�o declaradas
*/


#pragma once



void multiplyMatrix(int **result, int **a, int **b, int order);

bool equalMatrixes(int **a, int **b, int order);