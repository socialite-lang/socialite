package socialite.dist;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

import socialite.engine.ClientEngine;
import socialite.engine.Config;
import socialite.engine.DistEngine;
import socialite.engine.LocalEngine;
import socialite.resource.SRuntimeMaster;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;
import socialite.resource.Sender;


public class DistCompileTest {

	static WorkerAddrMap fakeMap() {
		WorkerAddrMap map = new WorkerAddrMap();
		InetAddress addr0=null, addr1=null;
		
		try {
			addr0 = InetAddress.getByName("192.168.1.1");
			addr1 = InetAddress.getByName("192.168.1.2");
		} catch (UnknownHostException e) { throw new RuntimeException(e); }
		
		map.add(addr0); map.add(addr1);
		return map;
	}
	static void triangle1() {
		SRuntimeMaster.createTest(fakeMap());
		
		DistEngine en = new DistEngine(Config.dist(2), fakeMap(), null);
		String init="Edge[int s:0..1768197]((int t)) sortby t."+
				"Edge[s](t) :- id=$MyId(), b=id, e=id, line=$ReadSplitted(\"hdfs:///user/jiwon/data/edges%d.txt\", b, e),"+
				"      (s1, s2)=$Split(line), s=$ToInt(s1), t=$ToInt(s2).";
		String query= "Triangle[int h:0..0]((int x, int y, int z))." +
			"Triangle[0](x, y, z) :- Edge[x](y), x<y, Edge[y](z), y<z, Edge[x](z).";
		
		en.justCompile(init);
		en.justCompile(query);
	}
	static void triangle2() {
		SRuntimeMaster.createTest(fakeMap());
		
		DistEngine en = new DistEngine(Config.dist(2), fakeMap(), null);
		String query="Edge[int s:0..9]((int t)) sortby t."+		
		"Edge[s](t) :- id=$MyId(), b=id, e=id, line=$ReadSplitted(\"hdfs:///user/jiwon/data/edges%d.txt\", b, e),"+
				"      (s1, s2)=$Split(line), s=$ToInt(s1), t=$ToInt(s2)."+
				"Node[int s:0..268435456](int i) predefined.\n";	
		
		String mainQuery1 = "Triangle[int h:0..15](int cnt).\n"+
		"Triangle[id]($Inc(1)) :- Node[x](_),  0==(x mod 8), Edge[x](y), x<y, Edge[x](z), y<z, Edge[y](z), id=$MyId().";
		
		en.justCompile(query);
		en.justCompile(mainQuery1);
		
		String finalQuery = "TotalTriangle[int n:0..0](int cnt).\n"+
		"TotalTriangle[0]($Inc(cnt)) :- Triangle(0, cnt).";						
		en.justCompile(finalQuery);
	}
	static void lastfmTriangle() {
		SRuntimeMaster.createTest(fakeMap());
		
		DistEngine en = new DistEngine(Config.dist(2), fakeMap(), null);
		String query="Edge[int s:0..1768197]((int t)) sortby t."+
			"Edge[s](t) :- line=$Read(\"hdfs:///user/jiwon/data/lastfm.txt\"),"+
					"      (s1, s2)=$Split(line), s=$ToInt(s1), t=$ToInt(s2).";
		
		en.justCompile(query);
		query= "Triangle[int h:0..0](int cnt)." +
				"Triangle[0]($Inc(1)) :- Edge[x](y), x<y, Edge[y](z), y<z, Edge[x](z)." +
				"?-Triangle[h](cnt).";
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		en.justCompile(query);
		en.justCompile("clear Triangle.");
		
	}
	
	static void pagerank() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(2), fakeMap(), null);
		int numVertices=/*4847571*/1768197;
		String initQuery = String.format(
				"InEdge[int s:0..%d]((int t)) sortby t.\n"+
				"InEdge[s](t) :- line=$Read(\"hdfs:///user/ubuntu/data/lastfm.txt\"),"+
				"			   (s1,s2)=$Split(line), s=$ToInt(s1), t=$ToInt(s2).\n"+
				"EdgeCnt[int s:0..%d](int cnt).\n"+
				"EdgeCnt[t]($Inc(1)) :- InEdge[s](t).",
				numVertices, numVertices);	
		
		String mainQuery=String.format("Rank[int n:0..%d]((int i:0..9, double rank)).\n"+
			"Rank[_](0, r) :- r= 0.000000006536. \n"+
			"Rank[_](1, r) :- r= 0.000000006536. \n"+
			"Rank[_](2, r) :- r= 0.000000006536. \n"+
			"Rank[p](1, $Sum(r)) :- InEdge[p](n), Rank[n](0, r1), EdgeCnt[n](cnt), r=0.85*r1/cnt. \n"+
			"Rank[p](2, $Sum(r)) :- InEdge[p](n), Rank[n](1, r1), EdgeCnt[n](cnt), r=0.85*r1/cnt. \n",
			numVertices);
		
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
	}
	
	static void pagerank2() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(2), fakeMap(), null);
		int numVertices=/*4847571*/1768197;
		String initQuery = String.format(
				"OutEdge[int s:0..%d]((int t)) sortby t.\n"+
				"OutEdge[s](t) :- line=$Read(\"hdfs:///user/ubuntu/data/lastfm.txt\"),"+
				"			   (s1,s2)=$Split(line), s=$ToInt(s1), t=$ToInt(s2).\n"+
				"EdgeCnt[int s:0..%d](int cnt).\n"+
				"EdgeCnt[s]($Inc(1)) :- OutEdge[s](t).",
				numVertices, numVertices);	
		String mainQuery=String.format("Rank[int n:0..%d]((int i:0..9, double rank)).\n"+
				"Rank[_](0, r) :- r= 0.000000006536. \n"+
				"Rank[_](1, r) :- r= 0.000000006536. \n"+
				"Rank[_](2, r) :- r= 0.000000006536. \n"+
				"Rank[_](3, r) :- r= 0.000000006536. \n"+
				"Rank[p](1, $Sum(r)) :- Rank[n](0, r1), EdgeCnt[n](cnt), r=0.85*r1/cnt, OutEdge[n](p).\n"+
				"Rank[p](2, $Sum(r)) :- Rank[n](1, r1), EdgeCnt[n](cnt), r=0.85*r1/cnt, OutEdge[n](p).\n"+
				"Rank[p](3, $Sum(r)) :- Rank[n](2, r1), EdgeCnt[n](cnt), r=0.85*r1/cnt, OutEdge[n](p).\n",
				numVertices);
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
	}
	static void pagerank_() {
		String initQuery = "OutEdge[int s:0..268435456]((int t:1)) sortby t.\n"+				
			"OutEdge[s](t) :- i=$MyId(), (s, t)=$LoadEdgeFriendster2Binary(\"hdfs://graph/28_2.directed/28_2.edge.%d.friendster\",i,i)."+
			"EdgeCnt[int s:0..268435456](int cnt).\n"+
			"EdgeCnt[s]($Inc(1)) :- OutEdge[s](t).";
		String mainQuery="Rank[int n:0..268435456](double rank).\n"+
			"Rank1[int n:0..268435456](double rank).\n"+
			"Rank[_](r) :- r= 0.000000000558793544769. \n"+
			"Rank1[_](r) :- r= 0.000000000558793544769. \n"+				
			"Rank1[p]($Sum(r)) :- Rank[n](r1), EdgeCnt[n](cnt), r=0.85*r1/cnt, OutEdge[n](p).\n";
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(16), fakeMap(), null);
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
	}
	static void sssp() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		long start=System.currentTimeMillis();
		String initQuery = "Edge[int s:0..4847571]((int t, double dist)) sortby dist.\n"+
		"Edge[s](t, d) :- (s, t, d)=$LoadEdgeDistanceBinary(\"hdfs:///user/jiwon/data/rand-dist.dat\"), s<2000000, t<2000000.";
		
		String mainQuery="Path[int s:0..4847571](double dist). \n" +
				"Path[s](d) :- s=5, d=0.0 . \n" +
				"Path[t]($Min(d)) :- Path[s](d1), Edge[s](t, d2), d=d1+d2.";
		
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
		
		mainQuery= "Path[s](d) :- s=5, d=0.0f . \n" +
				"Path[t]($Min(d)) :- Path[s](d1), Edge[s](t, d2), d=d1+d2.";
		//en.justCompile(mainQuery);
		
		//en.justCompile(mainQuery);
		//en.justCompile(mainQuery);
		//en.justCompile(mainQuery);
		
		long end=System.currentTimeMillis();
		System.out.println("Compile Time:"+(end-start)/1000.0+"sec");
	}

	static void comp() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		long start=System.currentTimeMillis();
		String initQuery = "Edge[int s:0..4847571]((int t:2)).\n"+		
			"Edge[t](s) :- (s, t, _)=$LoadEdgeDistanceBinary(\"data/lj-dist.dat\").\n"+
			"Node[int s:0..4847571](int i) predefined.\n"+
			"Node[s](1) :- Edge[s](t).\n";
		
		String mainQuery = "Comp[int n:0..4847571](int i).\n" +					
			"Comp[n](n) :- Node[n](_).\n"+ 
			"Comp[m]($Min(i)) :- Comp[n](i), Edge[n](m).";
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
	}
	
	static void lcc() {
        SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		long start=System.currentTimeMillis();
		String initQuery =  "Edge[int s:0..268435456]((int t:1)) sortby t.\n"+
		"EdgeCnt[int s:0..268435456](int cnt).\n"+
		"Edge[s](t) :- i=$MyId(), i1=i*2, i2=i*2+1, "+
		    "(s, t)=$LoadEdgeFriendster2Binary(\"hdfs://data/undirected/28_2.edge.%d.friendster\",i1,i2)."+
		"EdgeCnt(s, $Inc(1)) :- Edge(s, t)."+
		"Node[int s:0..268435456](int ignored).\n";
	
		String mainQuery = "Conn[int s:0..268435456](int c).\n"+
			"/*LCC[int s:0..268435456](double v).\n"+
			"LccSum[int i:0..15](double sum).\n"+
			"NCC[int i:0..15](double v).\n"+
			"NCC[_](l) :- l=0.0 .*/\n"+
			"Conn[n]($Inc(1)) :- Node[n](_), 0==(n mod 8), Edge[n](x), Edge[n](y), x<y, Edge[x](y).\n"+
			"/*LCC[n](l) :- Conn[n](c), EdgeCnt[n](k), k>1, l=1.0*c/(k*(k-1)) .\n"+
			"LccSum[i]($Sum(l)) :- LCC[n](l), i=$MyId(). \n"+
			"NCC[i](v) :- LccSum[i](sum), v=sum/268435456 . */\n";
	
		en.justCompile(initQuery);
		en.justCompile(mainQuery);
	}
	
	static void mutual() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		long start=System.currentTimeMillis();
		String initQuery =  "Edge[int s:0..268435456]((int t:1)) sortby t.\n"+
		"EdgeCnt[int s:0..268435456](int cnt).\n"+
		"Edge[s](t) :- i=$MyId(), i1=i*2, i2=i*2+1, "+
		    "(s, t)=$LoadEdgeFriendster2Binary(\"hdfs://data/undirected/28_2.edge.%d.friendster\",i1,i2)."+
		    "Pairs[int s:0..268435456](int i).\n"+		
		 	"Pairs[p1](p2) :- line=$Read(\"hdfs://graph/pairs.23\"),(a,b)=$Split(line), p1=$ToInt(a), p2=$ToInt(b).\n";
	
		en.justCompile(initQuery);
	}
	
	static void pr() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		String query = "InEdge[int s:0..100]((int t)) sortby t.\n" +
		      "EdgeCnt[int s:0..100](int cnt)." +
		      "EdgeCntInv[int s:0..100](double i)." +
		      "Rank[int n:0..100](int i:iter, double val) groupby(2)." +
		      "Term[int i:0..1](double d)." +
		      "InEdge[s](t) :- s=10, t=20 ." +
		      "EdgeCnt[s]($Inc(1)) :- InEdge[s](t)." +
		      "EdgeCntInv[s](i) :- EdgeCnt[s](c), i=0.7/c." +
		      "Rank[_](0, v) :- v=1.0.";

		en.justCompile(query);
		
		query = "Rank[_](1, v) :- v=0.3." +
		        "Rank[t](1, $Sum(v)) :-" +
		        "InEdge[s](t), Rank[s](1, v1), EdgeCntInv[s](i), v=v1*i.\n";		

		en.justCompile(query);
		en.shutdown();
	}
	static void triangle() {
		SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		String query = "Edge[int s:0..100]((int t)) sortby t.\n" +
		      "Triangle[int i:0..1](int count).\n" +
			   "Triangle[0]($Inc(1)) :- Edge[x](y), Edge[x](z), y<z, Edge[y](z).";

		int size=10;
		query += "Filter[int n:0.."+(size-1)+"]((int s:0..100, ApproxSet set)).\n"+
	    		//"Filter[mid](s, $ApproxSet(t)) :- Edge[s](t), mid = $Range(0,"+size+").\n";
				"";
		
		query += 		"Triangle[0]($Inc(1)) :- Edge[x](y), Edge[x](z), id=$MyId(), Filter[id](y, set),"+
				"a=$Contains(set, z), a==1, Edge[y](z).\n";
		en.justCompile(query);
		
		en.shutdown();
	}
	static void gd() {
        SRuntimeMaster.createTest(fakeMap());
		DistEngine en = new DistEngine(Config.dist(6), fakeMap(), null);
		
		final int M = 480189; // number of users
	    final int N = 17770; // number of movies
		final int L = 20; // numver of features

		double GAMMA = 0.00035;
		final double LAMBDA = 0.001;
		final double SHRINK_FACTOR = 0.995;

		String initQuery =
				"Rating[int u:0.." + (M - 1) + "]((int v, double r)) sortby v, multiset.\n" +
				//"RatingT[int v:0.." + (N - 1) + "]((int u, double r)) sortby u, multiset.\n" + 
				//"RatingCnt[int u:0..1](int cnt).\n" +
				"U[int u:0.." + (M - 1) + "](int w:iter, (int l:0.." + (L - 1) + ", double p)) groupby(3).\n" +
				//"V[int v:0.." + (N - 1) + "](int w:iter, (int l:0.." + (L - 1) + ", double q)) groupby(3).\n" +
				"Err[int u:0.." + (M - 1) + "]((int v, double e)) sortby v, groupby(2).\n" +
				//"ErrT[int v:0.." + (N - 1) + "]((int u, double e)) sortby u, groupby(2).\n" +
				//"ErrSum[int x:0..0](double s) groupby(1).\n" +
				//"DummyU(int i:0.." + (M - 1) + ").\n" +
				//"DummyV(int i:0.." + (N - 1) + ").\n" +
				"TmpU[int rank:0..4]((int u, (int l:0.." + (L - 1) + ", double p))) indexby u.\n" +
			    "TmpV[int rank:0..4]((int v, (int l:0.." + (L - 1) + ", double q))) indexby v.\n" +
			      
				//"DummyU(_) :- a=0.\n" +
				//"DummyV(_) :- a=0.\n" +
				"TmpU[0](a,b,c) :- a=0, b=2, c=1.1 .\n" +
				"TmpV[0](a,b,c) :- a=0, b=2, c=1.1 .\n" + 

				"TmpU[rank](u,l,p) :- Rating[u](v, r0), U[u](1, l, p), rank=1.";
		
		en.justCompile(initQuery);
		en.shutdown();
	}
	
	public static void main(String[] args) {
		/*
		triangle1();
		triangle2();
		*/
		//lastfmTriangle();
			
		//pagerank();
		//pagerank2();
		//pagerank_();
		
		//triangle1();
		
		
		//sssp();
		
		
		//triangle2();

		//lcc();
		//comp();
		
		//mutual();
		
		//gd();
		//pr();
		//triangle();
	}
}