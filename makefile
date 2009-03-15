lincefr: lincefr.o
	gcc -o lincefr lincefr.o
lincefr.o: lincefr.c strings/lincefr.h
	gcc -c -g lincefr.c
