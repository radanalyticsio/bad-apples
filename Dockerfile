FROM radanalyticsio/radanalytics-java-spark

ADD src  .

ADD pom.xml .

ADD secret.yaml .

ADD filter.yaml .

CMD ["java", "src/main/java/io/radanalytics/limitfilter/App.java"]
