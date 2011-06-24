#pragma once

/*
    Rotinas uteis para se trabalhar com xMatrix.
    A xMatrix � uma matriz dinamicamente alocada que utiliza apenas um bloco continuo de memoria.
    Essa matriz possui algumas vantagens em rela��o a matrix dinamicamente alocada comum (que � que cada linha
    � alocada como um novo bloco de memoria)
    As xMatrixes podem ser utilizadas como matrizes comuns.


*/


 void transposexMatrix(int **matrix, unsigned int order);
 int **newxMatrix(int order);
 void deletexMatrix(int **matrix);

 void printMatrix(int **matrix, int order);

 void fillMatrix(int **matrix, int order);

