FROM fabric8/java-centos-openjdk8-jdk

ADD src  .

ADD pom.xml .

ADD secret.yaml .

ADD filter.yaml .

CMD ["java", "src/main/java/io/radanalytics/App.java"]
