
var app = angular.module('schema-service-ui', ['ngSanitize', 'schemaForm']);

app.controller('MainCtrl', function ($scope, $http) {

    $http.get("/schemaformdata/something/test2").then(function (response) {
        console.log('Got config response: ' + response.data)
        $scope.schema = response.data.schema;
        $scope.form = response.data.form;
        $scope.model = response.data.model;
        $scope.$broadcast('schemaFormRedraw');
    });
});

