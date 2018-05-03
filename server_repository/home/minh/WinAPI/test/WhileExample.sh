# /bin/bash
# Script to test while statement
#
if test $# -eq 0  # La so tham so nhap vao
then 
echo "Error - Number missing form command line argument"
echo "Systax : $0 number"
echo "Use to print multiplication table for given number"
exit 1
fi
n=$1
i=1
while [ $i -le 10 ]
do
	echo "$n * $i = `expr $i \* $n`"
	i=`expr $i + 1`
done


