
# The Java implementation to use. 
#export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_51/

# The path for the hadoop running instance. Required if reading data from HDFS.
#export HADOOP_HOME=/path/to/hadoop/

# Distributed SociaLite servers can use upto 10 port numbers from the base port number.
#  (SOCIALITE_BASE_PORT, SOCIALITE_BASE_PORT+1, SOCIALITE_BASE_PORT+2, ... SOCIALITE_BASE_PORT+9)
export SOCIALITE_BASE_PORT=50100

# The maximum heap size in MB
export SOCIALITE_HEAPSIZE=15000

# The initial heap size in MB
export SOCIALITE_HEAPSIZE_MIN=8000

# The directory where pid files are stored. /tmp/ by default.
# export SOCIALITE_PID_DIR=/var/socialite/pids/

# The number of worker thread
# (if not defined, the #-of-cores is used)
export SOCIALITE_WORKER_NUM=16
