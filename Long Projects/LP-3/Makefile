JAVA=java
JAVAC=javac

default: run

run : program

program : 
		$(JAVAC) mxg200078/RedBlackTreeDriver.java
		$(JAVA) mxg200078/RedBlackTreeDriver.java sk-t01.txt
		# $(JAVA) mxg200078/RedBlackTreeDriver.java test1.txt

		rm -f mxg200078/*.class

clean:
		rm -f mxg200078/*.class