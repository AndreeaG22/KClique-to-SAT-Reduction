all: build

build: KCliqueToSat.class
	cp main.sh main && chmod +x main

KCliqueToSat.class:
	javac KCliqueToSat.java

clean:
	rm -rf main *.class