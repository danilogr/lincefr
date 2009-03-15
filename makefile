lincefr: lincefr.o
	gcc -o lincefr lincefr.o
lincefr.o: lincefr.c
	gcc -c -g lincefr.c
