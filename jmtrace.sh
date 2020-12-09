JMTRACE_PATH=target/jmtrace-1.0.0-jar-with-dependencies.jar
java -javaagent:${JMTRACE_PATH} $@
