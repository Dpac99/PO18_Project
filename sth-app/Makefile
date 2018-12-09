JAVADIR=/usr/share/java
JARNAME=sth-app

all:
	(cd src; javac -cp $(JAVADIR)/po-uuilib.jar:../../sth-core/sth-core.jar -encoding UTF-8 `find . -name \*.java`)
	(cd src; jar cvf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java` )

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`
