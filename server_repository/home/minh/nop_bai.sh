#!/bin/bash

if [ $# -eq 0 ]
then 
echo "Error: thieu tham so"
exit 1
fi


# tao file chua cac tep copy
name=/home/binhminh/baitaplinux
if [ -d $name ]
then
echo " file $name created" 
else
mkdir $name
fi



# copy file vao thu muc
i=1
while [ $i -eq $# ]
do
temp=$1
shift
cp $temp $name
chmod o-w,u-w $name/$temp
i=`expr $i + 1`
done

 


# ghi vao file log
if [ -f "log.txt" ]
then
touch log.txt
fi
echo " $LOGNAME : `date` : $0 $*" >> log.txt
