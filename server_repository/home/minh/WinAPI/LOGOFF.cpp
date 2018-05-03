#include <windows.h>
int main(){
	ExitWindowsEx(EWX_LOGOFF, 0);
	return 0;
}
