/*
   lincefr.c
   Desc. : Lincefr main file
*/

#include <stdio.h>
#include <stdlib.h>


int main( int argc, char *argv[])
{
  if( argc <= 1 )
   {
     fprintf(stderr,"[ERROR] No input file!\n");
     printf("Use: %s [FILENAME] [COMMAND]\n",argv[0]);
     return 1;
   }


 return 0;
}
