(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('DinersPotDetailController', DinersPotDetailController);

    DinersPotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DinersPot', 'Pot'];

    function DinersPotDetailController($scope, $rootScope, $stateParams, previousState, entity, DinersPot, Pot) {
        var vm = this;

        vm.dinersPot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:dinersPotUpdate', function(event, result) {
            vm.dinersPot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
