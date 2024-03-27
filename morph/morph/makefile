JC       = javac
JAVAHOME = /usr/local/java-1.0.2/lib/classes.zip
JCFLAGS  = -O -classpath .:../.:$(JAVAHOME) -d ../.
CLASSES  = Morph

.SUFFIXES : .java .class
.java.class:
	$(JC) $(JCFLAGS) $(<)

all: $(CLASSES:=.class)

clean:
	rm -rf *.class
