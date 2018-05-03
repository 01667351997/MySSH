#include <windows.h> 
#include <stdio.h> 
int main(int argc, char *argv[]){
HANDLE hDisk;
BYTE Buf[512];
DWORD byteread;
int i;
hDisk=CreateFile("\\\\.\\D",GENERIC_READ, FILE_SHARE_READ | FILE_SHARE_WRITE, NULL, OPEN_EXISTING,0,NULL); 
if (hDisk==INVALID_HANDLE_VALUE) 
	printf("Loi thiet bi"); 
else { 
	ReadFile(hDisk,Buf,512,&byteread,NULL); 
	for(i=0;i<512;i++) printf("%4X",Buf[i]); 
	CloseHandle(hDisk); 
} 
return 0;
} 
