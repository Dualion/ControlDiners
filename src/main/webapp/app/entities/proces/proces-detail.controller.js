(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('ProcesDetailController', ProcesDetailController);

    ProcesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Proces', 'Pot'];

    function ProcesDetailController($scope, $rootScope, $stateParams, previousState, entity, Proces, Pot) {
        var vm = this;

        vm.proces = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:procesUpdate', function(event, result) {
            vm.proces = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
