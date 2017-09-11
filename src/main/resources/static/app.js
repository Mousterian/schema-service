
var app = angular.module('schema-service-ui', ['ngSanitize', 'schemaForm']);

app.controller('MainCtrl', function ($scope, $http) {

    // TO DO: make this a dynamic selection
    $http.get("/schemaformdata/something/test2").then(function (response) {
        console.log('Got config response: ' + response.data)
        $scope.schema = response.data.schema;
        $scope.form = response.data.form;
        $scope.model = response.data.model;
        $scope.$broadcast('schemaFormRedraw');
    });

    $scope.submit = function() {
        // TO DO: make this a dynamic selection
        $http.put("/data/something/test2", { "model" : $scope.model });
    };
});

