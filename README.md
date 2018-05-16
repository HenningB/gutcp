# Java Implementation of the Grand Unified Theory of Classical Physics by Randell Mills

This project aims to implement some of the [Grand Unified Theory of Classical Physics](http://brilliantlightpower.com/book-download-and-streaming/) calculations by Randell Mills in Java.

It calculates ionization energies of elements and compares the results with NIST measurements.

## Building and running the program

To run the application, you need to install Java 8 or above, and then run the following command from the command line.

Unix:
```
./gradlew run
```

Windows:
```
gradlew.bat run
```

It downloads quite a bit from the internet (if you're not behind a corporate firewall), builds the program and runs it. The result is a file called `ionization-chart.html` located in the main directory.
