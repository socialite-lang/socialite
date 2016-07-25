
# The Java implementation to use. 
#export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_51/

# The path for the hadoop running instance. Required if reading data from HDFS.
#export HADOOP_HOME=/path/to/hadoop-2.7.1/

# SociaLite servers use up to 10 port numbers that are greater than the following base port number.
export SOCIALITE_BASE_PORT=50100

# The heap size (in MB) for a worker.
export SOCIALITE_HEAPSIZE=4000

# The number of worker nodes.
export SOCIALITE_WORKER_NUM=2

# The number of threads on a single worker.
export SOCIALITE_WORKER_THREAD_NUM=2
