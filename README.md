
~~~
# TO DO: either put this into the gradle build or run the front end with grunt?
cd src/main/resources/static
bower install angular-schema-form

./gradlew bootrun
curl http://localhost:8080/schema?schemaName=SOMETHING
~~~