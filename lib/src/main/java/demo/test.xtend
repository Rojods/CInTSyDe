package demo

class test {
	def static void main(String[] args) {
		var String a = "aaa;bbbbbbb;c"
		var b=new StringBuilder(a)
		var start=0
		var index=0
		
		while( (index=b.indexOf(";",start))!=-1 ){
			start=index+1
			b.insert(index+1,"\n")
		}
		
//		
//		index=b.indexOf(";",start)
//		start=index+1
//		println(index)
//		println(" start "+ start)
//		b.insert(index+1,"\n")
//		println(b.toString())
//		
//		index=b.indexOf(";",start)
//		start=index+1
//		println(index)
//		println(" start "+ start)
//		b.insert(index+1,"\n")
		println(b.toString())
	}
}