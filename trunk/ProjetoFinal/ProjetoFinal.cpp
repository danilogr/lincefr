// ProjetoFinal.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "util\matrix.h"
#include "util\nonsimd.h"
#include "util\sse2.h"
#include <Windows.h>
#include <WinBase.h>


int _tmain(int argc, _TCHAR* argv[])
{
     //OBJETIVO PROVAR =  A(BC) = (AB)C
     int **mA;
     int **mB;
     int **mC;
     int **resultado1,**resultado2;
     int **parte1;

     int tamanho[] = {4,16,64,101,256};
     int initialTime, finalTime;



     /*
         Teremos um loop que percorrerá o vetor tamanhos para definir os tamanhos das matrizex
     */
     //

     for(int i = 0; i < 5; i++)
     {
          int tamanhoAtual = tamanho[i];
          
          // Aloca as tres matrizes de calculo
          mA = newMatrix(tamanhoAtual);
          mB = newMatrix(tamanhoAtual);
          mC = newMatrix(tamanhoAtual);

          // Aloca as matrizes utilizadas para os resultados das multplicações
          resultado1  = newMatrix(tamanhoAtual);
          resultado2  = newMatrix(tamanhoAtual);
          parte1      = newMatrix(tamanhoAtual);

          // Preenche as matrizes com valores aleatorios
          fillMatrix(mA,tamanhoAtual);
          fillMatrix(mB,tamanhoAtual);
          fillMatrix(mC,tamanhoAtual);

          /*
              Multiplicação de matrizes sem o auxilio de instruções SIMD
          */

          printf("Normal [%03d] - ",tamanhoAtual);

          initialTime = GetTickCount();

          // A(BC) = Ax(BC)' = Ax(C'B') = Ax(C'xB)
          squareTranspose(mC,tamanhoAtual);
          multiplyMatrix(parte1,mC,mB,tamanhoAtual);
          multiplyMatrix(resultado1,mA,parte1,tamanhoAtual);

          // (AB)C = (AxB')xC'
          squareTranspose(mB,tamanhoAtual);
          multiplyMatrix(parte1,mA,mB,tamanhoAtual);
          multiplyMatrix(resultado2,parte1,mC,tamanhoAtual);


          squareTranspose(mB,tamanhoAtual);
          squareTranspose(mC,tamanhoAtual);

          finalTime = GetTickCount();

          finalTime -= initialTime;

          if (equalMatrixes(resultado1,resultado2,tamanhoAtual))
               printf("[OK]  ");
          else
               printf("[FAIL]");

          printf(" - %05d ms\n", finalTime);

          /*
             Multiplicação de matrizes com auxilo de SIMD -> SSE2
          */

           printf("SSE2   [%03d] - ",tamanhoAtual);

          initialTime = GetTickCount();

          // A(BC) = Ax(BC)' = Ax(C'B') = Ax(C'xB)
          squareTranspose(mC,tamanhoAtual);
          SSE2_multiplyMatrix(parte1,mC,mB,tamanhoAtual);
          SSE2_multiplyMatrix(resultado1,mA,parte1,tamanhoAtual);

          // (AB)C = (AxB')xC'
          squareTranspose(mB,tamanhoAtual);
          SSE2_multiplyMatrix(parte1,mA,mB,tamanhoAtual);
          SSE2_multiplyMatrix(resultado2,parte1,mC,tamanhoAtual);


          squareTranspose(mB,tamanhoAtual);
          squareTranspose(mC,tamanhoAtual);

          finalTime = GetTickCount();

          finalTime -= initialTime;

          if (SSE2_equalMatrixes(resultado1,resultado2,tamanhoAtual))
               printf("[OK]  ");
          else
               printf("[FAIL]");

          printf(" - %05d ms\n", finalTime);

          //Desaloca as matrizes da memoria
          deleteMatrix(mA);
          deleteMatrix(mB);
          deleteMatrix(mC);
          deleteMatrix(resultado1);
          deleteMatrix(resultado2);
          deleteMatrix(parte1);
                
          printf("\n");

     }

	return 0;

}
/*

void F1(*A, *B, *C, *D)
{
	// código
	return
}


void F2(*A, *B, *C, *D)
{
	// código

	__asm{


	}

	return
}

*/