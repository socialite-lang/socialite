#### SociaLite: Query Language For Large-Scale Graph Analysis

http://socialite.stanford.edu

**SociaLite** is a high-level query language for distributed graph analysis.
In SociaLite, analysis programs are implemented in high-level queries, that are compiled to parallel/distributed code.  The compiled code is highly optimized, and can run as fast as three orders of magnitude (1000x) faster than equivalent Hadoop programs on InfiniBand network.

SociaLite is Hadoop compatible, hence SociaLite queries can read data on HDFS (Hadoop Distributed File System).  With its Java and Python extension, SociaLite queries can read data from other input sources such as Amazon S3 or relational databases. 

Its integration with Python makes it convenient to implement graph mining algorithms in SociaLite. Many well-known algorithms such as PageRank, K-Means clustering, or Logistic Regression can be implemented in just a few lines of SociaLite queries and a couple of Python functions.

Interested? [Read the quick tutorial](http://socialite-lang.github.io/pages/quick_start)
