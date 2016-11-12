(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('DinersPotDialogController', DinersPotDialogController);

    DinersPotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DinersPot', 'Pot'];

    function DinersPotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DinersPot, Pot) {
        var vm = this;

        vm.dinersPot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pots = Pot.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dinersPot.id !== null) {
                DinersPot.update(vm.dinersPot, onSaveSuccess, onSaveError);
            } else {
                DinersPot.save(vm.dinersPot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:dinersPotUpdate', result);
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
