(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisDialogController', UsuarisDialogController);

    UsuarisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Usuaris'];

    function UsuarisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Usuaris) {
        var vm = this;

        vm.usuaris = entity;
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
            if (vm.usuaris.id !== null) {
                Usuaris.update(vm.usuaris, onSaveSuccess, onSaveError);
            } else {
                Usuaris.save(vm.usuaris, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:usuarisUpdate', result);
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
