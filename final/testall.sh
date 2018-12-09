#!/bin/sh

# default
testfolder="$(dirname $0)" # assumes script inside test folder
src_dir=../project

if [ $# -eq 1 ]
then
  src_dir=$1
elif [ $# -gt 1 ]
then
  echo "$0: too many arguments"
  echo "usage: $0 [ src dir (optional) ]"
  exit 1
fi

if ! [ -r $src_dir/Makefile ]
then
  echo "$0: that dir has no Makefile!"
  exit 1
fi

lightred="\033[31m"
lightgreen="\033[32;m"
red="\033[31;1m"
green="\033[32;1m"
reset="\033[0m"

# compiling: if you want to show the compilation shenanigans,
# remove everything from 2>&1 onwards onwards
make -C $src_dir  2>&1 > /dev/null
if [ "$?" -ne "0" ]
then
  echo
  printf '%b' "$0: ${red}build failed${reset}\n"
  printf '%b' "$0: ${red}aborting${reset}\n"
  exit 1
fi

if ! [ -d results ]
then
  mkdir results
fi

total=0
succs=0
for file in $testfolder/*.in
do
  input=$file
  raw=$(basename $input | cut -f 1 -d'.')
  import=$testfolder/$raw.import
  expected=$testfolder/expected/$raw.out
  out=results/$raw.outhyp
  if ! [ -r $input ]
  then
    printf '%b' "$0: cannot read file "$input"${red}aborting$reset\n"
    exit 1
  fi
  if ! [ -r $import ]
  then
    printf '%b' "$0: cannot read file $import${red}aborting$reset\n"
    exit 1
  fi
  if ! [ -r $expected ]
  then
    printf '%b' "$0: cannot read file $expected${red}aborting$reset\n"
    exit 1
  fi

  total=$(echo "$total + 1" | bc)
  java -Dimport=$import -Din=$input -Dout=$out -cp "/usr/share/java/po-uuilib.jar:$src_dir/sth-core/sth-core.jar:$src_dir/sth-app/sth-app.jar" sth.app.App
  diff -b $expected $out &> /dev/null
  if [ $? -eq 0 ]
  then
    printf '%b' "Test $raw -- ${green}OK$reset\n"
    succs=$(echo "$succs + 1" | bc)
  else
    printf '%b' "Test $raw -- ${red}FAILED\n"
    printf '%b' "$reset $lightred"
    diff -b $expected $out
    printf '%b' "$reset"
  fi
done

score=$(echo "100 * $succs / $total" | bc)
if [ $score -eq 100 ]
then
  printf '%b' "Score$green 100 [$succs/$total]\n"
else
  printf '%b' "Score$red $score [$succs/$total]\n"
fi

printf '%b' "$reset"
echo "$0: finished"
