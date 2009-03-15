/*
   lincefr.c
   Desc. : Lincefr main file
*/

#include <stdio.h>
#include <stdlib.h>
#include "strings/lincefr.h"

int main( int argc, char *argv[])
{
  if( argc <= 1 )
   {
     fprintf(stderr,lincefrstr1);
     printf(lincefrstr2);
     return 1;
   }


 return 0;
}
