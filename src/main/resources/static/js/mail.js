angular.module('smtp', ['ui.bootstrap'])
.controller('mail', function($scope, $http, $interval, $log, $uibModal) {
    $scope.emailList = []
    $scope.refresh = function() {
        $http.get('/emails').then(function(response) {
            $scope.emailList = response.data;
            $scope.updatedAt = new Date();
        });
    };
    $scope.refresh();
    $interval($scope.refresh, 1000);
    $scope.delete = function(id) {
        $log.log("delete " + id);
        $http.get('/delete/'+id).then(function(response) {
            $scope.refresh();
        });
    };

    $scope.showEmail = function(id) {
        $http.get('/emails/'+id).then(function(response) {
            $log.log("show " + id);
            $log.log(response.data);
            $uibModal.open({animation: 'true',
                  ariaLabelledBy: 'modal-title',
                  ariaDescribedBy: 'modal-body',
                  templateUrl: 'showEmail.html',
                  controller: function($scope, $uibModalInstance) {
                    $scope.email = response.data;
                    $scope.ok = function() {
                        $uibModalInstance.close();
                    };
                  },
                  controllerAs: '$ctrl',
                  appendTo: undefined
            });
        });
    }
});
