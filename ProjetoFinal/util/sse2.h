#pragma once

/*
    Nesse header as fun��es de multiplica��o e compara��o de matrizes
    que utilizam o conjuntos de instru��es SIMD SSE2 s�o declaradas

*/


void SSE2_multiplyxMatrix(int **result, int **a, int **b, int order);

bool SSE2_equalMatrixes(int **a, int **b, int order);
bool SSE2_equalxMatrixes(int **a, int **b, int order);

