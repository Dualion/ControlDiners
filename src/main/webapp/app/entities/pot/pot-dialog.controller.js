(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('PotDialogController', PotDialogController);

    PotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pot'];

    function PotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pot) {
        var vm = this;

        vm.pot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pot.id !== null) {
                Pot.update(vm.pot, onSaveSuccess, onSaveError);
            } else {
                Pot.save(vm.pot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:potUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
