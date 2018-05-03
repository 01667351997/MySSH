#!/bin/bash
#Menu.sh
copy_file(){
	echo "Nhap vao ten file nguon: "
	read filenguon
	echo "Nhap ten file dich: "
	read tmdich
	if [ -f $tmdich/$filenguon ]
	then
	echo "Da ton tai file trong thu luc dich"
	else
	cp $filenguon $tmdich
	fi
	return 0
}

xoa_file(){
	echo "Nhap vao ten file can xoa: "
	read filexoa
	if [ -f $filexoa ]
	then 
	rm -f $filexoa
	else
	echo "Khong ton tai file nay"
	fi
	return 0
}

while true
do 
	echo "An 1 | c | C de copy file"
	echo "An 2 | x | X de xoa file"
	echo "An 3 | t | T de thoat"
	echo nhap vao 1 ki tu:
	read ch
	case $ch in
	1 | c | C) copy_file;;
	2 | x | x) xoa_file;;
	3 | t | T) exit 0;;
	*) echo "Nhap lai ki tu"
	esac
done
