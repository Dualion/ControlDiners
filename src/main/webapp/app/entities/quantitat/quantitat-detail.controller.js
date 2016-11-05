(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('QuantitatDetailController', QuantitatDetailController);

    QuantitatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Quantitat'];

    function QuantitatDetailController($scope, $rootScope, $stateParams, previousState, entity, Quantitat) {
        var vm = this;

        vm.quantitat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:quantitatUpdate', function(event, result) {
            vm.quantitat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
