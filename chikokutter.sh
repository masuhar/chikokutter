export SHELLOPTS
set -o igncr
realpath_self=`realpath $0`
pushd `dirname $realpath_self` > /dev/null
SCRIPTPATHU=`pwd -P`
SCRIPTPATHW=`cygpath -w $SCRIPTPATHU`
popd > /dev/null
CLASSPATH="${SCRIPTPATHW}\bin;${SCRIPTPATHW}\lib\twitter4j-core-3.0.5.jar"
echo $CLASSPATH
export CLASSPATH
cd $SCRIPTPATHU
java chikokutter.Main
