// ProjetoFinal.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "util\matrix.h"
#include "util\nonsimd.h"
#include "util\sse2.h"
#include <Windows.h>
#include <WinBase.h>

#define REPETICOES 500


int _tmain(int argc, _TCHAR* argv[])
{
     //OBJETIVO PROVAR =  A(BC) = (AB)C
     int **mA;
     int **mB;
     int **mC;
     int **resultado1,**resultado2;
     int **parte1;

     //int tamanho[] = {4,16,64,128};

     int tamanho[] = {5,6,7,8};

     int initialTime, finalTime;
     double tempoTotal,tempoTotalSSE2;
     int totalCertos,totalCertosSSE2;
     int max,min,maxSSE2,minSSE2;


     // gera a semente para os numeros aleatorios
     srand((unsigned int)time(NULL));
     system("PAUSE");
    

     /*
          Adicionar a verificação da disponibilidade do SSE2 através do CPUID
     
     */


     /*
         Teremos um loop que percorrerá o vetor tamanhos para definir os tamanhos das matrizex
     */
     //
     printf("Realizando um total de %d testes por tamanho por tipo de instrucao\n\n",REPETICOES);

     bool resultado;
     int  tamanhoAtual;
     for(int i = 0; i< 4; i++)
     {
          printf("Teste tamanho %4d",tamanho[i]);
          tamanhoAtual = tamanho[i];
                    // Aloca as tres matrizes de calculo
          mA = newxMatrix(tamanhoAtual);
          mB = newxMatrix(tamanhoAtual);
          
          fillMatrix(mA,tamanhoAtual);
          fillMatrix(mB,tamanhoAtual);
          
          resultado1  = newxMatrix(tamanhoAtual);
          resultado2  = newxMatrix(tamanhoAtual);
          transposexMatrix(mB, tamanhoAtual);

          SSE2_multiplyxMatrix(resultado1,mA,mB,tamanhoAtual);
          multiplyMatrix(resultado2,mA,mB,tamanhoAtual);

          if (SSE2_equalxMatrixes(resultado1,resultado2,tamanhoAtual))
          {
               printf(" - [OK]\n");
          }
       


          deletexMatrix(mA);
          deletexMatrix(mB);
          deletexMatrix(resultado2);
      

          deletexMatrix(resultado1);

          system("PAUSE");

     }

     for(int i = 0; i < 4; i++)
     {
          tamanhoAtual = tamanho[i];

 
          // Aloca as tres matrizes de calculo
          mA = newxMatrix(tamanhoAtual);
          mB = newxMatrix(tamanhoAtual);
          mC = newxMatrix(tamanhoAtual);

          // Aloca as matrizes utilizadas para os resultados das multplicações
          resultado1  = newxMatrix(tamanhoAtual);
          resultado2  = newxMatrix(tamanhoAtual);
          parte1      = newxMatrix(tamanhoAtual);
     
          // Inicializa as variaveis de estatistica
          totalCertos = 0;
          totalCertosSSE2 = 0;
          tempoTotal = 0;
          tempoTotalSSE2 = 0;

          
          
          /*
              Para cada tamanho, executamos REPETICOES iterações que nos
              permitem avaliar melhor o tempo consumido pelo sistema
          */

          // Loop de contagem
          for(int j = 0; j < REPETICOES; j++)
          {


               // Preenche as matrizes com valores aleatorios
               fillMatrix(mA,tamanhoAtual);
               fillMatrix(mB,tamanhoAtual);
               fillMatrix(mC,tamanhoAtual);
               /*
                   Multiplicação de matrizes sem o auxilio de instruções SIMD
               */
               
               // Inicia a contagem de tempo
               initialTime = GetTickCount();

               // Parte esquerda
               // A(BC) = Ax(BC)' = Ax(C'B') = Ax(C'xB)
               transposexMatrix(mC,tamanhoAtual);
               multiplyMatrix(parte1,mC,mB,tamanhoAtual);
               multiplyMatrix(resultado1,mA,parte1,tamanhoAtual);

               // Parte direita
               // (AB)C = (AxB')xC'
               transposexMatrix(mB,tamanhoAtual);
               multiplyMatrix(parte1,mA,mB,tamanhoAtual);
               multiplyMatrix(resultado2,parte1,mC,tamanhoAtual);

               // Rearranja as matrizes
               transposexMatrix(mB,tamanhoAtual);
               transposexMatrix(mC,tamanhoAtual);

               // Analisa se as partes sao iguais
               resultado = equalxMatrixes(resultado1,resultado2,tamanhoAtual);

               // Obtem o tempo final
               finalTime = GetTickCount();
               finalTime -= initialTime;
               
               // Calcula os maximos e minimos
               if(j == 0)
               {
                    max = finalTime;
                    min = finalTime;
               } else {
                    if(finalTime > max)
                         max = finalTime;
                    if(finalTime < min)
                         min = finalTime;
               }

               // registra o valor
               tempoTotal += finalTime;

               // Anota os dados
               if (resultado)
                    totalCertos++;

               

               /*
                  Multiplicação de matrizes com auxilo de SIMD -> SSE2
               */

               initialTime = GetTickCount();

               // Parte 1
               // A(BC) = Ax(BC)' = Ax(C'B') = Ax(C'xB)
               transposexMatrix(mC,tamanhoAtual);
               SSE2_multiplyxMatrix(parte1,mC,mB,tamanhoAtual);
               SSE2_multiplyxMatrix(resultado1,mA,parte1,tamanhoAtual);

               // Parte 2
               // (AB)C = (AxB')xC'
               transposexMatrix(mB,tamanhoAtual);
               SSE2_multiplyxMatrix(parte1,mA,mB,tamanhoAtual);
               SSE2_multiplyxMatrix(resultado2,parte1,mC,tamanhoAtual);

               // Rearranja as matrizes
               transposexMatrix(mB,tamanhoAtual);
               transposexMatrix(mC,tamanhoAtual);

               // Compara as partes 1 e 2
               resultado = SSE2_equalxMatrixes(resultado1,resultado2,tamanhoAtual);

               // Obtem a diferença de tempos
               finalTime = GetTickCount();
               finalTime -= initialTime;

               // obtem maximo e minimo
               if(j == 0)
               {
                    maxSSE2 = finalTime;
                    minSSE2 = finalTime;
               } else {
                    if(finalTime > maxSSE2)
                         maxSSE2 = finalTime;
                    if(finalTime < minSSE2)
                         minSSE2 = finalTime;
               }

               // Salva o valor
               tempoTotalSSE2 += finalTime;

                // Anota os dados
               if (resultado)
                    totalCertosSSE2++;

        

          }

          //Escreve os resultados na tela
          
          // 
          // Obtem a media de tempo
          //

          tempoTotalSSE2 /= REPETICOES;
          tempoTotal     /= REPETICOES;


          printf("Normal [%03d] - MEDIA DE %7.2f ms (%04d - %04d ms)- %05.2f%% Acertos\n",tamanhoAtual,tempoTotal,min,max,((float)totalCertos / REPETICOES)*100);
          printf("SSE2   [%03d] - MEDIA DE %7.2f ms (%04d - %04d ms)- %05.2f%% Acertos\n",tamanhoAtual,tempoTotalSSE2,minSSE2,maxSSE2,((float)totalCertosSSE2 / REPETICOES)*100);
          printf("\n");


          //Desaloca as matrizes da memoria
          deletexMatrix(mA);
          deletexMatrix(mB);
          deletexMatrix(mC);

          deletexMatrix(resultado1);
          deletexMatrix(resultado2);
          deletexMatrix(parte1);
                
          

     }

	return 0;

}
