
bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

export SOCIALITE_PREFIX=`dirname "$bin"`

export SOCIALITE_CONF_DIR="${SOCIALITE_PREFIX}/conf"

if [ -f "${SOCIALITE_CONF_DIR}/socialite-env.sh" ]; then
  . "${SOCIALITE_CONF_DIR}/socialite-env.sh"
fi

SOCIALITE_MASTER=`cat "${SOCIALITE_CONF_DIR}/master"|sed "s/#.*$//;/^$/d"|head -1`
HADOOP_CONF_DIR="${HADOOP_HOME}/conf"
HADOOP2_CONF_DIR="${HADOOP_HOME}/etc/hadoop"
export HADOOP_PREFIX="${HADOOP_HOME}"

if [ "x$SOCIALITE_OUTPUT_DIR" = "x" ]; then
    SOCIALITE_OUTPUT_DIR="${SOCIALITE_PREFIX}/gen"
fi

EXT="${SOCIALITE_PREFIX}/ext"
#PREV_CP="${CLASSPATH}"
CLASSPATH="${CLASSPATH}:${SOCIALITE_CONF_DIR}"
if [ -f "${HADOOP_HOME}/bin/yarn" ]; then
    HADOOP2_CLASSPATH=`${HADOOP_HOME}/bin/yarn classpath`
    CLASSPATH=${CLASSPATH}:${HADOOP2_CONF_DIR}:${HADOOP2_CLASSPATH}
else
    CLASSPATH=${CLASSPATH}:${HADOOP_CONF_DIR}:${EXT}/hadoop-core-1.2.1.jar
fi

CLASSPATH=${CLASSPATH}:${EXT}/commons-lang3-3.1.jar
CLASSPATH=${CLASSPATH}:${EXT}/commons-lang-2.6.jar
CLASSPATH=${CLASSPATH}:${EXT}/commons-configuration-1.6.jar
CLASSPATH=${CLASSPATH}:${EXT}/commons-logging-1.1.1.jar:${EXT}/commons-logging-api-1.0.4.jar
CLASSPATH=${CLASSPATH}:${EXT}/log4j-1.2.16.jar
CLASSPATH=${CLASSPATH}:${EXT}/jython.jar
CLASSPATH=${CLASSPATH}:${EXT}/ST-4.0.7.jar:${EXT}/antlr-3.5.2-complete-no-st3.jar:${EXT}/trove-3.0.3.jar 
CLASSPATH=${CLASSPATH}:${EXT}/concurrent-prim-map-1.0.0.jar

#CLASSPATH=${CLASSPATH}:${EXT}/jfreechart-1.0.14-all.jar

CLASSPATH=${CLASSPATH}:${SOCIALITE_PREFIX}/classes
CLASSPATH=${CLASSPATH}:${SOCIALITE_PREFIX}/classes/socialite.jar
#CLASSPATH=${CLASSPATH}:${SOCIALITE_PREFIX}/socialite-all.jar 

#if [ "$PREV_CP" != "" ]; then
#    CLASSPATH=${CLASSPATH}:${PREV_CP}
#fi
export CLASSPATH


if [ "x$JAVA_HOME" = "x" ]; then
    echo "Error: JAVA_HOME is not set."
    exit 1
fi

# Determine the JVM type and version
JAVA="${JAVA_HOME}/bin/java"

java_ver_output=`"${JAVA}" -Xmx48m -Xms16m -version 2>&1`

jvm_ver_long=`echo "$java_ver_output" | awk -F'"' 'NR==1 {print $2}'`
JVM_VERSION=`echo $jvm_ver_long | sed 's/\(.*\)\.\(.*\)\..*/\1\2/; 1q'`
JVM_UPDATE_VERSION=${jvm_ver_long#*_}

jvm_type=`echo "$java_ver_output" | awk 'NR==2 {print $1}'`
case "$jvm_type" in
    "OpenJDK")
        JVM_VENDOR=OpenJDK
        JVM_ARCH=`echo "$java_ver_output" | awk 'NR==3 {print $2}'`
        ;;
    "Java(TM)")
        JVM_VENDOR=Oracle
        JVM_ARCH=`echo "$java_ver_output" | awk 'NR==3 {print $3}'`
        ;;
    *)
        JVM_VENDOR=Other
        JVM_ARCH=Unknown
        ;;
esac
#echo -e "JVM_VENDOR:"$JVM_VENDOR"\nJVM_ARCH:"$JVM_ARCH"\nJVM_VERSION:"$JVM_VERSION"\nJVM_UPDATE_VERSION:"$JVM_UPDATE_VERSION

# Define JVM Options

if [ "x$JAVA_HEAP_MAX" == "x" ]; then
    if [ "x$SOCIALITE_HEAPSIZE" != "x" ]; then
        JAVA_HEAP_MAX="-Xmx""$SOCIALITE_HEAPSIZE""m"
    else
        JAVA_HEAP_MAX=-Xmx2048m
    fi
fi
if [ "x$JAVA_HEAP_MIN" == "x" ]; then
    if [ "x$SOCIALITE_HEAPSIZE_MIN" != "x" ]; then
        JAVA_HEAP_MIN="-Xms""$SOCIALITE_HEAPSIZE_MIN""m"
    else
        JAVA_HEAP_MIN=-Xms1024m
    fi
fi

JVM_OPTS="$JVM_OPTS -da"
JVM_OPTS="$JVM_OPTS -server -Xss256k"
JVM_OPTS="$JVM_OPTS -XX:MaxInlineSize=128"
JVM_OPTS="$JVM_OPTS -XX:+UseParallelGC -XX:+UseTLAB -XX:TLABSize=8M -XX:+ResizeTLAB"

# Use conditional card marking for Java 1.7.0_40 and up
if [ "$JVM_VERSION" -ge 17 ] && [ "$JVM_ARCH" = "64-Bit" ] ; then
    if [ "$JVM_VERSION" -eq 17 ] && [ "$JVM_UPDATE_VERSION" -lt "40" ]; then
        : # do nothing
    else
        JVM_OPTS="$JVM_OPTS -XX:+UseCondCardMark"
    fi
fi


if [ "x$SOCIALITE_LOG_DIR" = "x" ]; then
    SOCIALITE_LOG_DIR="${SOCIALITE_PREFIX}/logs"
fi

export SOCIALITE_HOME=${SOCIALITE_PREFIX}
