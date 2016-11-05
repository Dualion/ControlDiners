(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('ProcesDialogController', ProcesDialogController);

    ProcesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Proces'];

    function ProcesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Proces) {
        var vm = this;

        vm.proces = entity;
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
            if (vm.proces.id !== null) {
                Proces.update(vm.proces, onSaveSuccess, onSaveError);
            } else {
                Proces.save(vm.proces, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:procesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataInici = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
