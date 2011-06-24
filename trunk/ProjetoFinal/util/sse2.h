#pragma once

/*
    Nesse header as funções de multiplicação e comparação de matrizes
    que utilizam o conjuntos de instruções SIMD SSE2 são declaradas

*/


void SSE2_multiplyxMatrix(int **result, int **a, int **b, int order);

bool SSE2_equalMatrixes(int **a, int **b, int order);
bool SSE2_equalxMatrixes(int **a, int **b, int order);

