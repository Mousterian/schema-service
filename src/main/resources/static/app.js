
var app = angular.module('schema-service-ui', ['ngSanitize', 'schemaForm']);

app.controller('MainCtrl', function ($scope, $http) {

    $http.get("http://localhost:8080/test.json").then(function (response) {
        console.log('Got config response: ' + response.data)
        $scope.schema = response.data.schema;
        $scope.form = response.data.form;
        $scope.model = response.data.model;
        $scope.$broadcast('schemaFormRedraw');
    });
});

