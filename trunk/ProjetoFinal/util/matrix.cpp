#include "stdafx.h"
#include "matrix.h"


void squareTranspose(int **matrix, unsigned int order)
{
       
     __asm
     {
          ;modificar esi, edi, ebp
          ; trocar Aij por Aji

          push edi                      //
          push esi                      // 
          push ebp                      //

          mov esi, [matrix]             //
          mov edi, [esi]                // primeiro ponteiro apontado por matrix ( matrix[0] )

          mov esi, order                // ordem em ESI

          mov ecx, 0                    // Linha em ecx
LoopLinha:
          mov ebx, ecx                  // Coluna em ebx (começa na coluna indicada pela linha)
          inc ebx                       // vai para a proxima coluna (sai da diagonal)

LoopColuna:
          ; Operacao de troca
          

          ;
          ;  mov ebp,matrix[linha,coluna]
          ;
          mov eax, esi                  // ordem em eax
          mul ecx                       // multiplica pela linha atual
          shl eax,2                     // multiplica por 4
          add eax,edi                   // adiciona a posicao da matrix
          mov ebp,[eax + 4*ebx]         // pega o valor numerico
        
          ;
          ;  xchg ebp,matrix[coluna,linha]
          ;
          mov eax, esi                  // ordem em eax
          mul ebx                       // multiplica pela coluna atual
          shl eax,2                     // multiplica por 4
          add eax,edi                   // adiciona a posicao da matriz
          xchg ebp,[eax + 4*ecx]        // troca os valores

          ;
          ; mov matrix[linha,coluna],ebp
          ;
          mov eax, esi                  //
          mul ecx
          shl eax,2
          add eax,edi
          mov [eax + 4*ebx],ebp         // salva o outro valor

          inc ebx
          cmp ebx,esi
          jb  LoopColuna

// verificacao do loop das linhas

          inc  ecx                      // incrementa a linha atual
          dec  esi                      // (nao modifica o carry)
          cmp  ecx, esi                 // se for
          inc  esi                      // (nao modifica carry)
          jb   LoopLinha                //        menor que a ultima linha, continua no loop
          // jb = jc


          pop ebp                       //
          pop esi                       //
          pop edi                       //

          mov eax,0xCAFEBABE
     }

}






/*
    Rotina que reserva um espaço na memoria com tamanho order*order
    E depois redistribui os ponteiros para que o espaço seja utilizado
    como uma matriz

*/
int **newMatrix(int order)
{
     int **firstpos;
     int *reservedMemory =  (int *)_aligned_malloc(order*order*sizeof(int),16);

     if (reservedMemory == NULL)
     {
              printf("Fatal Error: Memory allocation error!");
              return NULL;
     }

//     new int[order*order];


     firstpos = new int*[order];



     for(int register i = 0; i < order; i++)
     {
          //Note, na linha abaixo, que i não está multiplicado
          //pelo numero de bytes que possui um inteiro (o compilador faz isso automagicamente)

          firstpos[i] = reservedMemory + i*order;
 
     }

     return firstpos;                                  //retorna o valor apontado por firstpos
}


void printMatrix(int **matrix, int order)
{


     for (int register i = 0; i < order; i++)
     {
          for (int register j = 0; j < order; j++)
               printf("%3d ",matrix[i][j]);
          printf("\n");
     }

}


void fillMatrix(int **matrix, int order)
{

       srand((unsigned int)time(NULL));
       for( register int i = 0; i < order; i++)
          for ( register int j = 0; j < order; j++)
               matrix[i][j] = rand();
         
}




void deleteMatrix(int **matrix)
{
     _aligned_free(matrix[0]);

}