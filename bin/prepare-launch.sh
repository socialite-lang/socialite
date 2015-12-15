
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


