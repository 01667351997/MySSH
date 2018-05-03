#include <windows.h>
const char g_szClassName[]="myWindowClass" ;//ten lop cua so
//step 4: the windown procedure
LRESULT CALLBACK WndProc(HWND hwnd,UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch(msg)
	{
		case WM_CLOSE:
			DestroyWindow(hwnd);
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			break;
		default:
			//de windown xu li cac thong diep con lai
		return DefWindowProc(hwnd,msg,wParam,lParam); 
	}
	return 0;
}
int WINAPI WinMain(HINSTANCE hInstance,HINSTANCE hPrevIstance,LPSTR IpCmdLine, int nCmdShow)
{
	WNDCLASSEX wc;
	HWND hwnd;
	MSG Msg;
	//step 1: registering the windown class
	wc.cbSize=sizeof(WNDCLASSEX);
	wc.style=0;
	wc.lpfnWndProc=WndProc; //ham xu li thong diep cua so
	wc.cbClsExtra=0;//khong can thong tin them cho cua so
	wc.cbWndExtra=0;
	wc.hInstance = hInstance;
	wc.hIcon=LoadIcon(NULL,IDI_APPLICATION);
	wc.hCursor=LoadCursor(NULL,IDC_ARROW);
	wc.hbrBackground=(HBRUSH)(COLOR_WINDOW+1);//mau nen
	//wc.hbrBackground=GetStockObject(WHILE_BRUSH); //mau nen trang
	wc.lpszMenuName=NULL;//khong co he thong thuc don
	wc.lpszClassName=g_szClassName;//ten lop cua so
	wc.hIconSm=LoadIcon(NULL, IDI_APPLICATION);//dang ki lop cua so
	if(!RegisterClassEx(&wc))
	{
		MessageBox(NULL,"Windown Registation Failed!","Error!",MB_ICONEXCLAMATION|MB_OK);
		return 0;
	}
	//step 2: creat the windown
	// tao ra mot the nghiem cua lop cua so cho ung dung
	hwnd = CreateWindowEx(WS_EX_CLIENTEDGE,
	                     g_szClassName,
						 "The tilte of mu windown",
					     WS_OVERLAPPEDWINDOW,
						 CW_USEDEFAULT,CW_USEDEFAULT,240,120,
						 NULL,NULL,hInstance,NULL);
	if(hwnd=NULL){
		MessageBox (NULL,"Windown Creation Failed!","Error!",MB_ICONEXCLAMATION|MB_OK);
		return 0;
	}
	//hien thi cua so
	ShowWindow(hwnd, nCmdShow);
	UpdateWindow(hwnd);
	//step 3: the message loop
	//step 3 tao vong lap xu li thong diep
	while(GetMessage (&Msg,NULL,0,0)>0){
		TranslateMessage(&Msg);
		DispatchMessage(&Msg);
	}
	return Msg.wParam;
}
