/*
     Funções de multplicação e comparação de matrizes que  utilizam
     as vantagens de instruções SIMD -> SSE2
*/


#include "stdafx.h"
#include "matrix.h"

/*
    multiplica duas xMatrix utilizando SSE2

*/

void SSE2_multiplyxMatrix(int **result, int **a, int **b, int order)
{
     int linhaA, linhaB, colunaAtual, order2;
 
__asm
     {
          push esi                                          // salva esi
          push edi                                          // salva edi

          mov eax, dword ptr [order]                        // Transforma order
          shr eax,2                                         // em um numero divisivel
          shl eax,2
          mov dword ptr [order2],eax                        // por 4 e coloca em order2

          mov eax, dword ptr [a]                            //
          mov ebx, dword ptr [b]                            //
          mov eax, dword ptr [eax]                          //
          mov ebx, dword ptr [ebx]                          //
          mov edx, dword ptr [result]                       //
          mov edx, dword ptr [edx]                          //

          mov [linhaA], 0
loopLinhasA:

          mov ecx, ebx                                      // ecx possui a posicao atual da primeira linha
          mov [linhaB], 0
     loopLinhasB:
          mov esi, 0                                        // soma da multiplicaçao
          mov [colunaAtual],0                               // coluna 0
          push eax
          loopColunas:

                    //
                    // Primeiro multiplica 4
                    //

                    movdqa xmm0,[ecx]                                 // pega 4 inteiros da linha a ser multiplicada
                    movdqa xmm1,[eax]                                 // pega 4 inteiros da linha atual da matriz a
                    add    ecx,16                                     // proximos 4 inteiros
                    add    eax,16  
                    pmulld xmm0,xmm1                                  // multiplica os valores

                    //
                    // Depois soma os 4
                    //

                    //primeiro vamos somar 2 simultaneamente
                    pshufd xmm1,xmm0, 0x1E                            // coloca dois numeros no registrador xmm1
                    paddd  xmm1,xmm0                                  // soma os inteiros
                    

                    //
                    // Adiciona ambos na soma total
                    //  
                    pextrd edi,xmm1,0
                    add    esi,edi
                    pextrd edi,xmm1,1
                    add    esi,edi

                    mov edi, dword ptr [order2]                       // edi = order2
                    add dword ptr[colunaAtual],4                      // incrementa a coluna atual (4 colunas)
                    cmp dword ptr[colunaAtual],edi                    // verifica se o loop precisa ser repetido
                    jb loopColunas                                    //  

               //
               // Após sair do loop de colunas existe a possibilidade de existirem elementos
               // que ainda nao foram computados (quando order não é divisivel por 4)
               // O codigo foi escrito para fazer no maximo 1 multiplicacao
               //

               mov edi, dword ptr [order]
               sub edi, dword ptr [order2]                            
               jz continuaNormal                                      // se order = order2

               // caso contrario edi é igual a quantidade de numeros que faltam ser multiplicados
               sub edi,4                                              // (pois edi entre 0 e 3)
               shl edi,2                                              // multiplica por 4
               add ecx, edi                                           // Retorna o numero necessario de bytes
               add eax, edi                                           // para conseguir pegar soh numeros da matrix

               movdqa xmm0,[ecx]                                      //
               movdqa xmm1,[eax]                                      //

               pmulld  xmm1,xmm0                                      // multiplica (note que nao eh alinhada)

               sub ecx,edi                                            // recupera
               //sub eax,edi                                            // os valores (nao precisa ser recuperado)

               sar edi,2                                              // se order - order2 = 1
               neg edi                                                // entao edi = 3
               
               // Note que utilizamos eax já que o 
               // mesmo encontra-se salvo na pilha
               // e vai ser recuperado apos o EIP sair do loop
somaUm:  
               pextrd eax,xmm1,3                                      // pega o numero e soma ele
               add esi,eax                                            // na soma total
               add ecx,4                                              // cada numero representa 1 byte em ecx
     
               pshufd  xmm1,xmm1,0x93                                  // faz um rotate
               inc edi
               cmp edi,4                                              // se apenas um numero precisava ser somado
               jb  somaUm                                             // entao
              


continuaNormal:
               pop eax
               // saindo do loop de colunas (ou seja, apos multiplicar e somar todas as colunas
               // estamos prontos para salvar o resultado na matriz resultado

               mov edi, dword ptr [order]
               mov [edx], esi                                         // edx recebe o resultado
               add edx,4                                              // proximo inteiro

               inc dword ptr [linhaB]
               cmp [linhaB], edi                                      // note que edi possui a ordem
               jb loopLinhasB



          // incrementa uma linha em eax
         // mov  edi, dword ptr [order]                       // order em EDI
          shl  edi,2                                        // multiplica por 4 ( a order que está em edi)
          add  eax,edi                                      // soma em eax

          inc [linhaA]                                      // incrementa a linha atual
          shr  edi,2                                        //
          cmp dword ptr [linhaA],edi                        // compara a linha atual com a ordem
          jb loopLinhasA                                    // se for menor, repete o loop


          pop edi                                           // restaura edi
          pop esi                                           // restaura esi
          

     }



}



/*
    A função abaixo não está completa ( nao é utilizada )
*/
bool SSE2_equalMatrixes(int **a, int **b, int order)
{
 

     __asm
     {
          push ebp                           // salva esi
          push edi
          push ebp

          mov esi, dword ptr [order]         // ecx = order

          //
          //    Inicialização: Carrega os ponteiros para o primeiro elemento
          //  da matriz nos registradores ebx e eax
          //
          mov esi, dword ptr [a]        // eax = **a
          mov edi, dword ptr [b]        // ebx = **b

          xor ecx,ecx                   // zera o contador

          //
          //  Loop que percorre as linhas
          //
lineLoop:

          mov eax, dword ptr [esi + 4*ecx]   // Seleciona a linha da matriz a  apontada por ecx
          mov ebx, dword ptr [edi + 4*ecx]   // Seleciona a linha ma matriz b  apontada por ecx

          xor edx,edx                        // coluna 0
          test ebp,1
          jz continua1
          
          

          //caso for impar, a prmeira comparacao é normal
          

continua1:
          test ebp,2                         // verifica se é um numero divisivel por 4
          jz continua2                       // se for, continua

          // caso contrario temos mais dois inteiros para comparar

continua2:
        
          cmp ecx, ebp
          jb lineLoop
         
          movdqa     xmm0, xmmword ptr [eax]
          movdqa     xmm1, xmmword ptr [ebx]
          pcmpeqd    xmm0, xmm1                        // Compara se os dwords sao iguais
          pmovmskb   esi,xmm0                          // gera 16 bits baseado nos sinais de xmm0

          add eax,16                                   // incrementa posicao matriz a
          add ebx,16                                   // incrementa posicao matriz b
          
          cmp si,0xFFFF                                // verifica se todos os numeros sao iguais
          jne termina_falso                            // caso nao for as matrizes nao sao iguais

         // loop cmploop   

          //
          //  Apos sair do loop ainda existem comparacoes que precisam ser realizadas
          //

          mov ecx,edx
          jecxz terminaok

loopnonsse:
          mov edx, dword ptr [eax]
          cmp edx, dword ptr [ebx]
          jne termina_falso

          add eax,4
          add ebx,4

          loop loopnonsse

terminaok:

          mov eax,1                                    // true
          jmp fim                                      // salta para o fim
    

termina_falso:
          mov eax,0                                    // false

fim:
          pop ebp
          pop edi
          pop esi                                      // recupera esi

     }

}

/*
    Verifica se duas xMatrixes são iguais
*/

bool SSE2_equalxMatrixes(int **a, int **b, int order)
{
 

     __asm
     {
          push esi                           // salva esi
          //
          //  Descobre, inicialmente, a quantidade de vezes que o loop tera que ser repetido
          //

          mov ecx, dword ptr [order]         // ecx = order
          mov eax, ecx                       // eax = order
          mul ecx                            // eax = ecx*eax ,ou seja, (order^2)

          xor edx,edx                        // zera por causa da divisao
          mov ecx,4                          // 4 inteiros por iteração
          div ecx                            // EAX = order / 4; EDX = order mod 4

          mov  ecx,eax                        // numero de repeticoes do loop

          //
          //    Inicialização: Carrega os ponteiros para o primeiro elemento
          //  da matriz nos registradores ebx e eax
          //
          mov eax, dword ptr [a]        // eax = **a
          mov ebx, dword ptr [b]        // ebx = **b
          mov eax, dword ptr [eax]      // eax = a[0]
          mov ebx, dword ptr [ebx]      // ebx = b[0]

          //
          //  Loop de comparação.
          //
cmploop:

         
          movdqa     xmm0, xmmword ptr [eax]
          movdqa     xmm1, xmmword ptr [ebx]
          pcmpeqd    xmm0, xmm1                        // Compara se os dwords sao iguais
          pmovmskb   esi,xmm0                          // gera 16 bits baseado nos sinais de xmm0

          add eax,16                                   // incrementa posicao matriz a
          add ebx,16                                   // incrementa posicao matriz b
          
          cmp si,0xFFFF                                // verifica se todos os numeros sao iguais
          jne termina_falso                            // caso nao for as matrizes nao sao iguais

          loop cmploop   

          //
          //  Apos sair do loop ainda existem comparacoes que precisam ser realizadas
          //

          mov ecx,edx
          jecxz terminaok

loopnonsse:
          mov edx, dword ptr [eax]
          cmp edx, dword ptr [ebx]
          jne termina_falso

          add eax,4
          add ebx,4

          loop loopnonsse

terminaok:

          mov eax,1                                    // true
          jmp fim                                      // salta para o fim
    

termina_falso:
          mov eax,0                                    // false

fim:
          pop esi                                      // recupera esi
     }

     

}
