#include <stdio.h>
#include <dos.h>
int main(int argc, char *argv[]){
	union REGS regs;
	struct SREGS sregs;
	int Buf[512];
	int i;
	regs.h.ah = 0x02; regs.h.al = 0x01;
	regs.h.dh = 0x00; regs.h.dl = 0x80;
	regs.h.ch = 0x00; regs.h.cl = 0x01;
	regs.h.bx = FP_OFF(Buf);
	regs.es = FP_SEG(Buf);
	int86x(0x13,&regs,&regs,&sregs);
	for(int i=0;i<512;i++){
		printf("%4X",Buf[i]);
	}
	return 0;
}
