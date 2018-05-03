#include <stdio.h>
#include <stdlib.h>
int a[100];// mang dung de luu cac phan tu de sap xep
int n;
int tempArray[100];//mang tam de luu trong merge sort
void readFile(const char * name){
	FILE *f;
	f = fopen(name,"r");
	if(f==NULL){
		fprintf(stderr,"Can't open file");
	}
	else{
		int i = 1;
		while(!feof(f)){
			fscanf(f,"%d",&a[i]);
			i++;
		}
		n = i-1;
	}
}
void printArray();
void Swap(int &a,int &b){
	int temp = a;
	a = b;
	b = temp;  
}
void insertionSort(int a[],int n){//ham nay minh tu viet
	for(int i = 2;i<=n;i++){
		int p = i;
		while(a[p]<a[p-1]){
			Swap(a[p],a[p-1]);
			p--;
		}
	}
}
void insertion_sort(int a[],int n){
	for(int i = 2;i<=n;i++){
		int last = a[i];
		int j = i;
		while(j>1 && a[j-1]>last){
			a[j]=a[j-1];
			j--;
		}
		a[j]=last;
	}
}
void selectionSort(int a[],int n){
	for(int i=1;i<n;i++){
		int j,min = i;
		for(j= i+1;j<=n;j++){
			if(a[j]<a[min])
				min = j;
		}
		Swap(a[i],a[min]);
		printf("\nBuoc %i: ",i);
		for(int j=1;j<=n;j++){
			printf("%d ",a[j]);
		}
	}
}

 void bubbleSort(){
	int swapped,dem = 1;
	do{
		swapped = 0;
		for(int i = 1; i<n ;i++){
			if(a[i]>a[i+1]){
				swapped = 1;
				Swap(a[i],a[i+1]);
				printf("\nBuoc %d: ",dem);
				dem++;
				printArray();
			}
		}
	}while(swapped == 1);
}

void merge(int a[], int L, int M,int R){
	int i = L;
		int j = M+1;
	for(int k = L; k<=R ; k++){
		
		if(i>M){
			tempArray[k] = a[j];
			j++;
		}
		else if(j>R){
			tempArray[k] = a[i];
			i++;
		}
		else{
			if(a[i]<a[j]){
				tempArray[k] = a[i];
				i++;
			}
			else{
				tempArray[k] = a[j];
				j++;
			}
		}
	}
	for(int k = L; k<=R ;k++){
		a[k] = tempArray[k];
		printf("%d ",a[k]);
	}
	printf("\n");
}
void mergeSort(int a[], int L, int R){
	if(L<R){
		int M = (L+R)/2;
		mergeSort(a,L,M);
		mergeSort(a,M+1,R);
		merge(a,L,M,R);
	}
}
int dem = 1;
int partition(int *a,int L,int R,int indexPivot){
	int pivot = a[indexPivot];
	printf("\npivot = %d",pivot);
	Swap(a[indexPivot], a[R]);// cho pivot xuong cuoi array
	int storeIndex = L;
	for(int k = L; k<R ;k++){
		if(a[k] < pivot){
			Swap(a[k],a[storeIndex]);
			storeIndex++;
		}
	}
	Swap(a[R],a[storeIndex]);
	printf("\nstoreindex = %d",storeIndex);
	printf("\n buoc %d: ", dem);
	printArray();
	dem++;
	return storeIndex;
} 
void quickSort(int *a, int L, int R){
	if(L<R){
		int index = (L + R)/2;
		index = partition(a,L,R,index);
		if(L < index){
			quickSort(a,L,index-1);
		}
		if(index < R){
			quickSort(a,index+1,R);
		}
		
	}
	
}
void heapify(int a[],int i,int n){
	// array to be heapified is a[1..n]
	int L = 2*i;
	int R = 2*i + 1;
	int max = i;
	if(L<=n && a[L]>a[i]){
		max = L;
	}
	if(R<=n && a[R]>a[max]){
		max = R;
	}
	if(max != i){
		Swap(a[i],a[max]);
		heapify(a,max,n);
	}
}

void buildHeap(int a[],int n){
	//array is a[1..n]
	for(int i=n/2; i>=1; i--){
		heapify(a, i, n);
	}
}

void heapSort(int a[],int n){
	//array is a[1..n]
	buildHeap( a, n);
	printArray();
	for(int i=n ;i>1;i--){
		Swap(a[1],a[i]);
		heapify(a,1,i-1);
	}
}
void printArray(){
	for(int i = 1; i<=n ;i++){
		printf("%d ",a[i]);
	}
	printf("\n");
}
int main(){
	readFile("input.txt");
	printf("\nmang ban dau: ");
	printArray();
	printf("\npress 1 : Insertion Sort\npress 3 : Selection sort\npress 4 : Bubble sort "
	"\npress 5 : Merge Sort\npress 6 : Heap Sort\npress 7 : Quick Sort");
	printf("\nnhap cach sap xep muon thuc hien: ");
	int flag;
	scanf("%d",&flag);
	switch(flag){
		case 1:{
			insertion_sort(a,n);
			printf("\ninsertion sort : ");
			break;
		} 		
		case 2:{
			insertionSort(a,n);
			printf("\ninsertion sort : ");
			break;
		} 
		case 3:{
			selectionSort(a,n);
			printf("\n Selection sort: ");
			break;
		}
		case 4:{
			bubbleSort();
			printf("\n Bubble sort : ");
			break;
		}
		case 5:{
			mergeSort(a,1,n);
			printf("\n Merge Sort : ");
			break;
		}
		case 6:{
			heapSort(a,n);
			printf("\n Heap Sort : ");
			break;
		}
		case 7: {
			quickSort(a,1,n);
			printf("\n Quick Sort : ");
			break;
		}
		default: {
			printf("\nerror ");
		}
	}
	printArray();
	//printf("\nOK\n");
	return 0;
}
