(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('ExtreurePotController', ExtreurePotController);

    ExtreurePotController.$inject = ['$timeout', '$uibModalInstance', 'entity', 'Pot'];

    function ExtreurePotController ($timeout, $uibModalInstance, entity, Pot) {
        var vm = this;

        vm.quantitat = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            Pot.extreure(vm.pot, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
