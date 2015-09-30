# setup 'java.library.path' for native-hadoop code. Copied from the hadoop launch script
#JAVA_LIBRARY_PATH=''
#if [ -d "${HADOOP_HOME}/build/native" -o -d "${HADOOP_HOME}/lib/native" -o -e "${HADOOP_PREFIX}/lib/libhadoop.a" ]; then
#  JAVA_PLATFORM=`CLASSPATH=${CLASSPATH} ${JAVA} -Xmx32m org.apache.hadoop.util.PlatformName | sed -e "s/ /_/g"`
#
#  if [ "$JAVA_PLATFORM" = "Linux-amd64-64" ]; then
#    JSVC_ARCH="amd64"
#  else
#    JSVC_ARCH="i386"
#  fi
#
#  if [ -d "$HADOOP_HOME/build/native" ]; then
#    JAVA_LIBRARY_PATH=${HADOOP_HOME}/build/native/${JAVA_PLATFORM}/lib
#  fi
#
#  if [ -d "${HADOOP_HOME}/lib/native" ]; then
#    if [ "x$JAVA_LIBRARY_PATH" != "x" ]; then
#      JAVA_LIBRARY_PATH=${JAVA_LIBRARY_PATH}:${HADOOP_HOME}/lib/native/${JAVA_PLATFORM}
#    else
#      JAVA_LIBRARY_PATH=${HADOOP_HOME}/lib/native/${JAVA_PLATFORM}
#    fi
#  fi
#
#  if [ -e "${HADOOP_PREFIX}/lib/libhadoop.a" ]; then
#    JAVA_LIBRARY_PATH=${HADOOP_PREFIX}/lib
#  fi
#fi

if [ "x$JAVA_LIBRARY_PATH" != "x" ]; then 
  SOCIALITE_OPTS="$SOCIALITE_OPTS -Djava.library.path=$JAVA_LIBRARY_PATH"
fi

SOCIALITE_OPTS="$SOCIALITE_OPTS -Dsocialite.home=$SOCIALITE_HOME"
SOCIALITE_OPTS="${SOCIALITE_OPTS} -Dsocialite.output.dir=${SOCIALITE_OUTPUT_DIR}"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dsocialite.master=$SOCIALITE_MASTER"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Djline.keybindings=$SOCIALITE_HOME/conf/jline-keybindings.properties"
if [ "x$SOCIALITE_BASE_PORT" != "x" ]; then
  SOCIALITE_OPTS="$SOCIALITE_OPTS -Dsocialite.port=$SOCIALITE_BASE_PORT"
fi

SOCIALITE_OPTS="$SOCIALITE_OPTS -Dpython.verbose=warning"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dpython.options.proxyDebugDirectory=${SOCIALITE_OUTPUT_DIR}"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dpython.options.includeJavaStackInExceptions=false"
SOCIALITE_OPTS="$SOCIALITE_OPTS -Dpython.console=pysocialite.SociaLiteConsole"

if [ "x$SOCIALITE_WORKER_NUM" != "x" ]; then
    SOCIALITE_OPTS="${SOCIALITE_OPTS} -Dsocialite.worker.num=${SOCIALITE_WORKER_NUM}"
fi
if [ "x$SOCIALITE_WORKER_THREAD_NUM" != "x" ]; then
    SOCIALITE_OPTS="${SOCIALITE_OPTS} -Dsocialite.worker.num_threads=${SOCIALITE_WORKER_THREAD_NUM}"
fi
if [ "x$SOCIALITE_HEAPSIZE" != "x" ]; then
    SOCIALITE_OPTS="${SOCIALITE_OPTS} -Dsocialite.worker.heap_size=${SOCIALITE_HEAPSIZE}"
fi


