set class=amtc.gue.ws.service.soap.BookGrabber
set clpth=./target/UniversalService-war-1.0-SNAPSHOT/WEB-INF/classes
set resourcedir=./target/UniversalService-war-1.0-SNAPSHOT
set outsourcedir=./src/main/java
set outdir=./target/UniversalService-war-1.0-SNAPSHOT/WEB-INF/classes
wsgen -cp "%clpth%" -wsdl -keep -r "%resourcedir%" -d "%outdir%" -s "%outsourcedir%" %class%
PAUSE